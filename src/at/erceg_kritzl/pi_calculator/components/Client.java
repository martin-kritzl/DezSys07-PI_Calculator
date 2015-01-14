package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.control.Main;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Fragt von einem Calculator Pi ab und gibt dies aus.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public class Client implements Runnable {

	private int nachkommastellen;
	
	private Calculator calc;

	private static final Logger logger = LogManager.getLogger(Client.class);

	/**
	 * Verbindet sich mit dem Calculator und bekommt die Refernez des berechnenden Objekts.
	 *
	 * @param registryUri URI eines Calculators in dem die Registry liegt
	 * @param registryName Name des Eintrags in der Registry
	 * @param anzNachkommastellen Erwartete Nachkommastellen von pi
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Client(URI registryUri, String registryName, int anzNachkommastellen)
			throws MalformedURLException, RemoteException, NotBoundException {

		/* Damit Verbindungen zugelassen werden, wird am Anfang eine Policy angegeben. */
		
        if (System.getSecurityManager() == null) {
			
        	try {
				System.setProperty("java.security.policy",System.class.getResource("/java.policy").toString());
			}catch(Exception e){
				logger.error("policy file: java.policy was not found or could not be set as property");
			}
        	System.setSecurityManager(new SecurityManager());
        }

		//Die Refeferenz des Balancers wird ermittelt.
        this.calc = (Calculator) Naming.lookup(registryUri.toString() + "/" + registryName);
		logger.info("Client fragt Pi von " + registryUri.toString() + "/" + registryName + ".");
		this.nachkommastellen = anzNachkommastellen;
        
	}

	/**
	 * Berechnet pi mit dem erhaltenen Calculator
	 *
	 * @param anzNachkommastellen Erwartete Nachkommastellen von pi
	 * @return Berechnetes Pi
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	private BigDecimal getPi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return calc.pi(anzNachkommastellen);
	}

	/**
	 * Fragt den Calculator nach Pi. Bei der Rueckgabe von null, wird die Anfrage erneut gesendet.
	 */

	public void run() {
		long timeout = 3000;
		while(true) {
			try {
				//Pi wird erfragt
				BigDecimal pi = getPi(this.nachkommastellen);
				if (pi != null) {
					System.out.println(pi);
					break;
					//Sollte Pi nicht ermittelt werden koennen wird eine erneute Anfrage erstellt.
				} else {
					synchronized (this) {
						this.wait(timeout);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				logger.error("Die Verbindung konnte nicht aufgebaut werden");
				System.exit(-1);
			} catch (NotBoundException e) {
				logger.error("Der Balancer konnte unter der URI nicht erreicht werden");
				System.exit(-1);
			}
		}

	}
}
