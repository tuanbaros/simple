package simple.rxdemo;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tuannt on 7/12/17.
 * Project: RxDemo
 * Package: simple.rxdemo
 */
class MainService {
    private static final String BASE_URL = "https://api.learn2crack.com/";
    private static Retrofit.Builder sBuilder = new Retrofit.Builder();
    private static Retrofit sRetrofit = sBuilder.baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    static <T> T getRequest(Class<T> clazz) {
        return sRetrofit.create(clazz);
    }
}
