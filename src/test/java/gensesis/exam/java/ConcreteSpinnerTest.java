package gensesis.exam.java;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.genesis.exams.slot.Reel;
import com.genesis.exams.slot.Symbol;

public class ConcreteSpinnerTest {
	private Symbol[] normalSymbols;
	private Symbol[] emptySymbols;
	private Symbol[] negativeSymbols;

	private ConcreteSpinner spinner;

	@Before
	public void setUp() throws Exception {
		normalSymbols = new Symbol[] {new Symbol("A", 1), new Symbol("B", 1),
				                      new Symbol("C", 1), new Symbol("X", 2),
				                      new Symbol("Y", 3), new Symbol("Z", 4)};
		emptySymbols = new Symbol[0];
		negativeSymbols = new Symbol[] {new Symbol("A", 2), new Symbol("B", -1)};

		spinner = new ConcreteSpinner();
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testSpinNormalSymbols() {
		Reel reel = new Reel(normalSymbols);
		Symbol symbolResult = spinner.spin(reel);
		assertTrue(Arrays.asList(normalSymbols).contains(symbolResult));
	}

	@Test
	public void testSpinEmptySymbols() {
		exceptionRule.expect(IllegalArgumentException.class);
	    exceptionRule.expectMessage("The reel has no symbols");

		Reel reel = new Reel(emptySymbols);
		spinner.spin(reel);
	}

	@Test
	public void testSpinNegativeSymbols() {
		exceptionRule.expect(IllegalArgumentException.class);
		String errorMessage = String.format("The weight of the symbol is negative: %s", negativeSymbols[1].getName());
	    exceptionRule.expectMessage(errorMessage);

		Reel reel = new Reel(negativeSymbols);
		spinner.spin(reel);
	}
}
