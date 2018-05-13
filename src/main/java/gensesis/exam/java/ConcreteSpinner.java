package gensesis.exam.java;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import com.genesis.exams.slot.Reel;
import com.genesis.exams.slot.Spinner;
import com.genesis.exams.slot.Symbol;

/**
 * @author Daniel Yu
 *
 */
public class ConcreteSpinner implements Spinner {

	@Override
	/* Spin the reel to return a symbol
	 * @param reel 	the reel to spin 
	 * @return 		the symbol after spinning
	 * @see			com.genesis.exams.slot.Spinner#spin(com.genesis.exams.slot.Reel)
	 */
	public Symbol spin(Reel reel) {
		try {
			checkValidReel(reel);
			return createRandomSymbol(reel);
		}
		catch(IllegalArgumentException e) {
			throw e;
		}		
	}

	private void checkValidReel(Reel reel) {
		Symbol[] symbols = reel.getSymbols();
		if (symbols.length == 0) {
			throw new IllegalArgumentException("The reel has no symbols");
		}

		for(Symbol symbol: symbols) {
			if (symbol.getWeight() < 0) {
				String errorMessage = String.format("The weight of the symbol is negative: %s", symbol.getName());
				throw new IllegalArgumentException(errorMessage);
			}
		}
	}

	private Symbol createRandomSymbol(Reel reel) {
	    ArrayList<SymbolRange> symbolRanges = getSymbolRanges(reel.getSymbols());
		int maxRamdomNum = symbolRanges.get(symbolRanges.size()-1).curSum;
		int randomNum = ThreadLocalRandom.current().nextInt(1, maxRamdomNum + 1);
		return getSymbolByRandomNum(randomNum, symbolRanges);
	}

	private ArrayList<SymbolRange> getSymbolRanges(Symbol[] symbols) {
	    ArrayList<SymbolRange> symbolRanges = new ArrayList<SymbolRange>();
		int preSum=0, curSum=0;
		for (Symbol symbol: symbols) {
			curSum = preSum + symbol.getWeight();
			symbolRanges.add(new SymbolRange(symbol, preSum, curSum));
			preSum = curSum;
		}
		return symbolRanges;
	}

	private Symbol getSymbolByRandomNum(int randomNum, ArrayList<SymbolRange> symbolRanges) {
		Symbol symbol = null;
		for (SymbolRange symbolRange: symbolRanges) {
			if (randomNum > symbolRange.preSum && randomNum <= symbolRange.curSum) {
				symbol = symbolRange.getSymbol();
				return symbol;
			}
		}

		throw new IllegalArgumentException("Given random number is out of range");
	}

	/*
	 * SymbolRange is a type of data object to record a symbol and its valid range.
	 * The valid range of the symbol should be: 
	 * (1) larger than the preSum
	 * (2) less than or equal to the curSum
	 */
	class SymbolRange {
		private Symbol symbol;
		private int preSum;
		private int curSum;
		
		public SymbolRange(Symbol symbol, int preSum, int curSum) {
			this.symbol = symbol;
			this.preSum = preSum;
			this.curSum = curSum;
		}
		
		public Symbol getSymbol() {
			return this.symbol;
		}
		
		public int getPreSum() {
			return this.preSum;
		}
		
		public int getCurSum() {
			return this.curSum;
		}
	}

}
