package org.team10424102.whisky.controllers;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.components.ErrorManager;
import org.team10424102.whisky.databinding.ItemActivityBinding;
import org.team10424102.whisky.models.Activity;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private List<Activity> mDataset;
    @Inject BlackServerApi mApi;
    @Inject ErrorManager mErrorManager;

    public ActivitiesAdapter(List<Activity> dataset) {
        mDataset = dataset;
        App.getInstance().getObjectGraph().inject(this);
    }

    @Override
    public ActivitiesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int position) {
        ItemActivityBinding binding =
                ItemActivityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setTag(mDataset.get(position).getId());
        binding.getRoot().setOnClickListener(v -> {
            long id = (long) v.getTag();
            mApi.getActivity(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Activity>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Activity activity) {
                            Intent intent = new Intent(v.getContext(), ActivitiesDetailsActivity.class);
                            intent.putExtra("activity", activity);
                            v.getContext().startActivity(intent);
                        }
                    });
        });
        return new ViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Activity activity = mDataset.get(position);
        holder.binding.setActivity(activity);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemActivityBinding binding;

        public ViewHolder(ItemActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}