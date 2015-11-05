package org.team10424102.whisky.controllers.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Post;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PostsAdapter(List<Post> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = mDataset.get(position);
        holder.avatar.setImageResource(R.drawable.dummy_avatar);
        holder.name.setText(post.getSender().getDisplayName());
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        holder.time.setText(df.format(post.getSendTime()));
        holder.device.setText("来自" + post.getDevice());
        holder.content.setText(post.getContent());
        holder.comments.setText("123");
        holder.likes.setText("456");
        // TODO 加载游戏数据
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView avatar;
        public TextView name;
        public TextView time;
        public TextView device;
        public TextView content;
        public TextView comments;
        public TextView likes;

        public ViewHolder(View view) {
            super(view);
            avatar = (CircleImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            device = (TextView) view.findViewById(R.id.device);
            content = (TextView) view.findViewById(R.id.content);
            comments = (TextView) view.findViewById(R.id.comments);
            likes = (TextView) view.findViewById(R.id.likes);
        }
    }
}
