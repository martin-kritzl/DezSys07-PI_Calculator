package at.erceg_kritzl.pi_calculator.components;

import java.math.BigDecimal;
import java.rmi.Remote;

public interface Calculator extends Remote {

	public abstract BigDecimal pi(int anzNachkommastellen);

}
