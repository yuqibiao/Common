package com.smartdevice.aidltestdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.smartdevice.aidl.IZKCService;
import com.smartdevice.aidltestdemo.common.LoadingDialog;

public class BaseActivity extends Activity {
	
	public static String MODULE_FLAG = "module_flag";
	public static int module_flag = 0;
	public static int DEVICE_MODEL = 0;
	private LoadingDialog loadingDialog;
	ScreenOnOffReceiver mReceiver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		module_flag = getIntent().getIntExtra(MODULE_FLAG, 8);
		bindService();
		mReceiver = new ScreenOnOffReceiver();
		IntentFilter screenStatusIF = new IntentFilter();
		screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
		screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, screenStatusIF);
	}

	
	public boolean bindSuccessFlag = false;
	public static IZKCService mIzkcService;
	private ServiceConnection mServiceConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e("client", "onServiceDisconnected");
			mIzkcService = null;
			bindSuccessFlag = false;
			Toast.makeText(BaseActivity.this, getString(R.string.service_bind_fail), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("client", "onServiceConnected");
			mIzkcService = IZKCService.Stub.asInterface(service);
			if(mIzkcService!=null){
				try {
					Toast.makeText(BaseActivity.this, getString(R.string.service_bind_success), Toast.LENGTH_SHORT).show();
					DEVICE_MODEL = mIzkcService.getDeviceModel();
					mIzkcService.setModuleFlag(module_flag);
					if(module_flag==3){
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
		if(module_flag==3){
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

	protected Dialog getLoadingDialog() {
		if (loadingDialog == null) {
			loadingDialog = new LoadingDialog(getDialogContext());
		}
		return loadingDialog;
	}

	protected void showProgressDialog(String message) {
		getLoadingDialog();
		loadingDialog.setContent(message);
		loadingDialog.setCancelable(true);
		loadingDialog.show();
	}

	protected void dismissLoadDialog() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}
	}

	protected Context getDialogContext() {
		Activity activity = this;
		while (activity.getParent() != null) {
			activity = activity.getParent();
		}
		Log.d("Dialog", "context:" + activity);
		return activity;
	}

	public class ScreenOnOffReceiver extends BroadcastReceiver {

		private static final String TAG = "ScreenOnOffReceiver";
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("android.intent.action.SCREEN_ON")) {
//				SCREEN_ON = true;
				try {
					//打开电源+
					if(mIzkcService!=null){
						mIzkcService.setModuleFlag(8);
						SystemClock.sleep(1000);
						mIzkcService.setModuleFlag(module_flag);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			} else if (action.equals("android.intent.action.SCREEN_OFF")) {
//				SCREEN_ON = false;
//				try {
//					//关闭电源
//					mIzkcService.setModuleFlag(9);
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}
			}
		}
	}

}
