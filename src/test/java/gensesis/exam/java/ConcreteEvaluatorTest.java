package gensesis.exam.java;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.genesis.exams.slot.Symbol;

public class ConcreteEvaluatorTest {
	private HashMap<String, Integer> normalWinningCombination;
	private HashMap<String, Integer> negativeWinningCombination;
	private HashMap<String, Integer> mismatchWinningCombination;

	private String winningKey;
	private String negativeMulKey;
	private String mismatchKey;
	
	private Symbol[] winningSymbols;
	private Symbol[] losingSymbols;
	
	private final long NORMAL_BET = 100;
	private final long NEGATIVE_BET = -100;

	@Before
	public void setUp() throws Exception {
		normalWinningCombination = new HashMap<String, Integer>() {
            {
		        put("A,A,A",  20);
		        put("B,B,B",  20);
		        put("C,C,C",  20);
		        put("A,B,C",  30);
		    }
		};

		negativeMulKey = "B,B,B";
		negativeWinningCombination = new HashMap<String, Integer>() {
		    {
		        put("A,A,A",          20);
		        put(negativeMulKey,   -20);
		    }
		};

		mismatchKey = "C,C";
		mismatchWinningCombination = new HashMap<String, Integer>() {
		    {
		        put("A,A,A",       20);
	            put(mismatchKey,   20);
		    }
		};

		winningKey = "A,A,A";
		winningSymbols = new Symbol[] {new Symbol("A", 1), new Symbol("A", 1), new Symbol("A", 1)};

		losingSymbols = new Symbol[] {new Symbol("A", 1), new Symbol("B", 1), new Symbol("A", 1)};
	}

	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testNormalWinningCombinationWin() {
		ConcreteEvaluator evaluator = new ConcreteEvaluator(normalWinningCombination);
		long result = evaluator.evaluate(winningSymbols, NORMAL_BET);

		int betMultiplier = normalWinningCombination.get(winningKey);
		assertEquals(NORMAL_BET * betMultiplier, result);
	}

    @Test
    public void testNormalWinningCombinationLoose() {
        ConcreteEvaluator evaluator = new ConcreteEvaluator(normalWinningCombination);
        long result = evaluator.evaluate(losingSymbols, NORMAL_BET);

        assertEquals(0, result);
    }

	@Test
    public void testNegativeBet() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Bet cannot be negative");

        ConcreteEvaluator evaluator = new ConcreteEvaluator(normalWinningCombination);
        evaluator.evaluate(winningSymbols, NEGATIVE_BET);
    }

	@Test
    public void testNegativeWinningCombination() {
        exceptionRule.expect(IllegalArgumentException.class);
        String errorMessage = String.format("The bet multiplier of %s cannot be negative", negativeMulKey);
        exceptionRule.expectMessage(errorMessage);

        ConcreteEvaluator evaluator = new ConcreteEvaluator(negativeWinningCombination);
        evaluator.evaluate(winningSymbols, NORMAL_BET);
    }

	@Test
    public void testMismatchWinningCombination() {
        exceptionRule.expect(IllegalArgumentException.class);
        String errorMessage = String.format("The symbol number of winning combination key %s does not match the number of given symbols: ", mismatchKey);
        exceptionRule.expectMessage(errorMessage);

        ConcreteEvaluator evaluator = new ConcreteEvaluator(mismatchWinningCombination);
        evaluator.evaluate(winningSymbols, NORMAL_BET);
    }	
}
