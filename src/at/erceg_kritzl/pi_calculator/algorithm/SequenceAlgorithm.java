package at.erceg_kritzl.pi_calculator.algorithm;

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
		
		if (this.service.getServerNames().size() > 0) {
			for(int i = 0; i < this.service.getServerNames().size(); i++) {
				return this.service.getServerNames().get(i);
			} 
		} else {
			System.out.println("Kein Server momentan vorhanden");
		}
	
	}

}