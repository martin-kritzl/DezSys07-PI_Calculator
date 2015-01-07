package at.erceg_kritzl.pi_calculator.service;

import at.erceg_kritzl.pi_calculator.components.Balancer;
import at.erceg_kritzl.pi_calculator.components.Calculator;

import java.net.URI;
import java.util.List;

public interface Service {

	public boolean addServer(String name, Calculator calc);

	public boolean removeServer(String name);

	public abstract Calculator getServer(String name);

	public List<String> getServerNames();

	public abstract URI getUri();

}
