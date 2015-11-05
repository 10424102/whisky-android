package org.team10424102.whisky.controllers.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Post;

import java.text.SimpleDateFormat;
import java.util.List;

public class MatchResultsAdapter2 extends RecyclerView.Adapter<MatchResultsAdapter2.ViewHolder> {
    private List<Post> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MatchResultsAdapter2(List<Post> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MatchResultsAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_result_2, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = mDataset.get(position);
        Global.picasso.load(R.drawable.dummy_avatar).into(holder.avatar);
        Global.picasso.load(R.drawable.game_dota2).into(holder.gameLogo);
        holder.name.setText(post.getSender().getDisplayName());
        holder.signature.setText(post.getSender().getSignature());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        holder.time.setText("1小时前");
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
        public ImageView avatar;
        public TextView name;
        public TextView signature;
        public ImageView gameLogo;
        public TextView time;
        public TextView message;
        public TextView comments;
        public TextView likes;

        public ViewHolder(View view) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
            signature = (TextView) view.findViewById(R.id.signature);
            gameLogo = (ImageView) view.findViewById(R.id.game_logo);
            time = (TextView) view.findViewById(R.id.time);
            message = (TextView) view.findViewById(R.id.message);
            comments = (TextView) view.findViewById(R.id.comments);
            likes = (TextView) view.findViewById(R.id.likes);
        }
    }
}

