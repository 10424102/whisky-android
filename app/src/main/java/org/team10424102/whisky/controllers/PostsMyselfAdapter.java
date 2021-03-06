package org.team10424102.whisky.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import org.team10424102.whisky.App;
import org.team10424102.whisky.databinding.ItemPostMyselfBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.extensions.PostExtensionManager;

import java.util.List;

import javax.inject.Inject;

public class PostsMyselfAdapter extends RecyclerView.Adapter<PostsMyselfAdapter.ViewHolder> {
    private List<Post> mDataset;
    @Inject PostExtensionManager mPostExtensionManager;

    public PostsMyselfAdapter() {
        App.getInstance().getObjectGraph().inject(this);
    }

    public PostsMyselfAdapter(List<Post> dataset) {
        mDataset = dataset;
    }

    @Override
    public PostsMyselfAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ItemPostMyselfBinding binding = ItemPostMyselfBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        final Post post = mDataset.get(position);

        binding.stub.getViewStub().setLayoutResource(mPostExtensionManager.getLayout(post.getExtension()));

        binding.stub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                mPostExtensionManager.render(post.getExtension(), inflated);
            }
        });

        return new ViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Post post = mDataset.get(position);
        holder.binding.setPost(post);
        if (holder.binding.stub.getViewStub() != null) {
            holder.binding.stub.getViewStub().inflate();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemPostMyselfBinding binding;

        public ViewHolder(ItemPostMyselfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
