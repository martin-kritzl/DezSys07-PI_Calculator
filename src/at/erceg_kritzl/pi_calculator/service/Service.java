package at.erceg_kritzl.pi_calculator.service;

import at.erceg_kritzl.pi_calculator.components.Calculator;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Stellt ein Service dar, bei dem Calculator verwaltet werden.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public interface Service extends Remote{

	/**
	 * Fuegt einen neuen Server in die Registry ein
	 *
	 * @param name Name des Servers
	 * @param calc Objectreferenz auf den Server
	 * @return Ob der Server hinzugefuegt werden konnte
	 */
	public boolean addServer(String name, Calculator calc) throws RemoteException, AlreadyBoundException;

	/**
	 * Loescht einen Server aus der Registry.
	 *
	 * @param name Name des zu loeschenden Servers
	 * @return Ob der Server geloescht werden konnte
	 */
	public boolean removeServer(String name) throws RemoteException, NotBoundException;


	/**
	 * Gibt eine Liste der Namen der eingetragenen Server zurueck.
	 *
	 * @return Liste der Namen der Server
	 */
	public Set<String> getServerNames() throws RemoteException;

}
