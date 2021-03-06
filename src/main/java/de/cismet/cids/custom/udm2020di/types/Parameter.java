/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.Serializable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlTransient;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
@JacksonXmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter implements Serializable, Comparable<Parameter>, Cloneable {

    //~ Static fields/initializers ---------------------------------------------

    @JsonIgnore @XmlTransient public static final String PROP_SELECTED = "selected";

    @JsonIgnore @XmlTransient public static final char UNIT_SEPARATOR = '[';

    @JsonIgnore @XmlTransient public static final Pattern UNIT_REGEX = Pattern.compile("(?<=\\[)[^\\[.]+?(?=\\])");

    //~ Instance fields --------------------------------------------------------

    @JacksonXmlProperty(
        isAttribute = true,
        localName = "parameterpk"
    )
    @JsonProperty("parameterpk")
    private String parameterPk;
    @JacksonXmlProperty(
        isAttribute = true,
        localName = "parametergruppepk"
    )
    @JsonProperty("parametergruppepk")
    private String parametergruppePk;
    @JacksonXmlProperty(
        isAttribute = true,
        localName = "parametername"
    )
    @JsonProperty("parametername")
    private String parameterName;
    @JacksonXmlProperty(
        isAttribute = true,
        localName = "parametergruppename"
    )
    @JsonProperty("parametergruppename")
    private String parametergruppeName;
    @JacksonXmlProperty(
        isAttribute = true,
        localName = "parametereinheit"
    )
    @JsonProperty("parametereinheit")
    private String parameterEinheit;

    @JsonIgnore @XmlTransient private boolean selected;

    private final transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Parameter object.
     */
    public Parameter() {
    }

    /**
     * Creates a new Parameter object.
     *
     * @param  parameter  DOCUMENT ME!
     */
    public Parameter(final Parameter parameter) {
        this(
            parameter.parameterPk,
            parameter.parametergruppePk,
            parameter.parameterName,
            parameter.parametergruppeName,
            parameter.selected);
    }

    /**
     * Creates a new Parameter object.
     *
     * @param  parameterMapping  DOCUMENT ME!
     */
    public Parameter(final ParameterMapping parameterMapping) {
        this(parameterMapping.getParameterPk(), parameterMapping.getDisplayName());
    }

    /**
     * Creates a new Parameter object.
     *
     * @param  parameterPk    DOCUMENT ME!
     * @param  parameterName  DOCUMENT ME!
     */
    public Parameter(final String parameterPk, final String parameterName) {
        this.parameterPk = parameterPk;
        this.parameterName = parameterName;
    }

    /**
     * Creates a new Parameter object.
     *
     * @param  parameterPk          DOCUMENT ME!
     * @param  parametergruppePk    DOCUMENT ME!
     * @param  parameterName        DOCUMENT ME!
     * @param  parametergruppeName  DOCUMENT ME!
     * @param  selected             DOCUMENT ME!
     */
    public Parameter(final String parameterPk,
            final String parametergruppePk,
            final String parameterName,
            final String parametergruppeName,
            final boolean selected) {
        this.parameterPk = parameterPk;
        this.parametergruppePk = parametergruppePk;
        this.parameterName = parameterName;
        this.parametergruppeName = parametergruppeName;
        this.selected = selected;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of selected.
     *
     * @return  the value of selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Set the value of selected.
     *
     * @param  selected  new value of selected
     */
    public void setSelected(final boolean selected) {
        final boolean oldSelected = this.selected;
        this.selected = selected;
        propertyChangeSupport.firePropertyChange(PROP_SELECTED, oldSelected, selected);
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getParameterPk() {
        return parameterPk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parameterPk  DOCUMENT ME!
     */
    public void setParameterPk(final String parameterPk) {
        this.parameterPk = parameterPk;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getParametergruppePk() {
        return parametergruppePk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parametergruppePk  DOCUMENT ME!
     */
    public void setParametergruppePk(final String parametergruppePk) {
        this.parametergruppePk = parametergruppePk;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parameterName  DOCUMENT ME!
     */
    public void setParameterName(final String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getParametergruppeName() {
        return parametergruppeName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parametergruppeName  DOCUMENT ME!
     */
    public void setParametergruppeName(final String parametergruppeName) {
        this.parametergruppeName = parametergruppeName;
    }

    @Override
    public int compareTo(final Parameter probenparameter) {
        return this.getParameterName().compareTo(probenparameter.getParameterName());
    }

    @Override
    public String toString() {
        return this.getParameterName();
    }

    /**
     * Get the value of parameterEinheit.
     *
     * @return  the value of parameterEinheit
     */
    public String getParameterEinheit() {
        if ((this.parameterEinheit == null) || this.parameterEinheit.isEmpty()) {
            if ((this.getParameterName() != null) && !this.getParameterName().isEmpty()) {
                final Matcher matcher = UNIT_REGEX.matcher(this.getParameterName());
                if (matcher.find()) {
                    this.parameterEinheit = matcher.group();
                }
            }
        }
        return this.parameterEinheit;
    }

    /**
     * Set the value of parameterEinheit.
     *
     * @param  parameterEinheit  new value of parameterEinheit
     */
    public void setParameterEinheit(final String parameterEinheit) {
        this.parameterEinheit = parameterEinheit;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  ParameterName ohne Einheit.
     */
    @JsonIgnore
    @XmlTransient
    public String getParameterNamePlain() {
        final String paramUnit = this.getParameterEinheit();
        if ((paramUnit != null) && !paramUnit.isEmpty()) {
            if ((this.getParameterName() != null) && !this.getParameterName().isEmpty()) {
                final String tmpName = this.getParameterName().replace(paramUnit, "").trim();
                final int index = tmpName.indexOf(UNIT_SEPARATOR);
                if (index != -1) {
                    return tmpName.substring(0, index).trim();
                }
                return tmpName;
            }
        }

        return this.getParameterName();
    }

    /**
     * Checks for equality of Parameter PK only.
     *
     * @param   obj  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Parameter other = (Parameter)obj;
        if (!Objects.equals(this.parameterPk, other.parameterPk)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (29 * hash) + Objects.hashCode(this.parameterPk);
        return hash;
    }

    @Override
    public Parameter clone() throws CloneNotSupportedException {
        return new Parameter(this);
    }
}
