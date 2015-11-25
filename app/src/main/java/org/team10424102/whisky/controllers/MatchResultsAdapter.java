package org.team10424102.whisky.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Post;

import java.util.List;

public class MatchResultsAdapter extends RecyclerView.Adapter<MatchResultsAdapter.ViewHolder> {
    private List<Post> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MatchResultsAdapter(List<Post> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MatchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_result, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = mDataset.get(position);
        holder.message.setText(post.getContent());
        holder.comments.setText("123");
        holder.likes.setText("456");
        // TODO 加载游戏数据
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView comments;
        public TextView likes;

        public ViewHolder(View view) {
            super(view);
            message = (TextView) view.findViewById(R.id.message);
            comments = (TextView) view.findViewById(R.id.comments);
            likes = (TextView) view.findViewById(R.id.likes);
        }
    }
}

