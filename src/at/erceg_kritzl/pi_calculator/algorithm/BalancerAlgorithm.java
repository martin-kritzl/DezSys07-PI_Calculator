package at.erceg_kritzl.pi_calculator.algorithm;

import java.rmi.RemoteException;

public interface BalancerAlgorithm {

	public abstract String getServerName() throws RemoteException;

	public abstract void releaseServer(String name);
}