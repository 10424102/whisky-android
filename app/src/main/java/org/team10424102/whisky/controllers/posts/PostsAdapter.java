package org.team10424102.whisky.controllers.posts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import org.team10424102.whisky.App;
import org.team10424102.whisky.databinding.ItemPostBinding;
import org.team10424102.whisky.models.Post;
import org.team10424102.whisky.models.extensions.PostExtension;
import org.team10424102.whisky.models.extensions.PostExtensionManager;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> mDataset;

    public PostsAdapter(List<Post> dataset) {
        mDataset = dataset;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        final Post post = mDataset.get(position);

        final PostExtensionManager postExtensionManager =
                (PostExtensionManager) App.getInstance().getComponent(PostExtensionManager.class);

        binding.stub.getViewStub().setLayoutResource(postExtensionManager.getLayout(post.getExtension()));

        //binding.stub.setLayoutResource(Global.postExtensionManager.getLayout(post.getExtension()));
        binding.stub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                postExtensionManager.render(post.getExtension(), inflated);
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
        public ItemPostBinding binding;

        public ViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
