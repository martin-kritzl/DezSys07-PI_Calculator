package at.erceg_kritzl.pi_calculator.tests;

import at.erceg_kritzl.pi_calculator.algorithm.CalculatorAlgorithm;
import at.erceg_kritzl.pi_calculator.components.Calculator;
import org.junit.Before;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

/**
 * In dieser Testklasse wird die Klasse 'CalculatorAlgorithm' getestet. Dabei wird die
 * Methode zum Berechnen von Pi getestet.
 *
 * @author Stefan Erceg
 * @author Martin Kritzl
 * @version 20150114
 */
public class TestCalculatorAlgorithm {

    private Calculator calcAlg;

    /**
     * Erstellen des Objects CalculatorAlgorithm
     */
    @Before
    public void initialize() {
        this.calcAlg = new CalculatorAlgorithm();
    }

    /**
     * Brechnen von Pi mit 5 Nachkommastellen
     *
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Test
    public void testGetShortPi() throws RemoteException, NotBoundException {
        assertEquals(3.14159, this.calcAlg.pi(5).doubleValue(), 0.0000000001);
    }

    /**
     * Berechnen von Pi mit 14 Nachkommastellen
     *
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Test
    public void testGetLongPi() throws RemoteException, NotBoundException {
        assertEquals(3.14159265358979, this.calcAlg.pi(14).doubleValue(), 0.0000000001);
    }


}
