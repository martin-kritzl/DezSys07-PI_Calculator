package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.service.Service;

import java.rmi.RemoteException;

/**
 * Stellt das Interface zur Verwendung von Services dar.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public interface ServiceManager extends Calculator{

    /**
     * Gibt das Service des Anbieters zurueck.
     *
     * @return Service des Anbieters.
     * @throws RemoteException
     */
    public abstract Service getService() throws RemoteException;
}
