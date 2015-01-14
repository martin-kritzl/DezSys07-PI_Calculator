package at.erceg_kritzl.pi_calculator.tests;

/**
 * In dieser Testklasse wird die Klasse 'SequenceAlgorithm' getestet. Dabei werden die Methoden getestet, bei der ein verfuegbarer Server
 * auf beschaeftigt gesetzt und bei der ein bestimmter Server wieder fuer neue Anfragen freigegeben wird.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150114
 */
import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm;
import at.erceg_kritzl.pi_calculator.algorithm.CalculatorAlgorithm;
import at.erceg_kritzl.pi_calculator.algorithm.SequenceAlgorithm;
import at.erceg_kritzl.pi_calculator.components.Calculator;
import at.erceg_kritzl.pi_calculator.service.CalcService;
import at.erceg_kritzl.pi_calculator.service.Service;

public class TestSequenceAlgorithm {

	private Service serv;
	private BalancerAlgorithm alg;
	private Calculator calc;
	
	/**
	 * In der Before-Methode werden ein Service, ein BalancerAlgorithm und ein Calculator initialisiert.
	 * @throws RemoteException
	 */
	
	@Before
	public void initialize() throws RemoteException {
		
		serv = new CalcService();
		alg = new SequenceAlgorithm(serv);
		calc = new CalculatorAlgorithm();
	
	}
	
	/**
	 * Bei dieser Methode wird ein Server zu einem Service hinzugefuegt. Dieser wird auf beschaeftigt gesetzt und danach wieder fuer neue
	 * Anfragen freigegeben. Moechte ich mir wieder einen Server fuer eine Beschaeftigung holen, wird dieser Server auf beschaeftigt gesetzt,
	 * da er der einzige momentan verfuegbare ist.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	
	@Test
	public void testGetServerName_ReleaseServer() throws RemoteException, AlreadyBoundException {
		
		serv.addServer("server1", calc);
		alg.getServerName();
		alg.releaseServer("server1");
		assertEquals("server1", alg.getServerName());	
	
	}
	
	/**
	 * Falls kein Server verfuegbar ist, wird von der Methode "getServerName()" null zurueckgegeben.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	
	@Test
	public void testGetServerName_NoServer() throws RemoteException, AlreadyBoundException {
		assertEquals(null, alg.getServerName());
	}
	
	/**
	 * Bei dieser Methode werden 3 Server zu einem Service hinzugefuegt. Danach wird einer auf beschaeftigt gesetzt und Server 2 vom
	 * Service abgemeldet. Falls ein neuer Server mit der Methode "getServerName()" geholt wird, wird Server 1 uebergeben, da dieser der
	 * einzige momentan verfuegbare ist.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws NotBoundException
	 */
	
	@Test
	public void testSynchronize_delFromService() throws RemoteException, AlreadyBoundException, NotBoundException {
		
		serv.addServer("server1", calc);
		serv.addServer("server2", calc);
		serv.addServer("server3", calc);
		alg.getServerName();
		serv.removeServer("server2");
		assertEquals("server1", alg.getServerName());
	
	}

}
