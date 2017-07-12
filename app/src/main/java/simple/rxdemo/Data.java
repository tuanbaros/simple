package simple.rxdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tuannt on 7/12/17.
 * Project: RxDemo
 * Package: simple.rxdemo
 */
public class Data {
    @SerializedName("ver")
    private String mVersion;
    @SerializedName("name")
    private String mName;
    @SerializedName("api")
    private String mApi;

    public String getVersion() {
        return mVersion;
    }

    public String getName() {
        return mName;
    }

    public String getApi() {
        return mApi;
    }
}
