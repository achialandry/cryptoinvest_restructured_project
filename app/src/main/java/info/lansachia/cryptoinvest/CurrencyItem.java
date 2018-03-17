package info.lansachia.cryptoinvest;

import java.io.Serializable;

/**
 * Class to for all list of currencies which implements serializable interface
 * @author  Landry Achia
 */

public class CurrencyItem implements Serializable {

    /**
     * currency id for logo
     */
    private String currencyLogo;



    /**
     *name of currency
     */
    private String mCurrencyName;

    /**
     * currency price
     */
    private String mPrice;

    /**
     * currency symbol
     */
    private String mSymbol;

    /**
     * currency 24 hr volume
     */
    private String m24Hvolume;


    /**
     *total supply of currency
     */
    private String mTotalSupply;

    /**
     * available supply of currency
     */
    private String mAvailableSupply;

    /**
     * change per 1 hour of currency
     */
    private String mChangePerHourPercent;

    /**
     * Change per day  24hrs of currency in percentage
     */
    private String mChangePerDayPercent;

    /**
     * change per 1 week (7weeks) of Currency in percent
     */
    private String mChangePerWeekPercent;

    /**
     * currency ranking:
     */
    private String mCurrencyRanking;

    /**
     * currency market Cap
     */
    private String mCurrencyMarketCap;

    /**
     * Required no argument constructor for the Currency Item class
     */
    public CurrencyItem() {

    }

    /**
     * accessor method to get currency logo
     * @return the currencyLogo
     */
    public String getCurrencyLogo() {
        return currencyLogo;
    }

    /**
     * mutator method to set currency logo
     * @param currencyLogo is set
     */
    public void setCurrencyLogo(String currencyLogo) {
        if (currencyLogo.contains("-")) {
            this.currencyLogo = currencyLogo.replace("-", "_");
        } else {
            this.currencyLogo = currencyLogo;
        }

    }

    /**
     * accessor method to get currency name
     * @return name of currency
     */
    public String getCurrencyName() {
        return mCurrencyName;
    }

    /**
     * mutator method to set name of currency
     * @param currencyName for the currency Name in String format
     */
    public void setCurrencyName(String currencyName) {
        mCurrencyName = currencyName;
    }

    /**
     * accessor method to get price of currency
     * @return the price of currency
     */
    public String getPrice() {
        return  mPrice;
    }

    /**
     * mutator method to set price of currency
     * @param price variable in strng format
     */
    public void setPrice(String price) {
        mPrice = price;
    }

    /**
     * accessor method to get symbol of currency
     * @return string of symbol of the currency
     */
    public String getSymbol() {
        return mSymbol;
    }

    /**
     * mutator method to set symbol of currency
     * @param symbol of currency
     */
    public void setSymbol(String symbol) {
        mSymbol = symbol;
    }

    /**
     * accessor method to get 24hour volume of currency
     * @return 24hour volume of the currency
     */
    public String getM24Hvolume() {
        return m24Hvolume;
    }

    /**
     * mutator method to set 24hour volume of currency
     * @param m24Hvolume of currency
     */
    public void setM24Hvolume(String m24Hvolume) {
        this.m24Hvolume = m24Hvolume;
    }

    /**
     * accessor method to get total supply for given currency
     * @return currency total supply
     */
    public String getTotalSupply() {
        return mTotalSupply;
    }

    /**
     * mutator method to set total supply of currency
     * @param totalSupply of currency
     */
    public void setTotalSupply(String totalSupply) {
        mTotalSupply = totalSupply;
    }

    /**
     * accessor method to get available supply of currency
     * @return available supply of currency
     */
    public String getAvailableSupply() {
        return mAvailableSupply;
    }

    /**
     * mutator method to set available supply of currency
     * @param availableSupply of currency
     */
    public void setAvailableSupply(String availableSupply) {
        mAvailableSupply = availableSupply;
    }

    /**
     * accessor method to get the Change per hour for each currency
     * @return Change per hour in percent
     */
    public String getChangePerHourPercent() {
        return mChangePerHourPercent + "%";
    }

    /**
     * mutator method to set Change per hour in percent for currency
     * @param changePerHourPercent in percent for currency
     */
    public void setChangePerHourPercent(String changePerHourPercent) {
        mChangePerHourPercent = changePerHourPercent;
    }

    /**
     * accessor method ot get Change per day for each currency
     * @return change per day
     */
    public String getChangePerDayPercent() {
        return mChangePerDayPercent;
    }

    /**
     * mutator method to set change per day in percent for currency
     * @param changePerDayPercent in percent
     */
    public void setChangePerDayPercent(String changePerDayPercent) {
        mChangePerDayPercent = changePerDayPercent;
    }

    /**
     * accessor method to get Change per week in percent for currency
     * @return change per week in percent
     */
    public String getChangePerWeekPercent() {
        return mChangePerWeekPercent;
    }

    /**
     * mutator method to set change per week in percent for currency
     * @param changePerWeekPercent for currency
     */
    public void setChangePerWeekPercent(String changePerWeekPercent) {
        mChangePerWeekPercent = changePerWeekPercent;
    }

    /**
     * accessor method to get currency ranking of currency
     * @return currency ranking as string
     */
    public String getCurrencyRanking() {
        return mCurrencyRanking;
    }

    /**
     * Mutator method to set ranking of currency
     * @param currencyRanking for ranking of currency
     */
    public void setCurrencyRanking(String currencyRanking) {
        mCurrencyRanking = currencyRanking;
    }

    /**
     * accessor method to get currency market cap
     * @return string of currency market cap
     */
    public String getCurrencyMarketCap() {
        return mCurrencyMarketCap;
    }

    /**
     * mutator method to set Currency market cap
     * @param currencyMarketCap for the currency
     */
    public void setCurrencyMarketCap(String currencyMarketCap) {
        mCurrencyMarketCap = currencyMarketCap;
    }
}
