package in.slashed.slashed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import in.slashed.slashed.R;
import in.slashed.slashed.data.SearchResult;

/**
 * Created by Saurabh on 22-Dec-15.
 */

public class SearchResultAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SearchResult> mSearchResults;

    public SearchResultAdapter(Context context, ArrayList<SearchResult> searchResults) {
        mContext = context;
        mSearchResults = searchResults;
    }

    @Override
    public int getCount() {
        return mSearchResults.size();
    }

    @Override
    public Object getItem(int i) {
        return mSearchResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.search_list_item, null);
            holder = new ViewHolder();
            holder.mName = (TextView) view.findViewById(R.id.nameTV);
            holder.mDetail1 = (TextView) view.findViewById(R.id.detail1TV);
            holder.mDetail2 = (TextView) view.findViewById(R.id.detail2TV);
            holder.mImage = (ImageView) view.findViewById(R.id.imageIV);
            holder.mMinPrice = (TextView) view.findViewById(R.id.priceTV);
            holder.mSeller = (ImageView) view.findViewById(R.id.sellerIV);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        SearchResult searchResult = mSearchResults.get(i);
        holder.mName.setText(searchResult.getName());
        holder.mDetail1.setText(searchResult.getDetail1());
        holder.mDetail2.setText(searchResult.getDetail2());
        Picasso.with(mContext).load(searchResult.getImage()).into(holder.mImage);
        holder.mMinPrice.setText(Integer.toString(searchResult.getMinPrice()));
        if(searchResult.getSeller().equals("flipkart")) {
            Picasso.with(mContext).load(R.mipmap.flipkart).into(holder.mSeller);
        } else if (searchResult.getSeller().equals("amazon")) {
            Picasso.with(mContext).load(R.mipmap.amazon).into(holder.mSeller);
        } else if (searchResult.getSeller().equals("snapdeal")) {
            Picasso.with(mContext).load(R.mipmap.snapdeal).into(holder.mSeller);
        }

        return view;
    }

    private static class ViewHolder {
        TextView mName;
        TextView mDetail1;
        TextView mDetail2;
        ImageView mImage;
        TextView mMinPrice;
        ImageView mSeller;
    }
}