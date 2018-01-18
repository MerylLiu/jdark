/**
 * ApiService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package api;

public interface ApiService extends javax.xml.rpc.Service {
    public java.lang.String getApiServiceImplPortAddress();

    public api.CgiServiceImpl getApiServiceImplPort() throws javax.xml.rpc.ServiceException;

    public api.CgiServiceImpl getApiServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
