package info.lansachia.cryptoinvest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * This Class is the adapter to populate the recycler view with data from currency Item
 * and also implements the search on the fragment hosting the recycler view
 * @author  Landry Achia
 */

public class CurrencyItemAdapter extends RecyclerView.Adapter<CurrencyItemAdapter.MyViewHolder> implements Filterable {
    /**
     * variable for all list of currencies
     */
    protected List<CurrencyItem> mCurrencyItems;

    /**
     * variable for the filtered list of currency
     */
    private List<CurrencyItem> mCurrencyFilterList;

    /**
     * variable for context
     */
    private Context mContext;

    /**
     * variable for the currency filter
     */
    private CurrencyFilter mCurrencyFilter;



    /**
     * Creating an interface called onRecyclerviewItemClickListener
     * This interface will handle clicks on items on recyclerview
     */
    public interface RecyclerItemClickListener
    {

        /**
         * method called when an item within recycler view is clicked
         * @param view the position of the item
         * @param position the id the view which is clicked within the item or -1 if the item itself is clicked
         * @param isLongClick returns true if item is clicked long and false otherwise
         */
        void reCyclerClickItemListener(View view, int position, boolean isLongClick);
    }


    /**
     * Two parameter constructor for the currency adapter Class
     * @param context of the adapter
     * @param currencyItems for all list of currencies
     */
     CurrencyItemAdapter(Context context, List<CurrencyItem> currencyItems){
        this.mContext = context;
        this.mCurrencyItems = currencyItems;
        this.mCurrencyFilterList = currencyItems;
    }

    /**
     * override for the view holder on create
     * @param parent of the context to be hosted
     * @param viewType type of view
     * @return the view
     */
    @Override
    @Nullable
    public MyViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = null;
        if (parent != null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        return new MyViewHolder(layoutInflater, parent);
    }

    /**
     * override binds view to holder
     * @param holder holder for each currency item
     * @param position position the item to be binded at
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        CurrencyItem currencyItem = mCurrencyItems.get(position);
        holder.bind(currencyItem);
        holder.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void reCyclerClickItemListener(View view, int position, boolean isLongClick) {
                if (isLongClick){

                    Snackbar.make(view, mCurrencyItems.get(position).getCurrencyName()+" LongClick", Snackbar.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(mContext,"#" + position + "-" +mCurrencyItems.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * checks the total number of items on list
     * @return number of items to be populated on recycler view
     */
    @Override
    public int getItemCount() {
        Log.d("CryptoInvest", String.valueOf(mCurrencyItems.size()));
        return mCurrencyItems.size();

    }

    /**
     * Override method return the filter object
     * @return object filterd from list of currencies
     */
    @Override
    public Filter getFilter() {
        if (mCurrencyFilter == null){
            mCurrencyFilter = new CurrencyFilter(mCurrencyFilterList, this);
        }
        return mCurrencyFilter;
    }

    /**
     * View Holder class inherits the recycler view and implements click
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        /**
         * variable for the textview of currency symbol
         */
        private TextView mSymbol;

        /**
         * variable for the textview of currency price
         */
        private TextView mPrice;

        /**
         * variable for the textview of currency name
         */
        private TextView mName;

        /**
         * variable for the textview of currency recommendation
         */
        private TextView mRecommendation;

        /**
         * variable for the image view of currency item
         */
        private ImageView mCurrencyLogo;

        /**
         * variable for the name of currency Image
         */
        private String mImageName;

        /**
         * layout to the currency list
         */
        private ConstraintLayout parentView;

        /**
         * recycler view click listener
         */
        private RecyclerItemClickListener mRecyclerItemClickListener;


        /**
         * View holder to hold each currency item and details
         * @param inflater the currency list item onto view
         * @param parent of the layout to host view
         */
        private MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_currency, parent, false));

            mSymbol = (TextView)itemView.findViewById(R.id.currency_symbol);
            mPrice = (TextView)itemView.findViewById(R.id.currency_price);
            mName = (TextView)itemView.findViewById(R.id.currency_name);
            mRecommendation = (TextView)itemView.findViewById(R.id.currency_recommendation);
            mCurrencyLogo= (ImageView)itemView.findViewById(R.id.currency_icon);
            parentView =(ConstraintLayout) itemView.findViewById(R.id.parent_view_recycler_view);


            parentView.setOnClickListener(this);
            parentView.setOnLongClickListener(this);
        }

        /**
         * method to bind currency list to view
         * @param currencyItem to be bound onto the view
         */
        private void bind(CurrencyItem currencyItem){

            //setting image logo
            mImageName = "drawable/" + currencyItem.getCurrencyLogo();
            int imageResource = mContext.getResources().getIdentifier(mImageName, null, mContext.getPackageName());
            mCurrencyLogo.setImageResource(imageResource);

            //setting text to currency symbol
            mSymbol.setText(currencyItem.getSymbol());
            mSymbol.setTextSize(25);

            //currency name
            mName.setText(currencyItem.getCurrencyName());
            mName.setTextSize(20);

            //currency prize
            mPrice.setText("$"+currencyItem.getPrice());
            mPrice.setTextSize(23);

            //recommendation
            if (currencyItem.getChangePerHourPercent().contains("-")){
                mRecommendation.setText( currencyItem.getChangePerHourPercent()+"%");
                mRecommendation.setTextSize(19);
                mRecommendation.setTextColor(ContextCompat.getColor(mContext,R.color.color_of_bad_percent));
            }else {
                mRecommendation.setText("+ " + currencyItem.getChangePerHourPercent() +"%");
                mRecommendation.setTextSize(19);
                mRecommendation.setTextColor(ContextCompat.getColor(mContext, R.color.color_of_good_percent));
            }

        }

        /**
         * set click listener
         * @param recyclerItemClickListener for click events on currency items
         */
        private void  setClickListener(RecyclerItemClickListener recyclerItemClickListener){
            this.mRecyclerItemClickListener = recyclerItemClickListener;
        }

        /**
         * checks clicks on currency items
         * @param view that gets clicked
         */
        @Override
        public void onClick(View view) {
            mRecyclerItemClickListener.reCyclerClickItemListener(view, getLayoutPosition(), false);
        }

        /**
         * checks long clicks on currency items
         * @param view that will be clicked for long
         * @return boolean of true if clicked
         */
        @Override
        public boolean onLongClick(View view) {
            mRecyclerItemClickListener.reCyclerClickItemListener(view, getLayoutPosition(), true);
            return true;
        }
    }


}
