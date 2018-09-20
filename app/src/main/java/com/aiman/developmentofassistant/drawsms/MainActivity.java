package com.aiman.developmentofassistant.drawsms;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by linsheng on 2018/9/20.
 */

public class MainActivity extends AppCompatActivity {

    public static final int MSG_RECEIVED_CODE = 1;

    private EditText editText;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_RECEIVED_CODE ){
                String code = (String) msg.obj;
                //update UI
                editText.setText(code);
            }
        }
    };

    private SmsObserver oMbserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_query);

        oMbserver = new SmsObserver(MainActivity.this,mHandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri,true,oMbserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(oMbserver);
    }
}
