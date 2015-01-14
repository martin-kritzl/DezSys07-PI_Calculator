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

/**
 * Fuehrt das Programm aus
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);
	private static int serverPort = 45456;

	public static void main(String[] args) {
		Input cli = new CLI();
		cli.parseArgs(args);

		Calculator algorithm = new CalculatorAlgorithm();

		try {
			if (cli.isNewBalancer()) {
				new Balancer(cli.getBalancerName(), cli.getBalancerUri().getPort());
			}
			if (cli.getServers()!=null)
				for (String server : cli.getServers()) {
					Thread t = new Thread(new Server(cli.getBalancerUri(), cli.getBalancerName(), algorithm, server, serverPort++));
					//Runtime.getRuntime().addShutdownHook(t);
				}
			if (cli.getCountClients()!=0)
				for (int i = 0; i < cli.getCountClients(); i++) {
					ExecutorService clients = Executors.newFixedThreadPool(cli.getCountClients());
					clients.execute(new Client(cli.getBalancerUri(), cli.getBalancerName(), cli.getDigits()));
				}
		} catch (RemoteException e) {
			logger.error("Die Verbindung konnte nicht aufgebaut werden");
			System.exit(-1);
		} catch (MalformedURLException e) {
			logger.error("Falsche Angabe der URI des Balancers");
			System.exit(-1);
		} catch (NotBoundException e) {
			logger.error("Der Balancer konnte unter der URI nicht erreicht werden");
			System.exit(-1);
		} catch (AlreadyBoundException e) {
			logger.error("Der Name des Servers ist bereits belegt");
			System.exit(-1);
		} catch (UnknownHostException e) {
			logger.error("IP-Adresse des Balancers ist inkorrekt");
			System.exit(-1);
		}
//		System.exit(-1);
	}

}
