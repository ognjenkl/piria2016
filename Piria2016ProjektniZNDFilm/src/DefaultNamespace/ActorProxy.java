package DefaultNamespace;

public class ActorProxy implements DefaultNamespace.Actor {
  private String _endpoint = null;
  private DefaultNamespace.Actor actor = null;
  
  public ActorProxy() {
    _initActorProxy();
  }
  
  public ActorProxy(String endpoint) {
    _endpoint = endpoint;
    _initActorProxy();
  }
  
  private void _initActorProxy() {
    try {
      actor = (new DefaultNamespace.ActorServiceLocator()).getActor();
      if (actor != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)actor)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)actor)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (actor != null)
      ((javax.xml.rpc.Stub)actor)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public DefaultNamespace.Actor getActor() {
    if (actor == null)
      _initActorProxy();
    return actor;
  }
  
  public java.lang.String addActor(java.lang.String actor0) throws java.rmi.RemoteException{
    if (actor == null)
      _initActorProxy();
    return actor.addActor(actor0);
  }
  
  
}