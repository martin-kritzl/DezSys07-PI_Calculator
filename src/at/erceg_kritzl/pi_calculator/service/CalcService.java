package at.erceg_kritzl.pi_calculator.service;

import at.erceg_kritzl.pi_calculator.components.Calculator;

import java.io.Serializable;
import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CalcService extends UnicastRemoteObject implements Service, Serializable {

	private Map<String, Calculator> servers;

	public CalcService() throws RemoteException {
		this.servers = new ConcurrentHashMap<String, Calculator>();
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#addServer(java.lang.String, at.erceg_kritzl.pi_calculator.components.Calculator)
	 */
	public synchronized void addServer(String name, Calculator calc)throws RemoteException {
		this.servers.put(name, calc);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#removeServer(java.lang.String)
	 */
	public void removeServer(String name) throws RemoteException{
		this.servers.remove(name);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServer(java.lang.String)
	 */
	public Calculator getServer(String name) throws RemoteException{
		return this.servers.get(name);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServerNames()
	 * 
	 *  
	 */
	public Set<String> getServerNames() throws RemoteException{
		return this.servers.keySet();
	}
}
