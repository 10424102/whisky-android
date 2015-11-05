package org.team10424102.whisky.controllers.sub;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.FragmentMyInfoBinding;

/**
 * Created by yy on 11/5/15.
 */
public class FragmentMyInfo extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentMyInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_info, container, false);
        binding.setProfile(Global.getProfile());

        binding.signatureBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.signatureEdit.getVisibility() == View.GONE) {
                    binding.signature.setVisibility(View.GONE);
                    binding.signatureEdit.setVisibility(View.VISIBLE);
                    binding.signatureEdit.setText(binding.signature.getText());
                    binding.signatureEdit.requestFocus();
                    binding.signatureEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {

                            } else {
                                // 提交更改数据
                                binding.signatureEdit.setVisibility(View.GONE);
                                binding.signature.setVisibility(View.VISIBLE);
                                Global.getProfile().setSignature(binding.signatureEdit.getText().toString());
                            }
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }
}

