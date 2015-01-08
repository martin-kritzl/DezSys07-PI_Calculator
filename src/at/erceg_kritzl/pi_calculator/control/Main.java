package at.erceg_kritzl.pi_calculator.control;

import at.erceg_kritzl.pi_calculator.algorithm.CalculatorAlgorithm;
import at.erceg_kritzl.pi_calculator.components.Client;
import at.erceg_kritzl.pi_calculator.components.Balancer;
import at.erceg_kritzl.pi_calculator.components.Calculator;
import at.erceg_kritzl.pi_calculator.components.Server;
import at.erceg_kritzl.pi_calculator.service.CalcService;
import at.erceg_kritzl.pi_calculator.service.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	private static String balancerName = "Balancer";
	private static int serverPort = 45456;

	public static void main(String[] args) {
		Input cli = new CLI();
		cli.parseArgs(args);

		Calculator algorithm = new CalculatorAlgorithm();
		ExecutorService clients = Executors.newFixedThreadPool(cli.getCountClients());

		try {
			if (cli.isNewBalancer()) {
				Service service = new CalcService();
				new Balancer(service, balancerName, cli.getBalancerUri().getPort());
			}

			for (String server : cli.getServers()) {
				Thread t = new Thread(new Server(cli.getBalancerUri(), balancerName, algorithm, server, serverPort++));
				Runtime.getRuntime().addShutdownHook(t);
			}

			for (int i = 0; i < cli.getCountClients(); i++) {
				clients.execute(new Client(cli.getBalancerUri(), balancerName, cli.getDigits()));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

}
