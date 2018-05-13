package gensesis.exam.java;

import java.util.HashMap;
import java.util.Set;

import com.genesis.exams.slot.Evaluator;
import com.genesis.exams.slot.Symbol;

/**
 * @author Daniel Yu
 *
 */
public class ConcreteEvaluator implements Evaluator {
	private HashMap<String, Integer> winningCombination;

	public ConcreteEvaluator(HashMap<String, Integer> winningCombination) {
		try {
		    // check if Bet Multiplier is negative
		    Set<String> keys = winningCombination.keySet();
	        for (String key: keys) {            
	            if (winningCombination.get(key) < 0) {
	                String errorMessage = String.format("The bet multiplier of %s cannot be negative", key);
	                throw new IllegalArgumentException(errorMessage);
	            }
	        }

			this.winningCombination = winningCombination;
		}
		catch(IllegalArgumentException e) {
			throw e;
		}
	}

	@Override
	/* evaluate the pay-out based on symbols and bet
	 * @param symbols  the symbol result
	 * @param bet      the bet from users
	 * return          the pay-out. 0 if users do not win. 
	 * @see com.genesis.exams.slot.Evaluator#evaluate(com.genesis.exams.slot.Symbol[], long)
	 */
	public long evaluate(Symbol[] symbols, long bet) {
		try {
		    // check if given symbols are valid
		    Set<String> keys = winningCombination.keySet();
	        for (String key: keys) {            
	            if (key.split(",").length != symbols.length) {
	                String errorMessage = String.format("The symbol number of winning combination key %s does not match the number of given symbols: ", key);
	                throw new IllegalArgumentException(errorMessage);
	            }
	        }

	        // check if bet is negative
	        if (bet < 0) {
	            throw new IllegalArgumentException("Bet cannot be negative");
	        }   

			String key = getKeyOfCombination(symbols);		
			return winningCombination.containsKey(key) ? bet*winningCombination.get(key) : 0;
		}
		catch(IllegalArgumentException e) {
			throw e;
		}		
	}

	private String getKeyOfCombination(Symbol[] symbols) {
		String key = "";
		for (Symbol symbol: symbols) {
			key += symbol.getName() + ",";
		}

		//remove the last ","
		return key.isEmpty() ? key : key.substring(0, key.length()-1);
	}

}
