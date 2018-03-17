package info.lansachia.cryptoinvest;

/**
 * Class is the model for the data user will save onto firebase to track a particular currency
 *
 * @author  Landry Achia
 */

public class CurrencyFireBaseDataModel {
    /**
     * currency Id for the currency user is to track
     */
    private String currencyId;

    /**
     * variable for the price of the currency user is to track
     */
    private String currencyPrice;

    /**
     * variable for the amount user wants to track changes for a currency
     */
    private String userSettingCurrencyValue;

    /**
     * name of currency user will be tracking
     */
    private String userSettingCurrencyName;

    /**
     * required no argument constructor for this model class
     */
    CurrencyFireBaseDataModel(){
        //required constructor for serialization
    }

    /**
     * accessor method to get Id of currency user will be tracking
     * @return Id in string of currency
     */
    public String getCurrencyId() {
        return currencyId;
    }

    /**
     * mutator method to set the Id of currency
     * @param currencyId in string
     */
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * accessor method to get price of the currency user is to track
     * @return price of currency in string
     */
    public String getCurrencyPrice() {
        return currencyPrice;
    }

    /**
     * mutator method to set Price of currency
     * @param currencyPrice to set price of currency
     */
    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    /**
     *accessor method to get the value for the amount user wants to track
     * @return user value to track changes in price for that currency
     */
    public String getUserSettingCurrencyValue() {
        return userSettingCurrencyValue;
    }

    /**
     * mutator method to set the amount in change for currency user wants to track
     * @param userSettingCurrencyValue to be set to track changes
     */
    public void setUserSettingCurrencyValue(String userSettingCurrencyValue) {
        this.userSettingCurrencyValue = userSettingCurrencyValue;
    }

    /**
     * accessor method to get the name of the currency user is tracking
     * @return name of currency user is tracking
     */
    public String getUserSettingCurrencyName() {
        return userSettingCurrencyName;
    }

    /**
     * mutator method to set name of the currency user is tracking
     * @param userSettingCurrencyName name of currency being tracked
     */
    public void setUserSettingCurrencyName(String userSettingCurrencyName) {
        this.userSettingCurrencyName = userSettingCurrencyName;
    }
}
