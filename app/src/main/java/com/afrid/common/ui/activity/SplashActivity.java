package com.afrid.common.ui.activity;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.afrid.common.MyApplication;
import com.afrid.common.R;
import com.afrid.common.bean.json.return_data.LoginReturn;
import com.afrid.common.global.Constant;
import com.afrid.common.utils.BTManager;
import com.afrid.swingu.utils.SwingUManager;
import com.google.gson.Gson;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MySPUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SplashActivity extends AppCompatActivity {


    private static final String TAG = "SplashActivity";

    private ImageView ivSplash;
    private SwingUManager swingUManager;
    private BTManager btManager;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        ivSplash = (ImageView) findViewById(R.id.iv_splash);
        btManager = BTManager.getInstance(this);
        swingUManager = SwingUManager.getInstance(this);
        application = (MyApplication) getApplication();
        //---刷新蓝牙设备（已绑定的）
        Set<BluetoothDevice> deviceSet = btManager.getBoundsDevice();
        Iterator it = deviceSet.iterator();
        while (it.hasNext()) {
            BluetoothDevice device = (BluetoothDevice) it.next();
            String deviceName = device.getName();
            if (deviceName.startsWith("SwingU")) {
                MyLog.e(TAG , "device=="+device);
                swingUManager.connectDevice(device);
                //设置所连接手持机的id
                application.setCurrentReaderId(device.getAddress());
            }
        }

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anime);
        ivSplash.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String userInfoStr = (String) MySPUtils.get(SplashActivity.this, Constant.USER_INFO, "");

                MyLog.e(TAG , "userInfoStr："+userInfoStr);

                if (!TextUtils.isEmpty(userInfoStr)) {//已登录
                    LoginReturn userInfo = new Gson().fromJson(userInfoStr, LoginReturn.class);
                    int user_id = userInfo.getResultData().getUser_ID();
                    String user_name = userInfo.getResultData().getUser_NAME();
                    List<String> readerIdList = userInfo.getResultData().getReaderIdList();
                    application.setUser_id(user_id);
                    application.setUser_name(user_name);
                    application.setReaderIdList(readerIdList);

                    //---TODO 刷新数据

                    MainActivity.startAction(SplashActivity.this);
                } else {//未登录
                    LoginActivity.startAction(SplashActivity.this);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

}
