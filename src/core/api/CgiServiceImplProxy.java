package api;

public class CgiServiceImplProxy implements api.CgiServiceImpl {
  private String _endpoint = null;
  private api.CgiServiceImpl cgiServiceImpl = null;
  
  public CgiServiceImplProxy() {
    _initCgiServiceImplProxy();
  }
  
  public CgiServiceImplProxy(String endpoint) {
    _endpoint = endpoint;
    _initCgiServiceImplProxy();
  }
  
  private void _initCgiServiceImplProxy() {
    try {
      cgiServiceImpl = (new api.ApiServiceLocator()).getApiServiceImplPort();
      if (cgiServiceImpl != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cgiServiceImpl)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cgiServiceImpl)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cgiServiceImpl != null)
      ((javax.xml.rpc.Stub)cgiServiceImpl)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public api.CgiServiceImpl getCgiServiceImpl() {
    if (cgiServiceImpl == null)
      _initCgiServiceImplProxy();
    return cgiServiceImpl;
  }
  
  public java.lang.String cgi(java.lang.String params) throws java.rmi.RemoteException{
    if (cgiServiceImpl == null)
      _initCgiServiceImplProxy();
    return cgiServiceImpl.cgi(params);
  }
  
  
}