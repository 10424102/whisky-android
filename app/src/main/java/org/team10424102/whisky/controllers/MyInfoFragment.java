package org.team10424102.whisky.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.components.auth.AccountService;
import org.team10424102.whisky.databinding.MyInfoFragmentBinding;
import org.team10424102.whisky.ui.MyInfoItem;

/**
 * Created by yy on 11/5/15.
 */
public class MyInfoFragment extends Fragment {
    private final Handlers handlers = new Handlers(this);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MyInfoFragmentBinding binding = MyInfoFragmentBinding.inflate(inflater, container, false);

        Intent intent = new Intent(getContext(), AccountService.class);
        getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                AccountService.InnerBinder binder = (AccountService.InnerBinder) service;
                binding.setAccount(binder.getService().getCurrentAccount());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

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

