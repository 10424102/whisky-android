package org.team10424102.whisky.controllers;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.App;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.components.api.ApiService;
import org.team10424102.whisky.databinding.ItemActivityBinding;
import org.team10424102.whisky.models.Activity;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private List<Activity> mDataset;
    private final ApiService mApiService;

    public ActivitiesAdapter(List<Activity> dataset) {
        mDataset = dataset;
        mApiService = (ApiService)App.getInstance().getComponent(ApiService.class);
    }

    @Override
    public ActivitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ItemActivityBinding binding =
                ItemActivityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setTag(mDataset.get(position).getId());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                long id = (long) v.getTag();
                mApiService.getActivity(id).enqueue(new ApiCallback<Activity>() {
                    @Override
                    protected void handleSuccess(Activity result) {
                        Intent intent = new Intent(v.getContext(), ActivitiesDetailsActivity.class);
                        intent.putExtra("activity", result);
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