package info.lansachia.cryptoinvest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as singleton to make available data to all other fragments and
 * classes requiring to use currency item queried from API
 *
 * @author  Landry Achia
 */

public class CurrencyLab {

    /**
     * static currency lab variable
     */
    private static CurrencyLab sCurrencyLab;

    /**
     * list of currency items
     */
    private List<CurrencyItem> mCurrencyItems;

    /**
     * context
     */
    private Context mContext;

    /**
     * request queue to make request to api
     */
    private RequestQueue requestQueue;

    /**
     * Tag to be used for debugging
     */
    private static final String TAG = "Currency Lab";

    /**
     * API endpoint to query all currencies
     */
    public static final String URL_STRING = "https://api.coinmarketcap.com/v1/ticker/";

    /**
     * Constructor class with context parameter
     * @param context for constructor class
     * @return the currencyLab object
     */
    public static CurrencyLab get(Context context) {
        if (sCurrencyLab == null) {
            sCurrencyLab = new CurrencyLab(context);
        }
        return sCurrencyLab;
    }

    /**
     * private constructor class where actual query is made when called
     * @param context where class will be used
     */
    private CurrencyLab(Context context) {
        mCurrencyItems = new ArrayList<>();
        mContext = context;
        requestData();
    }

    /**
     * accessor method to get list of currencies
     * @return list of all currencies
     */
    public List<CurrencyItem> getCurrencyItems() {
        return mCurrencyItems;
    }

    /**
     * method to query api endpoint and return full list of data from api
     */
    private void requestData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_STRING,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            parseData(jsonArray);
                            Log.d(TAG, "requestData Called");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Crypto Invest", error + "failed");
                    }
                });

        //add request to the volley queue
        requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    /**
     * method to parse data from json obtained from api to currency items
     * @param jsonArray from api call if successful
     */
    private void parseData(JSONArray jsonArray) {
        try {
            JSONObject jsonObject;
            for (int i = 0; i <=30; i++) {

                jsonObject = jsonArray.getJSONObject(i);
                CurrencyItem currencyItem = new CurrencyItem();

                currencyItem.setCurrencyLogo(jsonObject.getString("id"));
                currencyItem.setSymbol(jsonObject.getString("symbol"));
                currencyItem.setCurrencyName(jsonObject.getString("name"));
                currencyItem.setPrice(jsonObject.getString("price_usd"));
                currencyItem.setM24Hvolume(jsonObject.getString("24h_volume_usd"));
                currencyItem.setCurrencyMarketCap(jsonObject.getString("market_cap_usd"));
                currencyItem.setAvailableSupply(jsonObject.getString("available_supply"));
                currencyItem.setTotalSupply(jsonObject.getString("total_supply"));
                currencyItem.setChangePerHourPercent(jsonObject.getString("percent_change_1h"));
                currencyItem.setChangePerDayPercent(jsonObject.getString("percent_change_24h"));
                currencyItem.setChangePerWeekPercent(jsonObject.getString("percent_change_7d"));


                mCurrencyItems.add(currencyItem);
            }


            Log.d(TAG, "parseData Called");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
