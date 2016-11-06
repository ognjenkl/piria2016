/**
 * ActorService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package actor;

public interface ActorService extends javax.xml.rpc.Service {
    public java.lang.String getActorAddress();

    public actor.Actor getActor() throws javax.xml.rpc.ServiceException;

    public actor.Actor getActor(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
