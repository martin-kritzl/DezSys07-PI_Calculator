package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.algorithm.CalculatorAlgorithm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

public class Server implements Calculator, Serializable {

	private Calculator alg;

	private String name;

	private CalculatorAlgorithm calculatorAlgorithm;

	public Server(URI balancerUri, Calculator alg, String name) {

	}

	public String getName() {
		return null;
	}

	public Calculator getAlorithm() {
		return null;
	}


	/**
	 * @see components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) {
		return null;
	}

}
