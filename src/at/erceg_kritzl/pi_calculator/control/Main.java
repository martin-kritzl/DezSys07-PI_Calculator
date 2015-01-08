package at.erceg_kritzl.pi_calculator.control;

import at.erceg_kritzl.pi_calculator.components.Client;
import at.erceg_kritzl.pi_calculator.components.Balancer;
import at.erceg_kritzl.pi_calculator.components.Calculator;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.Naming;

public class Main {

	private Client client;

	private Balancer balancer;

	private Calculator calculator;

	private CLI cli;

	private Input input;

	public static void main(String[] args) {
		Input cli = new CLI();
		cli.parseArgs(args);
		try {
			System.out.println(cli.getBalancerUri());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println(cli.getCountClients());
		System.out.println(cli.getDigits());
		if (cli.getServers() != null) {
			System.out.println(cli.getServers().get(0));
			System.out.println(cli.getServers().get(1));
		}
		System.out.println(cli.isNewBalancer());
	}

}
