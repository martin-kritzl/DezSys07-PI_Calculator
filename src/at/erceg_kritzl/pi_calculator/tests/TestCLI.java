package at.erceg_kritzl.pi_calculator.tests;

import at.erceg_kritzl.pi_calculator.algorithm.CalculatorAlgorithm;
import at.erceg_kritzl.pi_calculator.components.Balancer;
import at.erceg_kritzl.pi_calculator.components.Calculator;
import at.erceg_kritzl.pi_calculator.control.CLI;
import at.erceg_kritzl.pi_calculator.control.Input;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * In dieser Testklasse wird die Klasse 'CLI' getestet. Dabei wird eine Benutzereingabe simmuliert und
 * diese getestet.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150114
 */
public class TestCLI {

    private Input input;
    private TestAppender testAppender;

    @Before
    public void initialize() {
        this.input = new CLI();
        this.testAppender = new TestAppender();
        Logger.getRootLogger().addAppender(testAppender);
    }

    @Test
    public void testCorrectEntryAllFields() {
        this.input.parseArgs(new String[]{"-c", "10", "-d", "100", "-s", "server1", "-b", "rmi://127.0.0.1:60000/Balancer", "-n", "true"});
        assertTrue(this.testAppender.getLog().size()==0);
    }

    @Test
    public void testIncorrectEntryOption() {
        this.input.parseArgs(new String[]{"-f", "10", "-d", "100", "-s", "server1", "-b", "rmi://127.0.0.1:60000/Balancer", "-n", "true"});
        assertTrue(this.testAppender.getLog().get(0).getMessage().toString().contains("java -jar "));
    }

    @Test
     public void testIncorrectEntryOptionMinusN() {
        this.input.parseArgs(new String[]{"-n", "falsch"});
        assertTrue(this.testAppender.getLog().get(0).getMessage().toString().contains("java -jar "));
    }

    @Test
    public void testIncorrectEntryNoNameOptionMinusB() {
        this.input.parseArgs(new String[]{"-b", "rmi://127.0.0.1:60000"});
        assertTrue(this.testAppender.getLog().get(0).getMessage().toString().contains("java -jar "));
    }

    @Test
    public void testIncorrectEntryFalsePrefixOptionMinusB() {
        this.input.parseArgs(new String[]{"-b", "abc://127.0.0.1:60000/"});
        assertTrue(this.testAppender.getLog().get(0).getMessage().toString().contains("java -jar "));
    }

    @Test
    public void testCorrectEntryOptionMinusN() {
        this.input.parseArgs(new String[]{"-n", "false"});
        assertTrue(this.testAppender.getLog().size()==0);
    }

    @Test
    public void testGetCountClients() {
        this.input.parseArgs(new String[]{"-c", "10"});
        assertEquals(10, this.input.getCountClients());
    }

    @Test
    public void testGetDigits() {
        this.input.parseArgs(new String[]{"-d", "100"});
        assertEquals(100, this.input.getDigits());
    }

    @Test
    public void testGetBalancerUri() {
        this.input.parseArgs(new String[]{"-b", "rmi://127.0.0.1:60000/Balancer"});
        assertEquals("127.0.0.1", this.input.getBalancerUri().getHost());
        assertEquals(60000, this.input.getBalancerUri().getPort());
    }

    @Test
    public void testGetBalancerName() {
        this.input.parseArgs(new String[]{"-b", "rmi://127.0.0.1:60000/Balancer"});
        assertEquals("Balancer", this.input.getBalancerName());
    }

    @Test
    public void testIsBalancer() {
        this.input.parseArgs(new String[]{"-n", "true"});
        assertTrue(this.input.isNewBalancer());
    }

    @Test
    public void testGetServer() {
        this.input.parseArgs(new String[]{"-s", "server1,server2"});
        assertEquals("server1", this.input.getServers().get(0));
        assertEquals("server2", this.input.getServers().get(1));
    }



    /**
     * Berechnen von Pi mit 14 Nachkommastellen
     *
     * @throws RemoteException
     * @throws NotBoundException
     */
//    @Test
//    public void testGetLongPi() throws RemoteException, NotBoundException {
//        assertEquals(3.14159265358979, this.calcAlg.pi(14).doubleValue(), 0.0000000001);
//    }


}
