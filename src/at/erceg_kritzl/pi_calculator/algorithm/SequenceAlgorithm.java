package at.erceg_kritzl.pi_calculator.algorithm;

import java.util.ArrayList;
import java.util.List;

import at.erceg_kritzl.pi_calculator.service.Service;

public class SequenceAlgorithm implements BalancerAlgorithm {

	private Service service;

	public SequenceAlgorithm(Service service) {

		this.service = service;

	}

	/**
	 * @see at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm#getServerName()
	 * 
	 * 
	 */
	public String getServerName() {

		List<String> availableServers = new ArrayList<String>();

		if (this.service.getServerNames().size() > 0) {
			for (int i = 0; i < this.service.getServerNames().size(); i++) {
				availableServers.addAll(this.service.getServerNames());
				availableServers.remove();
			}
		} else {
			System.out.println("Kein Server momentan vorhanden");
		}

		return null;
	}

}