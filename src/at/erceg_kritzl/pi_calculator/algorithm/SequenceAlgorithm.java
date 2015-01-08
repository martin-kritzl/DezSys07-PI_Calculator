package at.erceg_kritzl.pi_calculator.algorithm;

import at.erceg_kritzl.pi_calculator.service.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	 * 
	 * 
	 */
	public String getServerName() throws RemoteException {

		System.out.println(this.availableServers);

		this.synchronize();

		if (this.availableServers.size()>0) {
			String out = new ArrayList<String>(this.availableServers).get(0);
			this.availableServers.remove(out);
			return out;
		}else
			return null;
	}

	public void releaseServer(String name) {
		this.availableServers.add(name);
	}


	public void synchronize() throws RemoteException {
		for (String name : this.service.getServerNames()) {
			if (!this.allServers.contains(name)) {
				this.allServers.add(name);
				this.availableServers.add(name);
			}
		}

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