package at.erceg_kritzl.pi_calculator.service;

import java.net.URI;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import at.erceg_kritzl.pi_calculator.components.Calculator;

public class CalcService implements Service {

	private List<String> servers;
	private Registry registry;
	private URI uri;

	public CalcService(URI uri) throws RemoteException {
		this.uri = uri;
		this.registry = LocateRegistry.createRegistry(this.getUri().getPort());
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#addServer(java.lang.String, at.erceg_kritzl.pi_calculator.components.Calculator)
	 */
	public void addServer(String name, Calculator calc) throws RemoteException, AlreadyBoundException {
		this.registry.bind(name, calc);
		this.servers.add(name);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#removeServer(java.lang.String)
	 */
	public void removeServer(String name) throws RemoteException, NotBoundException {
		this.registry.unbind(name);
		this.servers.add(name);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServer(java.lang.String)
	 */
	public Calculator getServer(String name) throws RemoteException, NotBoundException {
		if (this.registry.lookup(name) instanceof Calculator)
			return (Calculator) this.registry.lookup(name);
		else
			return null;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServerNames()
	 * 
	 *  
	 */
	public List<String> getServerNames() {
		return this.servers;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getUri()
	 */
	public URI getUri() {
		return this.uri;
	}

}
