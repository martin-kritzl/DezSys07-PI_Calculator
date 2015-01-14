package at.erceg_kritzl.pi_calculator.tests;

/**
 * In dieser Testklasse wird die Klasse 'SequenceAlgorithm' getestet. Dabei werden die Methoden zum 
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
	
	@Before
	public void initialize() throws RemoteException {
		
		serv = new CalcService();
		alg = new SequenceAlgorithm(serv);
		calc = new CalculatorAlgorithm();
	
	}
	
	@Test
	public void testReleaseServer_GetServerName() throws RemoteException, AlreadyBoundException {
		
		serv.addServer("server1", calc);
		serv.addServer("server2", calc);
		alg.releaseServer("server1");
		assertEquals("server2", alg.getServerName());	
	
	}
		
	@Test
	public void testGetServerName_NoServer() throws RemoteException, AlreadyBoundException {
		assertEquals(null, alg.getServerName());
	}
	
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
