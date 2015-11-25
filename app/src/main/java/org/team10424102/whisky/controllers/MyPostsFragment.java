package org.team10424102.whisky.controllers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.FragmentMyPostsBinding;

/**
 * Created by yy on 11/5/15.
 */
public class MyPostsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyPostsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_my_posts, container, false);
        binding.setHandlers(new Handlers());
        return binding.getRoot();
    }

    public static class Handlers {
        public void fuck(View view) {
            Log.i("yy", "fuck");
        }
    }
}

