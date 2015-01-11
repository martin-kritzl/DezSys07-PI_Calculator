package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.control.Main;

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
				Main.logger.error("policy file: java.policy was not found or could not be set as property");
			}
        	System.setSecurityManager(new SecurityManager());
        }

        this.calc = (Calculator) Naming.lookup(balancerUri.toString() + "/" + balancerName);
		Main.logger.info("Client fragt Pi von " + balancerUri.toString() + "/" + balancerName + ".");
		this.nachkommastellen = anzNachkommastellen;
        
	}

	public BigDecimal getPi(int anzNachkommastellen) throws RemoteException, NotBoundException {
		return calc.pi(anzNachkommastellen);
	}

	@Override
	public void run() {
		long timeout = 3000;
		try {
			while(true) {
				BigDecimal pi = getPi(this.nachkommastellen);
				if (pi!=null) {
					System.out.println(pi);
					break;
				} else {
					synchronized (this){
						this.wait(timeout);
					}
				}
			}


		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
