package at.erceg_kritzl.pi_calculator.control;

import java.net.URI;
import java.util.List;

/**
 * Stellt die Verarbeitung der Benutzereingaben dar.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150113
 */
public interface Input {

	/**
	 * Speichert die Eingabe des Benutzers in Attribute, um diese dann auslesen zu koennen
	 *
	 * @param args Eingabe des Benutzers
	 */
	public void parseArgs(String[] args);

	public int getCountClients();
	public int getDigits();

	/**
	 * Macht aus der Angabe des Benutzers eine Uri und gibt diese zurueck
	 *
	 * @return URI
	 */
	public URI getBalancerUri();

	public String getBalancerName();

	/**
	 * Erstellt aus dem eingegebenen String des Benutzers, eine Liste der Servernamen
	 *
	 * @return Liste der Servernamen
	 */
	public List<String> getServers();

	/**
	 * Gibt zurueck, ob ein neuer Balancer erstellt werden soll
	 *
	 * @return boolean
	 */
	public boolean isNewBalancer();

}
