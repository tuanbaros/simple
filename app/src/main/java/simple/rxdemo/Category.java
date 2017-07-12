package simple.rxdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FRAMGIA\nguyen.thanh.tuan on 7/13/17.
 */

public class Category {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
