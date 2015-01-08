package at.erceg_kritzl.pi_calculator.service;

import at.erceg_kritzl.pi_calculator.components.Calculator;

import java.io.Serializable;
import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CalcService implements Service, Serializable {

	private Map<String, Calculator> servers;

	public CalcService() {
		this.servers = new ConcurrentHashMap<String, Calculator>();
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#addServer(java.lang.String, at.erceg_kritzl.pi_calculator.components.Calculator)
	 */
	public synchronized void addServer(String name, Calculator calc) {
		this.servers.put(name, calc);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#removeServer(java.lang.String)
	 */
	public void removeServer(String name) {
		this.servers.remove(name);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServer(java.lang.String)
	 */
	public Calculator getServer(String name) {
		return this.servers.get(name);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServerNames()
	 * 
	 *  
	 */
	public List<String> getServerNames() {
		return new LinkedList<String>(this.servers.keySet());
	}
}
