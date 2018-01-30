/**
 * ApiServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package api;

import com.iptv.core.common.Configuration;

public class ApiServiceLocator extends org.apache.axis.client.Service implements api.ApiService {
	private static final String apiUrl = Configuration.webCfg.getProperty("cfg.webservice.api");

	public ApiServiceLocator() {
	}

	public ApiServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public ApiServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for ApiServiceImplPort
	private java.lang.String ApiServiceImplPort_address = apiUrl;

	@Override
	public java.lang.String getApiServiceImplPortAddress() {
		return ApiServiceImplPort_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String ApiServiceImplPortWSDDServiceName = "ApiServiceImplPort";

	public java.lang.String getApiServiceImplPortWSDDServiceName() {
		return ApiServiceImplPortWSDDServiceName;
	}

	public void setApiServiceImplPortWSDDServiceName(java.lang.String name) {
		ApiServiceImplPortWSDDServiceName = name;
	}

	@Override
	public api.CgiServiceImpl getApiServiceImplPort() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(ApiServiceImplPort_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getApiServiceImplPort(endpoint);
	}

	@Override
	public api.CgiServiceImpl getApiServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			api.ApiServiceImplPortSoapBindingStub _stub = new api.ApiServiceImplPortSoapBindingStub(portAddress, this);
			_stub.setPortName(getApiServiceImplPortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setApiServiceImplPortEndpointAddress(java.lang.String address) {
		ApiServiceImplPort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	@Override
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (api.CgiServiceImpl.class.isAssignableFrom(serviceEndpointInterface)) {
				api.ApiServiceImplPortSoapBindingStub _stub = new api.ApiServiceImplPortSoapBindingStub(
						new java.net.URL(ApiServiceImplPort_address), this);
				_stub.setPortName(getApiServiceImplPortWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	@Override
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("ApiServiceImplPort".equals(inputPortName)) {
			return getApiServiceImplPort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	@Override
	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("api", "ApiService");
	}

	private java.util.HashSet ports = null;

	@Override
	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("api", "ApiServiceImplPort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("ApiServiceImplPort".equals(portName)) {
			setApiServiceImplPortEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
