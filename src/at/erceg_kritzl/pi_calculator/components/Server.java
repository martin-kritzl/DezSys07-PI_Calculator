package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.control.Main;

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
public class Server extends UnicastRemoteObject implements Calculator, Runnable, Serializable{

	//public static final Logger logger = LogManager.getLogger(Server.class);
	private Calculator alg;

	private String name;
	private String balancerUriName;
	private ServiceManager sm;
	
	public Server(URI balancerUri, String balancerName, Calculator alg, String name, int port) 
			throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {

		this.name = name;
		this.alg = alg;
		this.balancerUriName = balancerUri.toString()+"/"+balancerName;
		
		/* Damit Verbindungen zugelassen werden, wird am Anfang eine Policy angegeben. */
		
		if (System.getSecurityManager() == null) {
            
			try {
				System.setProperty("java.security.policy",System.class.getResource("/java.policy").toString());
			}catch(Exception e){
				Main.logger.info("policy file: java.policy was not found or could not be set as property");
			}
            System.setSecurityManager(new SecurityManager());
        }
		

		sm = (ServiceManager) Naming.lookup(this.balancerUriName);
		Main.logger.info(name + " hat sich bei Balancer " + balancerName + " unter " + this.balancerUriName + " angemeldet.");
		sm.getService().addServer(this.name, this);
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
			Main.logger.info(this.getName() + " hat sich bei " + this.balancerUriName + " ab.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
