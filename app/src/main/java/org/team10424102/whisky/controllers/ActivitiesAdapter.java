package org.team10424102.whisky.controllers;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.components.ErrorManager;
import org.team10424102.whisky.databinding.ItemActivityBinding;
import org.team10424102.whisky.models.Activity;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private List<Activity> dataset;
    @Inject BlackServerApi api;

    public ActivitiesAdapter(List<Activity> dataset) {
        this.dataset = dataset;
        App.getInstance().getObjectGraph().inject(this);
    }

    @Override
    public ActivitiesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int position) {
        ItemActivityBinding binding =
                ItemActivityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setTag(dataset.get(position).getId());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                long id = (long) v.getTag();
                api.getActivity(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Activity>() {
                            @Override
                            public void call(Activity activity) {
                                Intent intent = new Intent(v.getContext(), ActivitiesDetailsActivity.class);
                                intent.putExtra("activity", activity);
                                v.getContext().startActivity(intent);
                            }
                        });
            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Activity activity = dataset.get(position);
        holder.binding.setActivity(activity);
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemActivityBinding binding;

        public ViewHolder(ItemActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}