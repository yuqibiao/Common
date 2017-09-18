package com.afrid.common.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afid.utils.ZKCManager;
import com.afrid.common.R;
import com.smartdevice.aidl.IZKCService;
import com.yyyu.baselibrary.template.BaseActivity;

public class TestActivity2 extends BaseActivity {

    private ZKCManager zkcManager;

    public static String MODULE_FLAG = "module_flag";
    public static int module_flag = 0;
    public static int DEVICE_MODEL = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        module_flag = getIntent().getIntExtra(MODULE_FLAG, 8);
        bindService();
        IntentFilter screenStatusIF = new IntentFilter();
        screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
        screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    public void printText(View view) {
        printGBKText();
    }

    public void printQRCode(View view) {

    }

    private void printGBKText() {
        try {
            mIzkcService.printTextWithFont("yuyuyu\nqqqqqqqqqqqq\nqqqqqqqqqqq\n", 0, 0);
        } catch (RemoteException e) {
            Log.e("", "远程服务未连接...");
            e.printStackTrace();
        }
    }


    public boolean bindSuccessFlag = false;
    public static IZKCService mIzkcService;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("client", "onServiceDisconnected");
            mIzkcService = null;
            bindSuccessFlag = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("client", "onServiceConnected");
            mIzkcService = IZKCService.Stub.asInterface(service);
            if (mIzkcService != null) {
                try {
                    DEVICE_MODEL = mIzkcService.getDeviceModel();
                    mIzkcService.setModuleFlag(module_flag);
                    if (module_flag == 3) {
                        mIzkcService.openBackLight(1);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                bindSuccessFlag = true;
            }
        }
    };

    public void bindService() {
        //com.zkc.aidl.all为远程服务的名称，不可更改
        //com.smartdevice.aidl为远程服务声明所在的包名，不可更改，
        // 对应的项目所导入的AIDL文件也应该在该包名下
        Intent intent = new Intent("com.zkc.aidl.all");
        intent.setPackage("com.smartdevice.aidl");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        unbindService(mServiceConn);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (module_flag == 3) {
            try {
                mIzkcService.openBackLight(0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbindService();
        super.onDestroy();
    }


}
