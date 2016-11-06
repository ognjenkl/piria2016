/**
 * AddActorResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package actor;

public class AddActorResponse  implements java.io.Serializable {
    private java.lang.String addActorReturn;

    public AddActorResponse() {
    }

    public AddActorResponse(
           java.lang.String addActorReturn) {
           this.addActorReturn = addActorReturn;
    }


    /**
     * Gets the addActorReturn value for this AddActorResponse.
     * 
     * @return addActorReturn
     */
    public java.lang.String getAddActorReturn() {
        return addActorReturn;
    }


    /**
     * Sets the addActorReturn value for this AddActorResponse.
     * 
     * @param addActorReturn
     */
    public void setAddActorReturn(java.lang.String addActorReturn) {
        this.addActorReturn = addActorReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddActorResponse)) return false;
        AddActorResponse other = (AddActorResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addActorReturn==null && other.getAddActorReturn()==null) || 
             (this.addActorReturn!=null &&
              this.addActorReturn.equals(other.getAddActorReturn())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAddActorReturn() != null) {
            _hashCode += getAddActorReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddActorResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://actor", ">addActorResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addActorReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://actor", "addActorReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
