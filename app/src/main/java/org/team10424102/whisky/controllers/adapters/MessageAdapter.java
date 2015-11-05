package org.team10424102.whisky.controllers.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.models.Message;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageAdapter(List<Message> dataset) {
        mDataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 绑定数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Message msg = mDataset.get(position);
        holder.logo.setImageResource(msg.getLogo());
        holder.title.setText(msg.getTitle());
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        holder.time.setText(df.format(msg.getTime()));
        holder.summary.setText(msg.getContent().substring(0, Math.min(30, msg.getContent().length())));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView logo;
        public TextView title;
        public TextView summary;
        public TextView time;

        public ViewHolder(View view) {
            super(view);
            logo = (CircleImageView) view.findViewById(R.id.logo);
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            summary = (TextView) view.findViewById(R.id.summary);
        }
    }
}
