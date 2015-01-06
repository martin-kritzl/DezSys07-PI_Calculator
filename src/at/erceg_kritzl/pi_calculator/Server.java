public class Server implements Calculator, Serializable {

	private Calculator alg;

	private String name;

	public Server(URI balancerUri, Calculator alg, String name) {

	}

	public String getName() {
		return null;
	}

	public Calculator getAlorithm() {
		return null;
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
