package at.erceg_kritzl.pi_calculator.control;

import at.erceg_kritzl.pi_calculator.algorithm.CalculatorAlgorithm;
import at.erceg_kritzl.pi_calculator.components.Balancer;
import at.erceg_kritzl.pi_calculator.components.Calculator;
import at.erceg_kritzl.pi_calculator.components.Client;
import at.erceg_kritzl.pi_calculator.components.Server;
import at.erceg_kritzl.pi_calculator.service.CalcService;
import at.erceg_kritzl.pi_calculator.service.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static final Logger logger = LogManager.getLogger(Main.class);
	private static String balancerName = "Balancer";
	private static int serverPort = 45456;

	public static void main(String[] args) {
		Input cli = new CLI();
		cli.parseArgs(args);

		Calculator algorithm = new CalculatorAlgorithm();

		try {
			if (cli.isNewBalancer()) {
				Service service = new CalcService();
				new Balancer(balancerName, cli.getBalancerUri().getPort());
			}
			if (cli.getServers()!=null)
				for (String server : cli.getServers()) {
					Thread t = new Thread(new Server(cli.getBalancerUri(), balancerName, algorithm, server, serverPort++));
					Runtime.getRuntime().addShutdownHook(t);
				}
			if (cli.getCountClients()!=0)
				for (int i = 0; i < cli.getCountClients(); i++) {
					ExecutorService clients = Executors.newFixedThreadPool(cli.getCountClients());
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
//		System.exit(-1);
	}

}
