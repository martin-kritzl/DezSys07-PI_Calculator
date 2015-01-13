package at.erceg_kritzl.pi_calculator.components;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Stellt die Berechnung von pi dar.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public interface Calculator extends Remote, Serializable {

	/**
	 * Berechnet Pi und gibt dies wieder zurueck.
	 *
	 * @param anzNachkommastellen Anzahl der Nachkommastellen die von Pi berechnet werden sollen.
	 * @return Berechnetes PI
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public abstract BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException;

}
