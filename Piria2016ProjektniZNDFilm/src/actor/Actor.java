/**
 * Actor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package actor;

public interface Actor extends java.rmi.Remote {
    public java.lang.String addActor(java.lang.String actor) throws java.rmi.RemoteException;
    public java.lang.String getActors(java.lang.String actorName) throws java.rmi.RemoteException;
}
