package at.erceg_kritzl.pi_calculator.algorithm;

import at.erceg_kritzl.pi_calculator.service.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SequenceAlgorithm implements BalancerAlgorithm {

	private Service service;
	private Set<String> freeServers, allServers;

	public SequenceAlgorithm(Service service) {

		this.service = service;
		this.freeServers = new HashSet<String>(service.getServerNames());
		this.allServers = new HashSet<String>(service.getServerNames());

	}

	/**
	 * @see at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm#getServerName()
	 * 
	 * 
	 */
	public String getServerName() {

//		List<String> availableServers = new ArrayList<String>();
//
//		if (this.service.getServerNames().size() > 0) {
//			for (int i = 0; i < this.service.getServerNames().size(); i++) {
//				availableServers.addAll(this.service.getServerNames());
//				availableServers.remove();
//			}
//		} else {
//			System.out.println("Kein Server momentan vorhanden");
//		}
//
		for (String name : this.service.getServerNames()) {
			if (!this.allServers.contains(name)) {
				this.allServers.add(name);
				this.freeServers.add(name);
			}
		}

		Set del = new HashSet<String>();

		for (String name : this.allServers) {
			if (!this.service.getServerNames().contains(name)) {
				del.add(name);
			}
		}

		this.allServers.removeAll(del);
		this.freeServers.removeAll(del);


		return new ArrayList<String>(this.freeServers).get(0);
	}

	public void releaseServer(String name) {
		this.freeServers.add(name);
	}

}