/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@XmlRootElement
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterMapping implements Cloneable {

    //~ Instance fields --------------------------------------------------------

    @JacksonXmlProperty(localName = "parameterpk")
    @JsonProperty("parameterpk")
    private String parameterPk;

    @JacksonXmlProperty(localName = "displayname")
    @JsonProperty("displayname")
    private String displayName;

    @JacksonXmlProperty(localName = "unit")
    @JsonProperty("unit")
    private String unit;

    @JacksonXmlProperty(localName = "pollutanttagkey")
    @JsonProperty("pollutanttagkey")
    private String pollutantTagKey;

    @JacksonXmlProperty(localName = "pollutantgrouptagkey")
    @JsonProperty("pollutantgrouptagkey")
    private String pollutantGroupTagKey;

    @JacksonXmlProperty(localName = "pollutanttagid")
    @JsonProperty("pollutanttagid")
    private long pollutantTagId;

    @JacksonXmlProperty(localName = "pollutantgrouptagid")
    @JsonProperty("pollutantgrouptagid")
    private long pollutantGroupTagId;

    @JacksonXmlProperty(localName = "pollutanttagname")
    @JsonProperty("pollutanttagname")
    private String pollutantTagName;

    @JacksonXmlProperty(localName = "pollutantgrouptagname")
    @JsonProperty("pollutantgrouptagname")
    private String pollutantGroupTagName;

    @JacksonXmlProperty(localName = "parameteraggregationpk")
    @JsonProperty("parameteraggregationpk")
    private String parameterAggregationPk;

    @JacksonXmlProperty(localName = "parameteraggregationexpression")
    @JsonProperty("parameteraggregationexpression")
    private String parameterAggregationExpression;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ParameterMapping object.
     */
    public ParameterMapping() {
    }

    /**
     * Creates a new ParameterMapping object.
     *
     * @param  parameterPk                     DOCUMENT ME!
     * @param  displayName                     DOCUMENT ME!
     * @param  unit                            DOCUMENT ME!
     * @param  pollutantTagKey                 DOCUMENT ME!
     * @param  pollutantGroupTagKey            DOCUMENT ME!
     * @param  pollutantTagId                  DOCUMENT ME!
     * @param  pollutantGroupTagId             DOCUMENT ME!
     * @param  pollutantTagName                DOCUMENT ME!
     * @param  pollutantGroupTagName           DOCUMENT ME!
     * @param  parameterAggregationPk          DOCUMENT ME!
     * @param  parameterAggregationExpression  DOCUMENT ME!
     */
    public ParameterMapping(final String parameterPk,
            final String displayName,
            final String unit,
            final String pollutantTagKey,
            final String pollutantGroupTagKey,
            final long pollutantTagId,
            final long pollutantGroupTagId,
            final String pollutantTagName,
            final String pollutantGroupTagName,
            final String parameterAggregationPk,
            final String parameterAggregationExpression) {
        this.parameterPk = parameterPk;
        this.displayName = displayName;
        this.unit = unit;
        this.pollutantTagKey = pollutantTagKey;
        this.pollutantGroupTagKey = pollutantGroupTagKey;
        this.pollutantTagId = pollutantTagId;
        this.pollutantGroupTagId = pollutantGroupTagId;
        this.pollutantTagName = pollutantTagName;
        this.pollutantGroupTagName = pollutantGroupTagName;
        this.parameterAggregationPk = parameterAggregationPk;
        this.parameterAggregationExpression = parameterAggregationExpression;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of unit.
     *
     * @return  the value of unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set the value of unit.
     *
     * @param  unit  new value of unit
     */
    public void setUnit(final String unit) {
        this.unit = unit;
    }

    /**
     * Get the value of parameterAggregationExpression.
     *
     * @return  the value of parameterAggregationExpression
     */
    public String getParameterAggregationExpression() {
        return parameterAggregationExpression;
    }

    /**
     * Set the value of parameterAggregationExpression.
     *
     * @param  parameterAggregationExpression  new value of parameterAggregationExpression
     */
    public void setParameterAggregationExpression(final String parameterAggregationExpression) {
        this.parameterAggregationExpression = parameterAggregationExpression;
    }

    /**
     * Get the value of parameterAggregationPk.
     *
     * @return  the value of parameterAggregationPk
     */
    public String getParameterAggregationPk() {
        return parameterAggregationPk;
    }

    /**
     * Set the value of parameterAggregationPk.
     *
     * @param  parameterAggregationPk  new value of parameterAggregationPk
     */
    public void setParameterAggregationPk(final String parameterAggregationPk) {
        this.parameterAggregationPk = parameterAggregationPk;
    }

    /**
     * Get the value of parameterPk.
     *
     * @return  the value of parameterPk
     */
    public String getParameterPk() {
        return parameterPk;
    }

    /**
     * Set the value of parameterPk.
     *
     * @param  parameterPk  new value of parameterPk
     */
    public void setParameterPk(final String parameterPk) {
        this.parameterPk = parameterPk;
    }

    /**
     * Get the value of displayName.
     *
     * @return  the value of displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the value of displayName.
     *
     * @param  displayName  new value of displayName
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the value of pollutantTagKey.
     *
     * @return  the value of pollutantTagKey
     */
    public String getPollutantTagKey() {
        return pollutantTagKey;
    }

    /**
     * Set the value of pollutantTagKey.
     *
     * @param  pollutantTagKey  new value of pollutantTagKey
     */
    public void setPollutantTagKey(final String pollutantTagKey) {
        this.pollutantTagKey = pollutantTagKey;
    }

    /**
     * Get the value of pollutantGroupTagKey.
     *
     * @return  the value of pollutantGroupTagKey
     */
    public String getPollutantGroupKey() {
        return pollutantGroupTagKey;
    }

    /**
     * Set the value of pollutantGroupTagKey.
     *
     * @param  pollutantGroupKey  new value of pollutantGroupTagKey
     */
    public void setPollutantGroupKey(final String pollutantGroupKey) {
        this.pollutantGroupTagKey = pollutantGroupKey;
    }

    /**
     * Get the value of pollutantTagId.
     *
     * @return  the value of pollutantTagId
     */
    public long getPollutantTagId() {
        return pollutantTagId;
    }

    /**
     * Set the value of pollutantTagId.
     *
     * @param  pollutantTagId  new value of pollutantTagId
     */
    public void setPollutantTagId(final long pollutantTagId) {
        this.pollutantTagId = pollutantTagId;
    }

    /**
     * Get the value of pollutantGroupTagId.
     *
     * @return  the value of pollutantGroupTagId
     */
    public long getPollutantGroupTagId() {
        return pollutantGroupTagId;
    }

    /**
     * Set the value of pollutantGroupTagId.
     *
     * @param  pollutantGroupTagId  new value of pollutantGroupTagId
     */
    public void setPollutantGroupTagId(final long pollutantGroupTagId) {
        this.pollutantGroupTagId = pollutantGroupTagId;
    }

    /**
     * Get the value of pollutantTagName.
     *
     * @return  the value of pollutantTagName
     */
    public String getPollutantTagName() {
        return pollutantTagName;
    }

    /**
     * Set the value of pollutantTagName.
     *
     * @param  pollutantTagName  new value of pollutantTagName
     */
    public void setPollutantTagName(final String pollutantTagName) {
        this.pollutantTagName = pollutantTagName;
    }

    /**
     * Get the value of pollutantGroupTagName.
     *
     * @return  the value of pollutantGroupTagName
     */
    public String getPollutantGroupTagName() {
        return pollutantGroupTagName;
    }

    /**
     * Set the value of pollutantGroupTagName.
     *
     * @param  pollutantGroupTagName  new value of pollutantGroupTagName
     */
    public void setPollutantGroupTagName(final String pollutantGroupTagName) {
        this.pollutantGroupTagName = pollutantGroupTagName;
    }

    @Override
    public ParameterMapping clone() throws CloneNotSupportedException {
        return new ParameterMapping(
                parameterPk,
                displayName,
                unit,
                pollutantTagKey,
                pollutantGroupTagKey,
                pollutantTagId,
                pollutantGroupTagId,
                pollutantTagName,
                pollutantGroupTagName,
                parameterAggregationPk,
                parameterAggregationExpression);
    }
}
