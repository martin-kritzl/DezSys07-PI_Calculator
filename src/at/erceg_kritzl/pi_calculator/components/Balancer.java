package at.erceg_kritzl.pi_calculator.components;

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

public class Balancer implements Calculator, ServiceManager {

	private BalancerAlgorithm alg;

	private Service service;

	private Registry registry;

	private String name;

	private BalancerAlgorithm balancerAlgorithm;

	public Balancer(String name, int port) throws RemoteException, AlreadyBoundException {
		this.name = name;
		this.registry = LocateRegistry.createRegistry(port);
		this.registry.bind(this.name, this);
		this.service = new CalcService();
	}

	public Service getService() {
		return this.service;
	}

	public void bind() throws RemoteException {
		this.registry.rebind(this.name, this);
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		//String freeServer = this.alg.getServerName();
		String freeServer = "server1";
		System.out.println(this.service.getServer(freeServer).toString());
		return this.service.getServer(freeServer).pi(anzNachkommastellen);
	}

}
