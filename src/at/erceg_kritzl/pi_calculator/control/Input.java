package at.erceg_kritzl.pi_calculator.control;

import java.util.Map;

public interface Input {

	public Map<String,String> parseArgs(String[] args);

}
