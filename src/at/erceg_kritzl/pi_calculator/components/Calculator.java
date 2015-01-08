package at.erceg_kritzl.pi_calculator.components;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote, Serializable {

	public abstract BigDecimal pi(int anzNachkommastellen) throws RemoteException, NotBoundException;

}
