package org.team10424102.whisky.models.extensions.image;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.ItemImageBinding;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.extensions.PostExtension;
import org.team10424102.whisky.models.extensions.PostExtensionIdentifier;

import java.util.List;

@PostExtensionIdentifier("image")
public class ImagePostExtension implements PostExtension<Gallery> {
    @Override
    @SuppressWarnings("unchecked")
    public void render(Gallery gallery, View view) {
        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(new ImageAdapter(gallery.getImages()));
    }

    @Override
    public int getLayout() {
        return R.layout.ext_image;
    }

    @Override
    public Parcelable parseJson(JsonNode dataNode, JsonParser jp) throws JsonProcessingException {
        Gallery gallery = new Gallery();
        for (JsonNode token : dataNode) {
            LazyImage image = new LazyImage(token.asText());
            gallery.getImages().add(image);
        }
        return gallery;
    }

    public static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        private List<LazyImage> mDataset;

        public ImageAdapter(List<LazyImage> dataset) {
            mDataset = dataset;
        }

        @Override
        public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemImageBinding binding = ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.binding.setImage(mDataset.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ItemImageBinding binding;

            public ViewHolder(ItemImageBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
