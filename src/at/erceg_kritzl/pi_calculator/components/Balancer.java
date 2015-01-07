package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.service.Service;
import at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Balancer implements Calculator {

	private BalancerAlgorithm alg;

	private Service service;

	private BalancerAlgorithm balancerAlgorithm;

	public Balancer(Service service) {
		this.service = service;
	}

	public Service getService() {
		return this.service;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return this.service.getServer(this.alg.getServerName()).pi(anzNachkommastellen);
	}

}
