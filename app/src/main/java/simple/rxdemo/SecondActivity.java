package simple.rxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureDrop;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "Second";
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mCompositeDisposable = new CompositeDisposable();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://android-samsung.herokuapp.com/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();


        Observable<List<Category>> observable = retrofit.create(RequestInterface.class).getCategories();
        Disposable disposable0 = observable.flatMap(Observable::fromIterable)
                .filter(category -> category.getId() > 5)
                .distinct()
                .sorted((category, t1) -> category.getName().length() - t1.getName().length())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Category>() {
                    @Override
                    public void onNext(Category value) {
                        Log.i("test", value.getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



//        Disposable disposable1 = retrofit.create(RequestInterface.class)
//                .getCategories()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(new DisposableObserver<List<Category>>() {
//                    @Override
//                    public void onNext(List<Category> value) {
//                        Log.i("test", "1");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("test", e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i("test", "complete");
//                    }
//                });

        mCompositeDisposable.add(disposable0);
    }


    private void doSomeWork() {

        Flowable<Integer> observable = Flowable.just(1, 2, 3, 4);

        observable.reduce(50, (t1, t2) -> t1 + t2).subscribe(getObserver());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable.isDisposed()) {
            return;
        }
        mCompositeDisposable.dispose();
    }

    private SingleObserver<Integer> getObserver() {

        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer value) {
                Log.d(TAG, " onSuccess : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, " onError : " + e.getMessage());
            }
        };
    }
}
