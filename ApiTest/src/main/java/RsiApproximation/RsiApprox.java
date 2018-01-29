package RsiApproximation;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Bartosz on 04.12.2017.
 */
public class RsiApprox {
    Calendar from = Calendar.getInstance();
    int dataRange;
    Stock stock;
    List<HistoricalQuote> dataAsList;
    HistoricalQuote[] dataAsArray;
    UnitData[] unitData;


    public RsiApprox(String name, int timeUnit, int period, int dataRange) throws IOException {
        from.add(timeUnit, period);
        this.dataRange = dataRange;
        this.stock = YahooFinance.get(name, from, Calendar.getInstance(), Interval.DAILY);
        prepareData();
    }

    public RsiApprox(String name, int timeUnit, int period, Calendar to, int dataRange) throws IOException {
        from.add(timeUnit, period);
        this.dataRange = dataRange;
        this.stock = YahooFinance.get(name, from, to, Interval.DAILY);
        prepareData();
    }

    public UnitData[] calculateRsi(){
        for(int i = 0; i < unitData.length; i++){
            String change = "";
            if(dataAsArray[i].getClose().compareTo(dataAsArray[i].getOpen()) == 1){
                change = "UP";
            } else if(dataAsArray[i].getClose().compareTo(dataAsArray[i].getOpen()) == 0){
                change = "NONE";
            } else{
                change = "DOWN";
            }

            unitData[i] = new UnitData(change, dataAsArray[i]);
        }

        countRsi();

        return Arrays.copyOfRange(unitData, dataRange, unitData.length);
    }

    private void countRsi(){
        for (int j = dataRange; j < unitData.length; j++){
            float upSum = 0;
            float downSum = 0;
            float rsiValue = 0;

            for(int k = j-dataRange; k < j; k++) {
                if(unitData[k].getChange().equals("UP")){
                    upSum++;
                } else if(unitData[k].getChange().equals("DOWN")){
                    downSum++;
                }
            }
            if(downSum == 0){
                rsiValue = 100;
            } else {
                rsiValue = 100 - ((100) / (1 + upSum / downSum));
            }
            //System.out.println("USTAWIAM INDEX: " + j + " NA WARTOSC: " + rsiValue + " GDZIE UP:DOWN: " + upSum + ":" + downSum);
            unitData[j].setRsi(rsiValue);
        }
    }

    public List<RsiSiblings> guessRsi(int period){
        float localRsiSum = 0;
        float periodAvgRsi = 0;
        for(int i = unitData.length - 1; i > unitData.length - period; i--){
            localRsiSum+=unitData[i].getRsi();
            //System.out.println("LOKALNA SUMA: " + localRsiSum + " PO DODANIU: " + unitData[i].getRsi());
        }
        periodAvgRsi = localRsiSum / period;
        System.out.println("LOKALNE RSI: " + periodAvgRsi);


        return findSimilar(period, periodAvgRsi);
    }

    private List<RsiSiblings> findSimilar(int period, float periodAverageRsi){
        List<RsiSiblings> rsiSiblings = new ArrayList<>();

        for (int i = period; i < unitData.length - period; i++){
            float matchSimilarAverageRsi = 0;
            float similarSum = 0;
            float predictedRsi = 0;

            for (int j = i - period; j < i; j++){
                similarSum+=unitData[j].getRsi();
            }
            matchSimilarAverageRsi = similarSum / period;
            //System.out.println("WYSZUKANE RSI: " + matchSimilarAverageRsi);
            if((int)matchSimilarAverageRsi == (int)periodAverageRsi){
                for (int k = i; k < i + period; k++){
                    predictedRsi+=unitData[k].getRsi();
                }
                rsiSiblings.add(new RsiSiblings(predictedRsi / period));
            }
        }
        return rsiSiblings;
    }

    private void prepareData() throws IOException {
        dataAsList = stock.getHistory();
        dataAsArray = new HistoricalQuote[dataAsList.size()];
        dataAsArray = dataAsList.toArray(dataAsArray);
        unitData = new UnitData[dataAsArray.length];
    }
}
