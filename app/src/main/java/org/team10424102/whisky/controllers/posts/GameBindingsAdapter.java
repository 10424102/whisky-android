package org.team10424102.whisky.controllers.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.ItemGameBindingBinding;

public class GameBindingsAdapter extends RecyclerView.Adapter<GameBindingsAdapter.ViewHolder> {

    @Override
    public GameBindingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ItemGameBindingBinding binding = ItemGameBindingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        App.getPicasso().load(R.drawable.dummy_avatar).into(holder.binding.avatar);
        Bitmap image = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.dummy_avatar);
        Palette.from(image).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                holder.binding.avatar.setBorderColor(palette.getMutedColor(Color.LTGRAY));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemGameBindingBinding binding;

        public ViewHolder(ItemGameBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
