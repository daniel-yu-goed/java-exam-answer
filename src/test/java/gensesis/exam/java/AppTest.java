package gensesis.exam.java;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.genesis.exams.slot.Reel;
import com.genesis.exams.slot.SpinResult;
import com.genesis.exams.slot.Symbol;

public class AppTest 
{
    private final long BET = 100;
    private final Integer MULTIPLIER_TWENTY = 20;
    private final Integer MULTIPLIER_THIRTY = 30;

    private Reel[] reels;
    private HashMap<String, Integer> winningCombination;
    private Long[] allPayouts;
    
    @Before
    public void setUp() throws Exception {
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
        reels = new Reel[] {reelOne, reelTwo, reelThree};
        
        winningCombination = new HashMap<String, Integer>() {
            {
                put("A,A,A",  MULTIPLIER_TWENTY);
                put("B,B,B",  MULTIPLIER_TWENTY);
                put("C,C,C",  MULTIPLIER_TWENTY);
                put("A,B,C",  MULTIPLIER_THIRTY);
            }
        };
        
        allPayouts = new Long[] {Long.valueOf("0"), BET*MULTIPLIER_TWENTY, BET*MULTIPLIER_THIRTY};
    }

    @Test
    public void testApp()
    {
        SpinResult spinResult = App.runSlotMachine(BET, reels, winningCombination);
        assertTrue(Arrays.asList(allPayouts).contains(spinResult.getPayout()));
    }
}
