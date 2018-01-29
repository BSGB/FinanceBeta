import RsiApproximation.RsiApprox;
import RsiApproximation.RsiSiblings;
import RsiApproximation.UnitData;

import BST.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Bartosz on 22.11.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        RsiApprox rsiApprox = new RsiApprox("INTC", Calendar.MONTH, -20, 7);
        UnitData[] ud = rsiApprox.calculateRsi();

        for(UnitData s: ud){
            System.out.println("OPEN: " + s.getHistoricalQuote().getOpen() + " CLOSE: " + s.getHistoricalQuote().getClose() + " RSI: " + s.getRsi() + " CHANGE: " + s.getChange());
        }

        List<RsiSiblings> list = rsiApprox.guessRsi(30);
        list.forEach(s -> System.out.println(s.getSiblingRsi()));

        /*SmaApprox smaApprox = new SmaApprox("INTC", Calendar.MONTH, -20, 7);
        smaApprox.calculateSma();
        smaApprox.bstInsert();
        smaApprox.bstInOrder();*/
    }
}
