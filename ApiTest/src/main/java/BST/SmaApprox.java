package BST;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class SmaApprox {

    Stock stock;
    Calendar from = Calendar.getInstance();

    BigDecimal SMA = BigDecimal.ZERO;
    BigDecimal SMACounter = BigDecimal.ZERO;
    int periodRange;
    BigDecimal divider;

    List<HistoricalQuote> histQuoteList;
    HistoricalQuote[] histQuotesTab;
    BigDecimal[] SMATab;

    BST bst;

    public SmaApprox(String name, int timeUnit, int period, int periodRange) throws IOException {
        from.add(timeUnit, period);
        this.periodRange = periodRange;
        this.stock = YahooFinance.get(name, from, Calendar.getInstance(), Interval.DAILY);
        prepareData();
    }

    public void calculateSma(){
        for (int indx = 0; indx <= histQuoteList.size() - 1; indx++) {
            SMACounter = BigDecimal.ZERO;
            //System.out.println("CLOSE: " + histQuotesTab[indx].getClose());
            for (int i = indx - periodRange; i < indx; i++){
                SMACounter = SMACounter.add(histQuotesTab[indx].getClose());
            }
            SMA = SMACounter.divide(divider);
            //System.out.println("SMACounter: " + SMACounter + "   BST.SmaApprox: " + BST.SmaApprox);
            SMATab[indx] = SMA;
        }
    }

    public void bstInsert(){
        for (int i = 1; i <= SMATab.length; i++){
            //System.out.println("Period " + i + ": " + SMATab[i - 1]);
            bst.insert(SMATab[i - 1]);
        }
    }

    public void bstInOrder(){
        System.out.println("\n#### INORDER: ####\n");
        bst.inOrder(bst.root);
    }

    public void bstPreOrder(){
        System.out.println("\n#### PREORDER: ####\n");
        bst.preOrder(bst.root);
    }

    public void bstPostOrder(){
        System.out.println("\n#### POSTORDER: ####\n");
        bst.postOrder(bst.root);
    }

    private void prepareData() throws IOException {
        SMA = BigDecimal.ZERO;
        SMACounter = BigDecimal.ZERO;
        divider = new BigDecimal(periodRange);
        histQuoteList = stock.getHistory();
        histQuotesTab = new HistoricalQuote[histQuoteList.size()];
        histQuotesTab = histQuoteList.toArray(histQuotesTab);
        SMATab = new BigDecimal[histQuotesTab.length];
        bst = new BST();
    }

}
