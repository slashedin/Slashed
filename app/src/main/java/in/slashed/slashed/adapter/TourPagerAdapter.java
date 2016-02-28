package in.slashed.slashed.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.slashed.slashed.R;

/**
 * Created by Saurabh on 16-Nov-15.
 */
public class TourPagerAdapter extends PagerAdapter {

    Context mContext;
    int image[];
    String text[];
    LayoutInflater mInflater;

    public TourPagerAdapter(Context context, int[] image, String[] text) {
        mContext = context;
        this.image = image;
        this.text = text;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView tourIV;
        TextView tourTV;

        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View tourView = mInflater.inflate(R.layout.tour_pager_item, container, false);

        tourIV = (ImageView) tourView.findViewById(R.id.tourPagerIV);
        tourIV.setImageResource(image[position]);

        tourTV = (TextView) tourView.findViewById(R.id.tourPagerTV);
        tourTV.setText(text[position]);

        ((ViewPager) container).addView(tourView);

        return tourView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout)object);
    }
}
