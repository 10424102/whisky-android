package org.team10424102.whisky.controllers.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.controllers.adapters.PhotosAdapter;
import org.team10424102.whisky.databinding.FragmentMyPhotosBinding;
import org.team10424102.whisky.models.LazyImage;
import org.team10424102.whisky.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 11/5/15.
 */
public class FragmentMyPhotos extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyPhotosBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_photos, container, false);

        final RecyclerView list = binding.grid;
        final GridLayoutManager lm = new GridLayoutManager(list.getContext(), 50);
        list.setLayoutManager(lm);

        User u = new User();
        u.setDisplayName(Global.getProfile().getUsername());

        List<LazyImage> data = new ArrayList<>();
        data.add(new LazyImage());
        data.add(new LazyImage());
        data.add(new LazyImage());

        list.setAdapter(new PhotosAdapter(data));

        return binding.getRoot();
    }
}

