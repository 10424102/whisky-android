package org.team10424102.whisky.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Response;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.databinding.FragmentMyInfoBinding;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.ui.MyInfoItem;

import java.util.zip.Inflater;

/**
 * Created by yy on 11/5/15.
 */
public class MyInfoFragment extends Fragment {
    public static final String ARG_PROFILE = "profile";
    private final Handlers handlers = new Handlers(this);

    public static MyInfoFragment newInstance(Profile profile) {

        Bundle args = new Bundle();

        args.putParcelable(ARG_PROFILE, profile);

        MyInfoFragment fragment = new MyInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyInfoBinding binding = FragmentMyInfoBinding.inflate(inflater, container, false);

        final Profile profile = getArguments().getParcelable(ARG_PROFILE);
        binding.setProfile(profile);
        binding.setHandlers(handlers);

        return binding.getRoot();
    }

    public static class Handlers {
        private MyInfoItem lastEditItem;
        private final MyInfoFragment mFragment;

        public Handlers(MyInfoFragment fragment) {
            mFragment = fragment;
        }

        private void init(View view) {
            if (lastEditItem != null && lastEditItem != view) {
                lastEditItem.commit();
                lastEditItem = null;
            }
            lastEditItem = (MyInfoItem) view;
        }

        private void focus(View view) {
            //EditText et = (EditText) view.findViewById(R.id.edit);
            //et.requestFocus();
        }

        public void editSignature(View itemView) {
            init(itemView);
            focus(itemView);
        }

        public void commitSignature(ViewGroup contentRoot) {
//            EditText et = (EditText)contentRoot.findViewById(R.id.edit);
//            String text = et.getText().toString();
//            if (!App.getProfile().getSignature().equals(text)) {
//                App.api().updateSignature(text).enqueue(new ApiCallback<Response>(200){
//                    @Override
//                    protected void handleSuccess(Response result) {
//                        Toast.makeText(mFragment.getContext(), "更新成功", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
        }

        public void editHometown(View itemView) {
            init(itemView);
            focus(itemView);
        }

        public void commitHometown(ViewGroup contentRoot) {
        }

        public void editHighschool(View itemView) {
            init(itemView);
            focus(itemView);
        }

        public void commitHighschool(ViewGroup contentRoot) {

        }

        public void editNickname(View itemView) {
            init(itemView);
            focus(itemView);
        }

        public void commitNickname(ViewGroup contentRoot) {

        }

        public void editUsername(View itemView) {
            init(itemView);
            focus(itemView);
        }

        public void commitUsername(ViewGroup contentRoot) {

        }

        public void editGender(View itemView) {
            init(itemView);
        }

        public void commitGender(ViewGroup contentRoot) {

        }

        public void editBirthday(View itemView) {
            init(itemView);
        }

        public void editCollege(View itemView) {
            init(itemView);
        }
    }
}

