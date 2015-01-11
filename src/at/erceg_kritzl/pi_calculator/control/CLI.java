package at.erceg_kritzl.pi_calculator.control;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.Messages;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class CLI implements Input {

	@Option(name = "-c", usage = "specify the number of clients")
	private Integer countClients = 0;

	@Option(name = "-d", usage = "specify the number of decimal digits from pi")
	private Integer digits = 10;

	@Option(name = "-s", usage = "specify the names of the server(s) [server1,server2,...]")
	private String servers;

	@Option(name = "-b", usage = "specify the location of the balancer [rmi://127.0.0.1:60000]")
	private String balancerUriString = "rmi://127.0.0.1:60000";

	@Option(name = "-n", usage = "specify if a new balancer should been build")
	private String newBalancerString = "false";

	private boolean newBalancer = false;
	private URI balancerUri;


	/**
	 * @see Input#parseArgs(java.lang.String[])
	 */

	public void parseArgs(String[] args) {
		// initialize a new CmdLineParser with CLI
		CmdLineParser parser = new CmdLineParser(this);

		try {
			// pars the args
			parser.parseArgument(args);

			if (this.newBalancerString.equals("true"))
				this.newBalancer = true;
			else if (!this.newBalancerString.equals("false"))
				throw new CmdLineException(parser, Messages.DEFAULT_META_EXPLICIT_BOOLEAN_OPTION_HANDLER, this.newBalancerString);

			this.balancerUri = new URI(this.balancerUriString);

		} catch (CmdLineException e) {
			// if something went wrong the help is printed

			this.inputError(e, parser);
		} catch (URISyntaxException e) {
			this.inputError(e, parser);
		}
	}

	private void inputError(Exception e, CmdLineParser parser) {
		System.err.println("java -jar CalcPi [options...] arguments...");
		parser.printUsage(System.err);
		System.err.println();
		System.exit(1);
	}

	@Override
	public int getCountClients() {
		return this.countClients;
	}

	@Override
	public int getDigits() {
		return this.digits;
	}

	/**
	 * Macht aus der Angabe des Benutzers eine Uri und gibt diese zurueck
	 *
	 * @return URI
	 */
	@Override
	public URI getBalancerUri() {
		return this.balancerUri;
	}

	/**
	 * Erstellt aus dem eingegebenen String des Benutzers, eine Liste der Servernamen
	 *
	 * @return Liste der Servernamen
	 */
	@Override
	public List<String> getServers() {
		if (this.servers!=null)
			return Arrays.asList(this.servers.split(","));
		else
			return null;
	}

	/**
	 * Gibt zurueck, ob ein neuer Balancer erstellt werden soll
	 *
	 * @return boolean
	 */
	@Override
	public boolean isNewBalancer() {
		return this.newBalancer;
	}
}
