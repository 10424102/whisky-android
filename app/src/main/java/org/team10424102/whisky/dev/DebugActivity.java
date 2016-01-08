package org.team10424102.whisky.dev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.controllers.WelcomeActivity;
import org.team10424102.whisky.databinding.DebugActivityBinding;
import org.team10424102.whisky.models.BlackServer;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.team10424102.whisky.App.DEFAULT_LOG_FILE_LOCATION;
import static org.team10424102.whisky.App.DEFAULT_LOG_FILE_NAME_PREFIX;
import static org.team10424102.whisky.App.DEFAULT_SERVER_ADDRESS;
import static org.team10424102.whisky.App.PREF_LOG_FILE_LOCATION;
import static org.team10424102.whisky.App.PREF_LOG_FILE_NAME_PREFIX;
import static org.team10424102.whisky.App.PREF_SERVER_ADDRESS;

/**
 * Created by yy on 11/7/15.
 */
public class DebugActivity extends AppCompatActivity {

    private EditText mServerAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags =
                    (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        DebugActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.debug_activity);

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

//        PersistenceService persistenceService = (PersistenceService) App.getInstance().getComponent(PersistenceService.class);
//
//
//        userList.setAdapter(new UserListAdapter(persistenceService.getAllUsers()));

        mServerAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerAddr.setError(null);
            }
        });
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
        editor.commit();

        //App.getInstance().init();

        final String host = "http://" + serverAddr;

        Observable.create(new Observable.OnSubscribe<BlackServer>() {
            @Override
            public void call(Subscriber<? super BlackServer> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                try {
                    URL url = new URL(host + "/status");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(1000);
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    int response = conn.getResponseCode();
                    InputStream is = conn.getInputStream();
                    ObjectMapper objectMapper = new ObjectMapper();
                    BlackServer server = objectMapper.readValue(is, BlackServer.class);
                    subscriber.onNext(server);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BlackServer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mServerAddr.setError("无法连接上服务器");
                    }

                    @Override
                    public void onNext(BlackServer blackServer) {
                        if (blackServer.getStatus().equals("OK")) {
                            Intent intent = new Intent(DebugActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            mServerAddr.setError("服务器维护中");
                        }
                    }
                });
    }
}
