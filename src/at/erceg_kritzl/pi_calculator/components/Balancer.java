package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm;
import at.erceg_kritzl.pi_calculator.algorithm.SequenceAlgorithm;
import at.erceg_kritzl.pi_calculator.control.Main;
import at.erceg_kritzl.pi_calculator.service.CalcService;
import at.erceg_kritzl.pi_calculator.service.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Stellt die Verbindung des Servers mit dem Client dar. Das Objekt dieser Klasse wird in
 * die Registry geschrieben, um sie von anderen erreichbar zu machen.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public class Balancer extends UnicastRemoteObject implements ServiceManager{

	private BalancerAlgorithm alg;

	private Service service;

	private Registry registry;

	private String name;

	private volatile int countCalls;

	private static final Logger logger = LogManager.getLogger(Balancer.class);

	/**
	 * Initialisiert einen Service und schreibt sich selber in die Registry
	 *
	 * @param name Name des Balancers
	 * @param port Port an dem die Registry erreichbar sein soll
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws UnknownHostException
	 */
	public Balancer(String name, int port) throws RemoteException, AlreadyBoundException, UnknownHostException {
		this.name = name;
		this.countCalls = 1;
		this.service = new CalcService();
		this.alg = new SequenceAlgorithm(this.service);
		this.registry = LocateRegistry.createRegistry(port);
		this.registry.bind(this.name, this);
		logger.info(name + " hat sich unter " + InetAddress.getLocalHost().getHostAddress() + " angemeldet.");
	}

	/**
	 * @see ServiceManager#getService()
	 */
	public Service getService() throws RemoteException {
		return this.service;
	}

	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		String availableServer;
		BigDecimal erg = null;
		//Ermittelt einen verfuegbaren Server
		while ((availableServer=this.alg.getServerName())!=null){
			try {
				//Berechnet ueber einen Server Pi
				erg = this.service.getServer(availableServer).pi(anzNachkommastellen);
				this.alg.releaseServer(availableServer);
				logger.info(availableServer + " hat pi fuer " + anzNachkommastellen + " Stellen berechnet.(Aufruf nr. " + this.countCalls++ + ")");
				return erg;
			} catch (ConnectException e) {
				//Wenn die Connection zum Server nicht gegeben ist wird dieser entfernt
				this.service.removeServer(availableServer);
			}
		}
		return null;

	}

}
