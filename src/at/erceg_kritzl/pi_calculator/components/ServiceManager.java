package at.erceg_kritzl.pi_calculator.components;

import at.erceg_kritzl.pi_calculator.service.Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Martin Kritzl on 08.01.2015.
 */
public interface ServiceManager extends Calculator{
    public abstract Service getService() throws RemoteException;
}
