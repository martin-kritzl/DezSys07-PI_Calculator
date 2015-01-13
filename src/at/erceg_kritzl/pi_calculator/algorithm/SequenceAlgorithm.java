package at.erceg_kritzl.pi_calculator.algorithm;

import at.erceg_kritzl.pi_calculator.service.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Ermittelt aus einem Service sequentiell Server die verfuegbar sind und zur Berechnung bereit stehen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public class SequenceAlgorithm implements BalancerAlgorithm {

	private Service service;
	private Set<String> availableServers, allServers;

	public SequenceAlgorithm(Service service) throws RemoteException {
		this.service = service;
		this.allServers = new HashSet<String>(service.getServerNames());
		this.availableServers = new HashSet<String>(service.getServerNames());
	}

	/**
	 * @see at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm#getServerName()
	 */
	public synchronized String getServerName() throws RemoteException {
		//Synchronisiert die Liste des Services mit den availableServers
		this.synchronize();

		//Gibt einen der verfuegbaren Server zurueck
		if (this.availableServers.size()>0) {
			String out = new ArrayList<String>(this.availableServers).get(0);
			this.availableServers.remove(out);
			return out;
		}else
			return null;
	}

	/**
	 * @see at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm#releaseServer(String)
	 */
	public synchronized void releaseServer(String name) {
		this.availableServers.add(name);
	}

	/**
	 * Synchronisiert die Liste der Server des Services mit den verfuebaren Servern.
	 *
	 * @throws RemoteException
	 */
	private void synchronize() throws RemoteException {
		/*
		 * Wenn ein Server in dem Service hinzukommt wird dieser in allServers und
		 * availableServers hinzugefuegt.
		 */
		for (String name : this.service.getServerNames()) {
			if (!this.allServers.contains(name)) {
				this.allServers.add(name);
				this.availableServers.add(name);
			}
		}

		/*
		 * Wenn ein Server in dem Service nicht mehr vorhanden ist, wird dieser in allServers
		 * und availableServers hinzugefuegt.
		 */
		Set del = new HashSet<String>();
		for (String name : this.allServers) {
			if (!this.service.getServerNames().contains(name)) {
				del.add(name);
			}
		}
		this.allServers.removeAll(del);
		this.availableServers.removeAll(del);
	}

}