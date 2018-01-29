package RsiApproximation;

import yahoofinance.histquotes.HistoricalQuote;

/**
 * Created by Bartosz on 03.12.2017.
 */
public class UnitData {

    private float rsi = 0;
    private String change = "";
    private HistoricalQuote historicalQuote;

    public UnitData(String change, HistoricalQuote historicalQuote) {
        this.change = change;
        this.historicalQuote = historicalQuote;
    }

    public float getRsi() {
        return rsi;
    }

    public void setRsi(float rsi) {
        this.rsi = rsi;
    }

    public HistoricalQuote getHistoricalQuote() {
        return historicalQuote;
    }

    public void setHistoricalQuote(HistoricalQuote historicalQuote) {
        this.historicalQuote = historicalQuote;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
