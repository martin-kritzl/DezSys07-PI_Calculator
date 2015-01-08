package at.erceg_kritzl.pi_calculator.components;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client implements Runnable {

	private int nachkommastellen;
	
	private Calculator calc;
	
	public Client(URI balancerUri, String balancerName, int anzNachkommastellen)
			throws MalformedURLException, RemoteException, NotBoundException {

		/* Damit Verbindungen zugelassen werden, wird am Anfang eine Policy angegeben. */
		
        if (System.getSecurityManager() == null) {
			
        	try {
				System.setProperty("java.security.policy",System.class.getResource("/java.policy").toString());
			}catch(Exception e){
				System.err.println("policy file: java.policy was not found or could not be set as property");
			}
        	System.setSecurityManager(new SecurityManager());
        }
        
        //String balancerUri = "rmi://" + balancerIP + ":" + balancerPort + "/Balancer";
        
        this.calc = (Calculator) Naming.lookup(balancerUri.toString() + "/" + balancerName);
		this.nachkommastellen = anzNachkommastellen;
        
	}

	public BigDecimal getPi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return calc.pi(anzNachkommastellen);
	}

	@Override
	public void run() {
		try {
			getPi(this.nachkommastellen);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
