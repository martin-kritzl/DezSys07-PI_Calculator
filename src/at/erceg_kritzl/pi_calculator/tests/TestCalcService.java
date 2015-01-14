package at.erceg_kritzl.pi_calculator.tests;

/**
 * In dieser Testklasse wird die Klasse 'CalcServer' getestet. Dabei werden die Methoden getestet, mit der Server zu einem Service
 * hinzugefuegt und mit der Server von einem Service entfernt werden koennen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150114
 */
import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import at.erceg_kritzl.pi_calculator.algorithm.CalculatorAlgorithm;
import at.erceg_kritzl.pi_calculator.components.Calculator;
import at.erceg_kritzl.pi_calculator.service.CalcService;
import at.erceg_kritzl.pi_calculator.service.Service;

public class TestCalcService {

	private Service serv;
	private Calculator calc;
	
	/**
	 * In der Before-Methode werden ein Service und ein Calculator initialisiert.
	 * @throws RemoteException
	 */
	
	@Before
	public void initialize() throws RemoteException {
		
		serv = new CalcService();
		calc = new CalculatorAlgorithm();
	
	}
	
	/**
	 * Bei dieser Methode werden 3 Server zu einem Service hinzugefuegt. Danach wird mittels der Methode "getServerNames()" ueberprueft,
	 * ob sich die 3 Server nun in dem Set befinden.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	
	@Test
	public void testAddServer() throws RemoteException, AlreadyBoundException {
		
		serv.addServer("server1", calc);
		serv.addServer("server2", calc);
		serv.addServer("server3", calc);
		assertEquals(3, serv.getServerNames().size());
	
	}
	
	/**
	 * Bei dieser Methode wird 1 Server zu einem Service hinzugefuegt. Moechte man danach einen weiteren Server mit demselben Namen zu dem
	 * Service hinzufuegen, liefert die Methode "addServer()" 'false' zurueck.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	
	@Test
	public void testAddServerWithExistingName() throws RemoteException, AlreadyBoundException {
		
		serv.addServer("server1", calc);
		assertEquals(false, serv.addServer("server1", calc));
	
	}
	
	/**
	 * 
	 * Bei dieser Methode werden 3 Server zu einem Service hinzugefuegt. Danach wird Server 2 von dem Service entfernt. Wenn man die
	 * Anzahl der Server in dem Set mit der Methode "getServerNames()" ausliest, sollten sich nur 2 Server in dem Set befinden.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws NotBoundException
	 */
	
	@Test
	public void testRemoveServer() throws RemoteException, AlreadyBoundException, NotBoundException {
		
		serv.addServer("server1", calc);
		serv.addServer("server2", calc);
		serv.addServer("server3", calc);
		serv.removeServer("server2");
		assertEquals(2, serv.getServerNames().size());
		
	}
	
	/**
	 * Bei dieser Methode wird 1 Server zu einem Service hinzugefuegt. Falls man einen Server entfernen moechte, der nicht existiert
	 * bzw. dessen Name ungueltig ist, liefert die Methode "removeServer()" 'false' zurueck.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws NotBoundException
	 */
	
	@Test
	public void testRemoveServer_InvalidName() throws RemoteException, AlreadyBoundException, NotBoundException {
		
		serv.addServer("server1", calc);
		assertEquals(false, serv.removeServer("server5000"));
		
	}
	
	/**
	 * Bei dieser Methode wird 1 Server zu einem Service hinzugefuegt. Mittels der Methode "getServer()" wird eine Referenz auf eine
	 * Serverreferenz zurueckgegeben, womit die Methode "pi(nachkommastellen)" ausgefuehrt werden kann. Getestet wird, ob bei der Eingabe
	 * der gewuenschten Nachkommastellen von Pi das korrekte Ergebnis herauskommt.
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws NotBoundException
	 */
	
	@Test
	public void testGetServerReference() throws RemoteException, AlreadyBoundException, NotBoundException {
		
		serv.addServer("server1", calc);
		assertEquals(3.141, serv.getServer("server1").pi(3).doubleValue(), 0.001);	
		
	}
	
	

}
