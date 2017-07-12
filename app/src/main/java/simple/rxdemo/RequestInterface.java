package simple.rxdemo;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by tuannt on 7/12/17.
 * Project: RxDemo
 * Package: simple.rxdemo
 */
interface RequestInterface {
    @GET("android/jsonarray/")
    Observable<List<Data>> getAndroid();

    @GET("category")
    Observable<List<Category>> getCategories();
}
