package at.erceg_kritzl.pi_calculator;

import java.util.List;

public class CalcService implements Service {

	private List<String> servers;


	/**
	 * @see Service#addServer(java.lang.String, Calculator)
	 */
	public boolean addServer(String name, Calculator calc) {
		return false;
	}


	/**
	 * @see Service#removeServer(java.lang.String)
	 */
	public boolean removeServer(String name) {
		return false;
	}


	/**
	 * @see Service#getServer(java.lang.String)
	 */
	public Calculator getServer(String name) {
		return null;
	}


	/**
	 * @see Service#getServerNames()
	 * 
	 *  
	 */
	public List<String> getServerNames() {
		return null;
	}


	/**
	 * @see Service#Service(URI)
	 * 
	 *  
	 */
	public Service(URI ownUri) {

	}

}
