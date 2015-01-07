package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.service.Service;
import at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm;

import java.math.BigDecimal;

public class Balancer implements Calculator {

	private BalancerAlgorithm alg;

	private Service service;

	private BalancerAlgorithm balancerAlgorithm;

	public Balancer(Service service) {

	}

	public Service getService() {
		return null;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) {
		return null;
	}

}
