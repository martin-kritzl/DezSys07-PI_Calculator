package at.erceg_kritzl.pi_calculator.components;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

	private Calculator calc;
	
	public Client(String balancerIP, int balancerPort) 
			throws MalformedURLException, RemoteException, NotBoundException {

		/* Damit Verbindungen zugelassen werden, wird am Anfang eine Policy angegeben. */
		
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", System.class.getResource("/java.policy").toString());
            System.setSecurityManager(new SecurityManager());
        }
        
        String balancerUri = "rmi://" + balancerIP + ":" + balancerPort + "/Balancer";
        
        this.calc = (Calculator) Naming.lookup(balancerUri);
		
	}

	public BigDecimal getPi(int anzNachkommastellen) {
		return calc.pi(anzNachkommastellen);
	}

}
