package in.slashed.slashed.data;

import org.apache.http.entity.StringEntity;

/**
 * Created by Saurabh on 22-Dec-15.
 */
public class SearchResult {

    String mName;
    String mDetail1;
    String mDetail2;
    String mImage;
    int mMinPrice;
    String mSeller;
    String mBuyUrl;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getMinPrice() {
        return mMinPrice;
    }

    public void setMinPrice(int minPrice) {
        mMinPrice = minPrice;
    }

    public String getSeller() {
        return mSeller;
    }

    public void setSeller(String seller) {
        mSeller = seller;
    }

    public String getBuyUrl() {
        return mBuyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        mBuyUrl = buyUrl;
    }

    public String getDetail1() {
        if (!mDetail1.isEmpty()) {
            return mDetail1 + ", ";
        } else {
            return mDetail1;
        }
    }

    public void setDetail1(String detail1) {
        mDetail1 = detail1;
    }

    public String getDetail2() {
        return mDetail2;
    }

    public void setDetail2(String detail2) {
        mDetail2 = detail2;
    }
}
