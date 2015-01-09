package at.erceg_kritzl.pi_calculator.components;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class Server implements Calculator, Runnable, Serializable {
	
	private Calculator alg;

	private String name;

	private ServiceManager sm;
	
	public Server(URI balancerUri, String balancerName, Calculator alg, String name, int port) 
			throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {

		this.name = name;
		this.alg = alg;
		
		/* Damit Verbindungen zugelassen werden, wird am Anfang eine Policy angegeben. */
		
		if (System.getSecurityManager() == null) {
            
			try {
				System.setProperty("java.security.policy",System.class.getResource("/java.policy").toString());
			}catch(Exception e){
				System.err.println("policy file: java.policy was not found or could not be set as property");
			}
            System.setSecurityManager(new SecurityManager());
        }
		
		Calculator serverReference = (Calculator) UnicastRemoteObject.exportObject(this, port);

		System.out.println(name+ " will sich bei Balancer "+balancerName+ " unter " + balancerUri.toString()+" anmelden.");
		sm = (ServiceManager) Naming.lookup(balancerUri.toString() + "/" + balancerName);
		System.out.println(name+ " hat sich bei Balancer "+balancerName+ " unter " + balancerUri.toString()+".");
		sm.getService().addServer(this.name, serverReference);
//		System.out.println(this.sm.getService().getServer("server1").toString());
//		ServiceManager sm2 = (ServiceManager) Naming.lookup(balancerUri.toString() + "/" + balancerName);
//		System.out.println(sm2.getService().getServer("server1").toString());

//		Registry registry = LocateRegistry.getRegistry(balancerUri.getHost(), balancerUri.getPort());
//		registry.rebind(balancerName, (Calculator) UnicastRemoteObject.exportObject(this.sm, balancerUri.getPort()));
	}

	public String getName() {
		return this.name;
	}

	public Calculator getAlorithm() {
		return this.alg;
	}


	/**
	 * @see at.erceg_kritzl.pi_calculator.components.Calculator#pi(int)
	 * 
	 *  
	 */
	public BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return alg.pi(anzNachkommastellen);
	}

	@Override
	public void run() {
		try {
			this.sm.getService().removeServer(this.name);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
