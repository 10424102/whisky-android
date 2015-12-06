package org.team10424102.whisky.dev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.controllers.BaseActivity;
import org.team10424102.whisky.controllers.WelcomeActivity;
import org.team10424102.whisky.databinding.ActivityDebugBinding;
import org.team10424102.whisky.models.ServerHealth;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.team10424102.whisky.App.DEFAULT_LOG_FILE_LOCATION;
import static org.team10424102.whisky.App.DEFAULT_LOG_FILE_NAME_PREFIX;
import static org.team10424102.whisky.App.DEFAULT_SERVER_ADDRESS;
import static org.team10424102.whisky.App.PREF_LOG_FILE_LOCATION;
import static org.team10424102.whisky.App.PREF_LOG_FILE_NAME_PREFIX;
import static org.team10424102.whisky.App.PREF_SERVER_ADDRESS;

/**
 * Created by yy on 11/7/15.
 */
public class DebugActivity extends BaseActivity {


    private EditText mServerAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDebugBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_debug);

        mServerAddr = binding.address;


        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);


        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);

        String serverAddr = pref.getString(PREF_SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
        String logFileNamePrefix = pref.getString(PREF_LOG_FILE_NAME_PREFIX, DEFAULT_LOG_FILE_NAME_PREFIX);
        String logFileLocation = pref.getString(PREF_LOG_FILE_LOCATION, DEFAULT_LOG_FILE_LOCATION);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String logFileName = logFileNamePrefix + sdf.format(new Date());

        binding.setServerAddr(serverAddr);


        binding.setLogFileName(logFileName);
        binding.setLogFileLocation(logFileLocation);


        File root = Environment.getExternalStorageDirectory();


        RecyclerView userList = binding.userList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userList.setLayoutManager(linearLayoutManager);
        userList.setAdapter(new UserListAdapter(App.getDataService().getAllUsers()));
    }

    public void startApp(View view) {

        String serverAddr = mServerAddr.getText().toString();
        if (TextUtils.isEmpty(serverAddr)) {
            Toast.makeText(DebugActivity.this, "请填写服务器地址", Toast.LENGTH_LONG).show();
            return;
        }
        if (!serverAddr.contains(":")) {
            Toast.makeText(DebugActivity.this, "使用默认端口 8080", Toast.LENGTH_LONG).show();
            serverAddr += ":8080";
        }

        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_SERVER_ADDRESS, serverAddr);
        editor.apply();


        ((App)getApplication()).changeServerAddress(serverAddr);

        App.api().getServerHealth().enqueue(new ApiCallback<ServerHealth>() {
            @Override
            protected void handleSuccess(ServerHealth result) {
                if (result.getStatus().equals("UP")) {
                    Intent intent = new Intent(DebugActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DebugActivity.this, "服务器地址有误或者服务器未开启", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
