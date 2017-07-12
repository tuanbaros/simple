package simple.rxdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.List;
import simple.rxdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private DataAdapter mDataAdapter;
    private CompositeDisposable mCompositeDisposable;
    private List<Data> mAndroids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mCompositeDisposable = new CompositeDisposable();
        mAndroids = new ArrayList<>();

        mDataAdapter = new DataAdapter(mAndroids);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(mDataAdapter);

        RequestInterface request = MainService.getRequest(RequestInterface.class);
        Disposable disposable = request.getAndroid()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleComplete,
                        this::handleSubcribe);
//                .subscribeWith(new DisposableObserver<List<Data>>() {
//                    @Override
//                    public void onNext(List<Data> value) {
//                        Log.i(this.getClass().getName(), "next");
//                        handleResponse(value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(this.getClass().getName(), "error");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(this.getClass().getName(), "complete");
//                    }
//                });

        Disposable disposable1 = request.getAndroid()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError, this::handleComplete,
                        this::handleSubcribe);

//        mCompositeDisposable.add(disposable);
        mCompositeDisposable.addAll(disposable, disposable1);
    }

    private void handleResponse(List<Data> androidList) {
//        mAndroids.clear();
        mAndroids.addAll(androidList);
        mDataAdapter.notifyDataSetChanged();
//        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable error) {
//        Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleComplete() {
        Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();
    }

    private void handleSubcribe(Disposable disposable) {
//        Toast.makeText(this, "subcribe", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
