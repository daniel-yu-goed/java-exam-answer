package gensesis.exam.java;

import java.util.HashMap;

import com.genesis.exams.slot.Evaluator;
import com.genesis.exams.slot.Reel;
import com.genesis.exams.slot.SlotMachine;
import com.genesis.exams.slot.SpinResult;
import com.genesis.exams.slot.Spinner;
import com.genesis.exams.slot.Symbol;

/**
 * This is the main method for a {@link com.genesis.exams.slot.SlotMachine SlotMachine}.
 * @author Daniel Yu
 *
 */
public class App 
{
    private static long BET = 100;

    public static void main(String[] args)
    {
        Reel[] reels = setupReels();
        HashMap<String, Integer> winningCombination = setupWinningCombination();   

        SpinResult spinResult = runSlotMachine(BET, reels, winningCombination);
        Symbol[] symbols = spinResult.getSymbols();
        
        System.out.println("The symbols after spinning is: " + getStringOfSymbols(symbols));
        System.out.println("The pay-out is: " + spinResult.getPayout());
    }
    
    public static SpinResult runSlotMachine(long bet, Reel[] reels, HashMap<String, Integer> winningCombination) {        
        Spinner spinner = new ConcreteSpinner();        
        Evaluator evaluator = new ConcreteEvaluator(winningCombination);
        SlotMachine slotMachine = new SlotMachine(reels, spinner, evaluator);
        
        return slotMachine.spin(bet);
    }
    
    private static Reel[] setupReels() {
        // initialize symbols
        Symbol symbolA = new Symbol("A", 1);
        Symbol symbolB = new Symbol("B", 1);
        Symbol symbolC = new Symbol("C", 1);
        Symbol symbolX = new Symbol("X", 2);
        Symbol symbolY = new Symbol("Y", 3);
        Symbol symbolZ = new Symbol("Z", 4);
        
        // initialize reels
        Reel reelOne   = new Reel(new Symbol[] {symbolA, symbolB, symbolC, symbolX, symbolY, symbolZ});
        Reel reelTwo   = new Reel(new Symbol[] {symbolZ, symbolY, symbolX, symbolA, symbolB, symbolC});
        Reel reelThree = new Reel(new Symbol[] {symbolA, symbolB, symbolC, symbolX, symbolY, symbolZ});

        return new Reel[] {reelOne, reelTwo, reelThree};
    }
    
    private static HashMap<String, Integer> setupWinningCombination() {
        return new HashMap<String, Integer>() {
            {
                put("A,A,A",  20);
                put("B,B,B",  20);
                put("C,C,C",  20);
                put("A,B,C",  30);
            }
        };
    }

    private static String getStringOfSymbols(Symbol[] symbols) {
        String symbolString = "";
        for(Symbol symbol: symbols) {
            symbolString += symbol.getName() + ",";
        }
        return symbolString.substring(0, symbolString.length()-1);
    }
}
