package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.service.Service;
import at.erceg_kritzl.pi_calculator.algorithm.BalancerAlgorithm;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Balancer implements Calculator, ServiceManager {

	private BalancerAlgorithm alg;

	private Service service;

	private BalancerAlgorithm balancerAlgorithm;

	public Balancer(Service service, int port) throws RemoteException {
		this.service = service;
		Registry registry = LocateRegistry.createRegistry(port);
	}

	public Service getService() {
		return this.service;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return this.service.getServer(this.alg.getServerName()).pi(anzNachkommastellen);
	}

}
