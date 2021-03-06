/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.serversearch;

import Sirius.server.middleware.interfaces.domainserver.MetaService;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

import java.math.BigDecimal;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import de.cismet.cids.server.search.AbstractCidsServerSearch;
import de.cismet.cids.server.search.SearchException;

/**
 * Search for Objects, filter by tags.
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class FilterByTagsSearch extends AbstractCidsServerSearch {

    //~ Static fields/initializers ---------------------------------------------

    protected static final String DOMAIN = "UDM2020-DI";

    protected static final Logger LOGGER = Logger.getLogger(FilterByTagsSearch.class);

    //~ Instance fields --------------------------------------------------------

    protected final String filterByTagsTpl;
    protected final String selectObjectIdConstantsTpl;

    @Getter @Setter protected List<Node> nodes;

    @Getter @Setter protected Collection<Integer> filterTagIds;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FilterByTagsSearch object.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public FilterByTagsSearch() throws IOException {
        this.filterByTagsTpl = IOUtils.toString(this.getClass().getResourceAsStream(
                    "/de/cismet/cids/custom/udm2020di/serversearch/filter-by-tags.tpl.sql"),
                "UTF-8");

        this.selectObjectIdConstantsTpl = IOUtils.toString(this.getClass().getResourceAsStream(
                    "/de/cismet/cids/custom/udm2020di/serversearch/select-object-id-constants.tpl.sql"),
                "UTF-8");
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Parametrise the sql template.
     *
     * @param   nodes         classIds
     * @param   filterTagIds  objectIds
     *
     * @return  DOCUMENT ME!
     */
    protected String createFilterByTagsSearchStatement(final Collection<Node> nodes,
            final Collection<Integer> filterTagIds) {
        final Map<Integer, Collection<Integer>> objectIdMap = new HashMap<Integer, Collection<Integer>>();
        for (final Node node : nodes) {
            if (MetaObjectNode.class.isAssignableFrom(node.getClass())) {
                final MetaObjectNode metaObjectNode = (MetaObjectNode)node;
                final Collection<Integer> objectIds;
                if (objectIdMap.containsKey(node.getClassId())) {
                    objectIds = objectIdMap.get(node.getClassId());
                } else {
                    objectIds = new ArrayList<Integer>();
                    objectIdMap.put(node.getClassId(), objectIds);
                }
                objectIds.add(metaObjectNode.getObjectId());
            }
        }

        final StringBuilder searchStatementBuilder = new StringBuilder();
        final StringBuilder tagIdsBuilder = new StringBuilder();
        final Iterator<Integer> tagIdsIterator = filterTagIds.iterator();
        while (tagIdsIterator.hasNext()) {
            tagIdsBuilder.append('\'').append(tagIdsIterator.next()).append('\'');
            if (tagIdsIterator.hasNext()) {
                tagIdsBuilder.append(',');
            }
        }
        int i = 0;
        for (final Integer classId : objectIdMap.keySet()) {
            i++;
            final Collection<Integer> objectIds = objectIdMap.get(classId);
            final StringBuilder objectIdsBuilder = new StringBuilder();
//            final StringBuilder objectIdConstantsBuilder = new StringBuilder();
            final Iterator<Integer> objectIdsIterator = objectIds.iterator();
            while (objectIdsIterator.hasNext()) {
                final Integer objectId = objectIdsIterator.next();
//                String objectIdConstantsStatement = this.selectObjectIdConstantsTpl.replace(
//                        "%CLASS_ID%",
//                        classId.toString());
//                objectIdConstantsStatement
//                        = objectIdConstantsStatement.replace("%OBJECT_ID%", objectId.toString());
//                objectIdConstantsBuilder.append(objectIdConstantsStatement);
                objectIdsBuilder.append(objectId);

                if (objectIdsIterator.hasNext()) {
//                    objectIdConstantsBuilder.append('\n');
//                    objectIdConstantsBuilder.append(" UNION ");
//                    objectIdConstantsBuilder.append('\n');
                    objectIdsBuilder.append(',');
                }
            }

//            String filterByTagsStatement = this.filterByTagsTpl.replace(
//                    "%OBJECT_ID_CONSTANTS%",
//                    objectIdConstantsBuilder);
            String filterByTagsStatement = this.filterByTagsTpl.replace(
                    "%CLASS_ID%",
                    classId.toString());
            filterByTagsStatement = filterByTagsStatement.replace(
                    "%TAG_IDS%",
                    tagIdsBuilder);
            filterByTagsStatement = filterByTagsStatement.replace(
                    "%OBJECT_IDS%",
                    objectIdsBuilder);
            searchStatementBuilder.append(filterByTagsStatement);

            if (i < objectIdMap.size()) {
                searchStatementBuilder.append('\n');
                searchStatementBuilder.append(" UNION ");
                searchStatementBuilder.append('\n');
            }
        }

        return searchStatementBuilder.toString();
    }

    @Override
    public Collection<Node> performServerSearch() throws SearchException {
        final long startTime = System.currentTimeMillis();

        if ((this.nodes != null) && !this.nodes.isEmpty()
                    && (this.filterTagIds != null) && !this.filterTagIds.isEmpty()) {
            LOGGER.info("filtering " + nodes.size() + " nodes by "
                        + filterTagIds.size() + " tags");

            final String searchStatement = this.createFilterByTagsSearchStatement(nodes, filterTagIds);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(searchStatement);
            }

            final MetaService metaService = (MetaService)getActiveLocalServers().get(DOMAIN);
            if (metaService != null) {
                try {
                    final ArrayList<ArrayList> resultSet = metaService.performCustomSearch(searchStatement);

                    if (resultSet.isEmpty()) {
                        LOGGER.warn("all nodes filtered by " + filterTagIds.size() + " tags in "
                                    + (System.currentTimeMillis() - startTime) + " ms");
                        nodes.clear();
                    } else if (resultSet.size() == nodes.size()) {
                        LOGGER.warn("no nodes filtered by " + filterTagIds.size() + " tags in "
                                    + (System.currentTimeMillis() - startTime) + "ms");
                    } else {
                        final int nodesSize = nodes.size();
                        final Map<Integer, Collection<Integer>> objectIdMap =
                            new HashMap<Integer, Collection<Integer>>();

                        for (final ArrayList row : resultSet) {
                            final Integer classId = ((BigDecimal)row.get(0)).intValue();
                            final Integer objectId = ((BigDecimal)row.get(1)).intValue();
                            final Collection<Integer> objectIds;

                            if (objectIdMap.containsKey(classId)) {
                                objectIds = objectIdMap.get(classId);
                            } else {
                                objectIds = new ArrayList<Integer>();
                                objectIdMap.put(classId, objectIds);
                            }
                            objectIds.add(objectId);
                        }

                        final ListIterator<Node> nodesIterator = nodes.listIterator();
                        while (nodesIterator.hasNext()) {
                            final Node node = nodesIterator.next();
                            if (MetaObjectNode.class.isAssignableFrom(node.getClass())) {
                                final MetaObjectNode metaObjectNode = (MetaObjectNode)node;
                                if (!objectIdMap.containsKey(metaObjectNode.getClassId())
                                            || !objectIdMap.get(metaObjectNode.getClassId()).contains(
                                                metaObjectNode.getObjectId())) {
                                    nodesIterator.remove();
                                }
                            }
                        }

                        LOGGER.info(nodes.size() + " nodes of "
                                    + nodesSize + " original nodes remaining after applying "
                                    + filterTagIds.size() + " filter tags in "
                                    + (System.currentTimeMillis() - startTime) + "ms");
                    }
                } catch (RemoteException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    throw new SearchException(ex.getMessage(), ex);
                }
            } else {
                final String message = "active local server " + DOMAIN + "not found";
                LOGGER.error(message); // NOI18N
                throw new SearchException(message);
            }
        } else {
            LOGGER.warn("missing parameters, returning unmodified node collection of size "
                        + this.nodes.size());
        }

        return nodes;
    }
}
