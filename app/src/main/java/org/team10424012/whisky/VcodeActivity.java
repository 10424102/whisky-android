package org.team10424012.whisky;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yy on 10/30/15.
 */
public class VcodeActivity extends AppCompatActivity {

    public static final int COUNTDOWN_LENGTH = 120;

    private String phone;
    private TextView hint;
    private TextView countdown;
    private Button register;
    private EditText[] vcode = new EditText[4];

    private int countdownNum = COUNTDOWN_LENGTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcode);
        phone = getIntent().getStringExtra("phone");
        hint = (TextView) findViewById(R.id.vcode_hint);
        countdown = (TextView) findViewById(R.id.countdown);
        register = (Button) findViewById(R.id.register);
        Resources res = getResources();
        hint.setText(String.format(res.getString(R.string.vcode_hint), phone));
        vcode[0] = (EditText) findViewById(R.id.vcode_1);
        vcode[1] = (EditText) findViewById(R.id.vcode_2);
        vcode[2] = (EditText) findViewById(R.id.vcode_3);
        vcode[3] = (EditText) findViewById(R.id.vcode_4);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VcodeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Resources res = getResources();
        final String countdownFormat = res.getString(R.string.vcode_countdown);
        final SpannableString resendText =
                new SpannableString(res.getString(R.string.vcode_resend));
        resendText.setSpan(new UnderlineSpan(), 0, resendText.length(), 0);
        countdown.setText(String.format(countdownFormat, countdownNum));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (countdownNum > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countdown.setText(String.format(countdownFormat, countdownNum));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countdown.setText(resendText);
                            countdown.setTextColor(Color.BLUE);
                        }
                    });
                }
                countdownNum = countdownNum - 1;
            }
        }, 1000, 1000);
    }
}
