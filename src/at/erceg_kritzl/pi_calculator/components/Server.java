package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.control.Main;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Ist fuer die Berechnung von pi zustaendig. Dabei meldet sich der Server bei einem Balancer an, um seine
 * Dienste zu Verfuegung zu stellen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject implements Calculator, Runnable, Serializable{
	private Calculator alg;

	private String name;
	private String balancerUriName;
	private ServiceManager sm;

	private static final Logger logger = LogManager.getLogger(Server.class);

	/**
	 * Der Server registriert sich in der Registry des Balancers
	 *
	 * @param registryUri URI eines Calculators in dem die Registry liegt
	 * @param registryName Name des Eintrags in der Registry
	 * @param alg Algorithmus der tatsaechlich Pi ausrechnet
	 * @param name Name des Servers der in der Registry abgelegt wird
	 * @param port Port unter dem der Server erreichbar sein soll
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public Server(URI registryUri, String registryName, Calculator alg, String name, int port)
			throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {

		this.name = name;
		this.alg = alg;
		this.balancerUriName = registryUri.toString()+"/"+registryName;
		
		/* Damit Verbindungen zugelassen werden, wird am Anfang eine Policy angegeben. */
		
		if (System.getSecurityManager() == null) {
            
			try {
				System.setProperty("java.security.policy",System.class.getResource("/java.policy").toString());
			}catch(Exception e){
				logger.info("policy file: java.policy was not found or could not be set as property");
			}
            System.setSecurityManager(new SecurityManager());
        }
		

		sm = (ServiceManager) Naming.lookup(this.balancerUriName);
		logger.info(name + " hat sich bei " + registryName + " unter " + this.balancerUriName + " angemeldet.");
		if (!sm.getService().addServer(this.name, this))
			throw new AlreadyBoundException();
		sm.getService().addServer(this.name, this);
		new Thread(this).setDaemon(true);
	}

	/**
	 *
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return alg.pi(anzNachkommastellen);
	}

	/**
	 * Ist fuer das Abmelden beim Balancer zustaendig.
	 */
	@Override
	public void run() {
		try {
			this.sm.getService().removeServer(this.name);
			logger.info(this.getName() + " hat sich bei " + this.balancerUriName + " ab.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
