package org.team10424102.whisky.controllers.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Activity;

import java.text.SimpleDateFormat;
import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private List<Activity> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ActivitiesAdapter(List<Activity> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ActivitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Activity activity = mDataset.get(position);
        // TODO 设置封面
        Global.picasso.load(R.drawable.demo_activity_poster).into(holder.cover);
        // TODO 设置游戏图标
        Global.picasso.load(R.drawable.game_dota2).into(holder.gameLogo);
        holder.title.setText(activity.getTitle());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        holder.startTime.setText(df.format(activity.getStartTime()));
        holder.location.setText(activity.getLocation());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cover;
        public ImageView gameLogo;
        public TextView title;
        public TextView startTime;
        public TextView location;

        public ViewHolder(View view) {
            super(view);
            cover = (ImageView) view.findViewById(R.id.cover);
            gameLogo = (ImageView) view.findViewById(R.id.game_logo);
            title = (TextView) view.findViewById(R.id.title);
            startTime = (TextView) view.findViewById(R.id.start_time);
            location = (TextView) view.findViewById(R.id.location);
        }
    }
}