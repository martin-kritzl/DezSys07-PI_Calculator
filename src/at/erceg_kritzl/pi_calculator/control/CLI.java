package at.erceg_kritzl.pi_calculator.control;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.Messages;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Parst die Eingaben des Benutzers und gibt bei einem Fehler die korrekte Verwendung aus
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public class CLI implements Input {

	@Option(name = "-c", usage = "specify the number of clients <default: 0")
	private Integer countClients = 0;

	@Option(name = "-d", usage = "specify the number of decimal digits from pi <default: 10>")
	private Integer digits = 10;

	@Option(name = "-s", usage = "specify the names of the server(s) [server1,server2,...] <default: no Server>")
	private String servers;

	@Option(name = "-b", usage = "specify the location of the balancer <default: rmi://127.0.0.1:60000/Balancer>")
	private String balancerUriString = "rmi://127.0.0.1:60000/Balancer";

	@Option(name = "-n", usage = "specify if a new balancer should been build <default: false>")
	private String newBalancerString = "false";

	private boolean newBalancer = false;
	private URI balancerUri;
	private String balancerName;

	private static final Logger logger = LogManager.getLogger(CLI.class);


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
			String uri = this.balancerUriString.substring(0, this.balancerUriString.lastIndexOf("/"));
			String name = this.balancerUriString.substring(this.balancerUriString.lastIndexOf("/")+1, this.balancerUriString.length());
			if (uri.indexOf("rmi://")==-1 || name==null)
				throw new CmdLineException(parser, Messages.DEFAULT_META_EXPLICIT_BOOLEAN_OPTION_HANDLER, this.balancerUriString);
			this.balancerName = this.balancerUriString.substring(this.balancerUriString.lastIndexOf("/")+1, this.balancerUriString.length());
			this.balancerUri = new URI(this.balancerUriString.substring(0, this.balancerUriString.lastIndexOf("/")));

		} catch (CmdLineException e) {
			// if something went wrong the help is printed

			this.inputError(e, parser);
			//System.exit(0);
		} catch (URISyntaxException e) {
			this.inputError(e, parser);
			//System.exit(0);
		}
	}

	/**
	 * Gibt die korrekte Verwendung des Programms aus.
	 *
	 * @param e Ausgeloeste Exception
	 * @param parser Comand Line Parser
	 */
	private void inputError(Exception e, CmdLineParser parser) {
		logger.info("java -jar CalcPi [options...] arguments...");
		parser.printUsage(System.err);
		//System.exit(1);
	}

	/**
	 * @see Input#getCountClients()
	 */
	public int getCountClients() {
		return this.countClients;
	}

	@Override
	public int getDigits() {
		return this.digits;
	}

	/**
	 * @see Input#getBalancerUri()
	 */
	@Override
	public URI getBalancerUri() {
		return this.balancerUri;
	}

	/**
	 * @see Input#getBalancerName()
	 */
	public String getBalancerName() {
		return this.balancerName;
	}

	/**
	 * @see Input#getServers()
	 */
	public List<String> getServers() {
		if (this.servers!=null)
			return Arrays.asList(this.servers.split(","));
		else
			return null;
	}

	/**
	 * @see Input#isNewBalancer()
	 */
	public boolean isNewBalancer() {
		return this.newBalancer;
	}
}
