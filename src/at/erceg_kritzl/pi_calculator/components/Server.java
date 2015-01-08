package at.erceg_kritzl.pi_calculator.components;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import at.erceg_kritzl.pi_calculator.service.Service;

@SuppressWarnings("serial")
public class Server implements Calculator, Runnable, Serializable {

	private Service service;
	
	private Calculator alg;

	private String name;

	public Server(Service service, Calculator alg, String name, int port) throws RemoteException {

		this.service = service;
		this.name = name;
		
		/* Damit Verbindungen zugelassen werden, wird am Anfang eine Policy angegeben. */
		
		if (System.getSecurityManager() == null) {
            
			try {
				System.setProperty("java.security.policy",System.class.getResource("/java.policy").toString());
			}catch(Exception e){
				System.err.println("policy file: java.policy was not found or could not be set as property");
			}
            System.setSecurityManager(new SecurityManager());
        }
		
		this.alg = (Calculator) UnicastRemoteObject.exportObject(this, port);
		
		
	}

	public String getName() {
		return this.name;
	}

	public Calculator getAlorithm() {
		return this.alg;
	}


	/**
	 * @see components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return alg.pi(anzNachkommastellen);
	}

	@Override
	public void run() {
		try {
			this.service.removeServer(this.name);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
