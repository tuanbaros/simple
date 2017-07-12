package simple.rxdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import simple.rxdemo.databinding.RecyclerItemBinding;

/**
 * Created by tuannt on 7/12/17.
 * Project: RxDemo
 * Package: simple.rxdemo
 */
class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Data> mAndroidList;

    DataAdapter(List<Data> androidList) {
        mAndroidList = androidList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
            LayoutInflater.from(parent.getContext());
        RecyclerItemBinding itemBinding =
            RecyclerItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mAndroidList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAndroidList == null ? 0 : mAndroidList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerItemBinding mItemBinding;

        ViewHolder(RecyclerItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mItemBinding = itemBinding;

            itemView.setOnClickListener(view -> {
                Context context = itemView.getContext();
                context.startActivity(new Intent(context, SecondActivity.class));
            });
        }

        void bind(Data data) {
            if (data == null) {
                return;
            }
            mItemBinding.setData(data);
            mItemBinding.executePendingBindings();
        }
    }
}
