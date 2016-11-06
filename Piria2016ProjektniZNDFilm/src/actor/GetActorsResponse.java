/**
 * GetActorsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package actor;

public class GetActorsResponse  implements java.io.Serializable {
    private java.lang.Object[] getActorsReturn;

    public GetActorsResponse() {
    }

    public GetActorsResponse(
           java.lang.Object[] getActorsReturn) {
           this.getActorsReturn = getActorsReturn;
    }


    /**
     * Gets the getActorsReturn value for this GetActorsResponse.
     * 
     * @return getActorsReturn
     */
    public java.lang.Object[] getGetActorsReturn() {
        return getActorsReturn;
    }


    /**
     * Sets the getActorsReturn value for this GetActorsResponse.
     * 
     * @param getActorsReturn
     */
    public void setGetActorsReturn(java.lang.Object[] getActorsReturn) {
        this.getActorsReturn = getActorsReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetActorsResponse)) return false;
        GetActorsResponse other = (GetActorsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getActorsReturn==null && other.getGetActorsReturn()==null) || 
             (this.getActorsReturn!=null &&
              java.util.Arrays.equals(this.getActorsReturn, other.getGetActorsReturn())));
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
        if (getGetActorsReturn() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetActorsReturn());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGetActorsReturn(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetActorsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://actor", ">getActorsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getActorsReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://actor", "getActorsReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://dto", "item"));
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
