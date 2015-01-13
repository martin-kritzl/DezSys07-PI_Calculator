package at.erceg_kritzl.pi_calculator.algorithm;

import java.rmi.RemoteException;

/**
 * Stellt den Algoritmus zur Berechnung des besten Servers dar.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public interface BalancerAlgorithm {

	/**
	 * Gibt einen verfuegbaren Client zurueck und setzt diesen fuer andere Aufrufe auf beschaeftigt.
	 *
	 * @return Gibt einen verfuegbaren Server zurueck
	 * @throws RemoteException
	 */
	public abstract String getServerName() throws RemoteException;

	/**
	 * Gibt einen bestimmten Server wieder fuer andere Anfragen frei.
	 *
	 * @param name Name des Servers der wieder freigegeben werden soll.
	 */
	public abstract void releaseServer(String name);
}