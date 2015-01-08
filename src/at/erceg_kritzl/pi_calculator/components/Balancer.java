package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.algorithm.SequenceAlgorithm;
import at.erceg_kritzl.pi_calculator.service.CalcService;
import at.erceg_kritzl.pi_calculator.service.Service;
import at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Balancer extends UnicastRemoteObject implements ServiceManager{

	private BalancerAlgorithm alg;

	private Service service;

	private Registry registry;

	private String name;

	private BalancerAlgorithm balancerAlgorithm;

	public Balancer(String name, int port) throws RemoteException, AlreadyBoundException {
		this.name = name;
		this.service = new CalcService();
		this.alg = new SequenceAlgorithm(this.service);
		this.registry = LocateRegistry.createRegistry(port);
		this.registry.bind(this.name, this);
	}

	public Service getService() throws RemoteException {
		return this.service;
	}

	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		String availableServer = this.alg.getServerName();
		if (availableServer!=null) {
			BigDecimal erg = this.service.getServer(availableServer).pi(anzNachkommastellen);
			this.alg.releaseServer(availableServer);
			System.out.println(availableServer+" hat pi fuer " + anzNachkommastellen+ " Stellen berechnet.");
			return erg;
		} else
			return null;

	}

}
