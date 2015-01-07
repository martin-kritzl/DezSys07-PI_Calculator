package at.erceg_kritzl.pi_calculator.service;

import java.net.URI;
import java.util.List;
import at.erceg_kritzl.pi_calculator.components.Calculator;

public class CalcService implements Service {

	private List<String> servers;


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#addServer(java.lang.String, at.erceg_kritzl.pi_calculator.components.Calculator)
	 */
	public boolean addServer(String name, Calculator calc) {
		return false;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#removeServer(java.lang.String)
	 */
	public boolean removeServer(String name) {
		return false;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServer(java.lang.String)
	 */
	public Calculator getServer(String name) {
		return null;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getServerNames()
	 * 
	 *  
	 */
	public List<String> getServerNames() {
		return null;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.service.Service#getUri()
	 */
	public URI getUri() {
		return null;
	}

}
