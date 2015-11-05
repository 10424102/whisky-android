package org.team10424102.whisky.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;

/**
 * Created by yy on 11/7/15.
 */
public class DebugActivity extends AppCompatActivity {

    public static final String TAG_IP = "debug_server_ip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Global.initMobSmsSdk(this);
        setContentView(R.layout.activity_debug);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        final String ipStr = sharedPref.getString(TAG_IP, "");
        final EditText address = (EditText) findViewById(R.id.ip);
        address.setText(ipStr);
        final Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String addressStr = address.getText().toString();
                if (TextUtils.isEmpty(addressStr)) {
                    Toast.makeText(DebugActivity.this, "请填写服务器地址", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!addressStr.contains(":")) {
                    Toast.makeText(DebugActivity.this, "使用默认端口 8080", Toast.LENGTH_LONG).show();
                    addressStr += ":8080";
                }

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(TAG_IP, addressStr);
                editor.commit();

                if (Global.initRetrofit(DebugActivity.this, addressStr)) {
                    Intent intent = new Intent(DebugActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
