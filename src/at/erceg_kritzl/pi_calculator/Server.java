package at.erceg_kritzl.pi_calculator;

public class Server implements Calculator, Serializable {

	private Calculator alg;

	public Server(URI balancerUri, Calculator alg) {

	}


	/**
	 * @see Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) {
		return null;
	}

}
