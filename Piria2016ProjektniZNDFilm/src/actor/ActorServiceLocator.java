/**
 * ActorServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package actor;

public class ActorServiceLocator extends org.apache.axis.client.Service implements actor.ActorService {

    public ActorServiceLocator() {
    }


    public ActorServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ActorServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Actor
    private java.lang.String Actor_address = "http://localhost:8080/ActorsSOAP/services/Actor";

    public java.lang.String getActorAddress() {
        return Actor_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ActorWSDDServiceName = "Actor";

    public java.lang.String getActorWSDDServiceName() {
        return ActorWSDDServiceName;
    }

    public void setActorWSDDServiceName(java.lang.String name) {
        ActorWSDDServiceName = name;
    }

    public actor.Actor getActor() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Actor_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getActor(endpoint);
    }

    public actor.Actor getActor(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            actor.ActorSoapBindingStub _stub = new actor.ActorSoapBindingStub(portAddress, this);
            _stub.setPortName(getActorWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setActorEndpointAddress(java.lang.String address) {
        Actor_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (actor.Actor.class.isAssignableFrom(serviceEndpointInterface)) {
                actor.ActorSoapBindingStub _stub = new actor.ActorSoapBindingStub(new java.net.URL(Actor_address), this);
                _stub.setPortName(getActorWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Actor".equals(inputPortName)) {
            return getActor();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://actor", "ActorService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://actor", "Actor"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Actor".equals(portName)) {
            setActorEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
