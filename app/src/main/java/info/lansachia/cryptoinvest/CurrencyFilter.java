package info.lansachia.cryptoinvest;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;


/**
 * Currency Filter class to filter currencies based on user entry from list of all currencies
 * @author  Landry Achia
 */
public class CurrencyFilter extends Filter {
    /**
     * member variable for Currency Item Adapter from adapter class
     */
    private CurrencyItemAdapter mCurrencyItemAdapter;

    /**
     * member variable for Filtered List of currency Items
     */
    private List<CurrencyItem> mCurrencyItemFilteredList;

    /**
     * A Currency Filter Constructor that takes arguments for currency items and currency adapter
     * @param currencyItems for all currencies
     * @param currencyItemAdapter for currency items on recycler view list of currencies
     */
    CurrencyFilter(List<CurrencyItem> currencyItems, CurrencyItemAdapter currencyItemAdapter){
        this.mCurrencyItemAdapter = currencyItemAdapter;
        this.mCurrencyItemFilteredList = currencyItems;
    }

    /**
     * This override method performs the filtering
     * @param constraint for the char sequences to be queried
     * @return the result of the filtered char sequence
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        //check constraint validity
        if (constraint !=null && constraint.length() > 0){
            //change to lower case
            constraint = constraint.toString().toLowerCase();

            //store the filtered currency
            List<CurrencyItem> filteredCurrencies = new ArrayList<>();

            for (int i = 0; i<mCurrencyItemFilteredList.size(); i++){
                //check if exist in list
                if (mCurrencyItemFilteredList.get(i).getCurrencyName().toLowerCase().contains(constraint)){
                    //add currency to filtered currencies
                    filteredCurrencies.add(mCurrencyItemFilteredList.get(i));
                }
            }
            filterResults.count = filteredCurrencies.size();
            filterResults.values = filteredCurrencies;
        }else {
            filterResults.count = mCurrencyItemFilteredList.size();
            filterResults.values = mCurrencyItemFilteredList;
        }

        return filterResults;
    }

    /**
     * override method publishes results
     * @param constraint queried char sequence
     * @param filterResults filtered results after char sequence is entered
     */
    @Override
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        mCurrencyItemAdapter.mCurrencyItems = (ArrayList<CurrencyItem>) filterResults.values;
        //refresh
        mCurrencyItemAdapter.notifyDataSetChanged();

    }
}
