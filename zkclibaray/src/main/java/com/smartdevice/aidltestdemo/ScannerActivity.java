package com.smartdevice.aidltestdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartdevice.aidl.ICallBack;
import com.smartdevice.aidltestdemo.scan.ClientConfig;
import com.smartdevice.aidltestdemo.scan.ScanInstructionActivity;
import com.smartdevice.aidltestdemo.scan.ScanSetActivity;
import com.smartdevice.aidltestdemo.util.ExecutorFactory;

public class ScannerActivity extends BaseActivity implements OnClickListener {

	private EditText et_code;
	private Button btn_scan, btn_clear, btn_instruction, btn_set;
	private boolean runFlag = true;
	public String text = "";
	RemoteControlReceiver screenStatusReceiver = null;
	MediaPlayer player;
	Vibrator vibrator;
	private String firstCodeStr = "";
	private boolean beginToReceiverData = true;
	TextView tv_send,tv_receiver;
	int send = 0,receiver=0;

	ICallBack.Stub mCallback = new ICallBack.Stub() {

		@Override
		public void onReturnValue(byte[] buffer, int size)
				throws RemoteException {
			if(beginToReceiverData){
				beginToReceiverData = false;
				return;
			}
			String codeStr = new String(buffer, 0, size);
			if(ClientConfig.getBoolean(ClientConfig.SCAN_REPEAT)){
				if(firstCodeStr.equals(codeStr)){
					vibrator.vibrate(100);
				}
			}
			if(ClientConfig.getBoolean(ClientConfig.APPEND_RINGTONE)){
				player.start();
			}
			if(ClientConfig.getBoolean(ClientConfig.APPEND_VIBRATE)){					
				vibrator.vibrate(100);
			}
			firstCodeStr = codeStr;
			//发送到外部接收
			Intent intentBroadcast = new Intent();
			intentBroadcast.setAction("com.zkc.scancode");
			intentBroadcast.putExtra("code", codeStr);
			sendBroadcast(intentBroadcast);
			text += codeStr;			
			int startIndex = text.indexOf("{");
			int endIndex = text.indexOf("}");
			String keyStr = "";
			if (startIndex > -1 && endIndex > -1 && endIndex - startIndex < 5) {
				keyStr = text.substring(startIndex + 1, endIndex);
				text = text.substring(0, text.indexOf("{"));
			}

//			if (!keyStr.equals("")) {
//				text += "\r\n";
//			}

			if(DEVICE_MODEL!=3504||DEVICE_MODEL!=3503){
				text += "\r\n";
			}
			if(!TextUtils.isEmpty(text)){
				mHandler.sendEmptyMessage(1);
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner_two);
		initView();
		initEvent();
		beginToReceiverData = true;
		btn_scan.setEnabled(false);
		player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		screenStatusReceiver = new RemoteControlReceiver();
		IntentFilter screenStatusIF = new IntentFilter();
		screenStatusIF.addAction(Intent.ACTION_SCREEN_ON);
		screenStatusIF.addAction(Intent.ACTION_SCREEN_OFF);
		screenStatusIF.addAction(Intent.ACTION_SHUTDOWN);
		screenStatusIF.addAction("com.zkc.keycode");
		registerReceiver(screenStatusReceiver, screenStatusIF);
        //查询服务是否绑定成功，bindSuccessFlag为服务是否绑定成功的标记，在BaseActivity声明
		ExecutorFactory.executeThread(new Runnable() {

			@Override
			public void run() {
				while (runFlag) {
					if (bindSuccessFlag) {
						// 注册回调接口
						try {
							mIzkcService.registerCallBack("Scanner", mCallback);
							// 关闭线程
							runFlag = false;
							mHandler.sendEmptyMessage(0);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							runFlag = false;
							e.printStackTrace();
						}
					}
				}

			}
		});
	}

	Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				initScanSet();
				btn_scan.setEnabled(true);
				break;
			case 1:
				et_code.setText(text);
				tv_receiver.setText("R:"+ ++receiver);
				break;
			case 2:
				tv_send.setText("S:"+ ++send);
				break;
			default:
				break;
			}
			return false;
		}
	});

	private void initEvent() {
		btn_scan.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_instruction.setOnClickListener(this);
		btn_set.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {
		et_code = (EditText) findViewById(R.id.et_code);
		btn_scan = (Button) findViewById(R.id.btn_scan);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_instruction = (Button) findViewById(R.id.btn_instruction);
		btn_set = (Button) findViewById(R.id.btn_set);
		tv_receiver = (TextView) findViewById(R.id.tv_receiver);
		tv_send = (TextView) findViewById(R.id.tv_send);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		int i = v.getId();
		if (i == R.id.btn_scan) {
			beginToReceiverData = false;
			try {
				mIzkcService.scan();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (i == R.id.btn_clear) {
			et_code.setText("");
			text = "";
			tv_receiver.setText("R:" + "  ");
			tv_send.setText("S:" + "  ");
			send = 0;
			receiver = 0;

		} else if (i == R.id.btn_instruction) {
			intent = new Intent(this, ScanInstructionActivity.class);

		} else if (i == R.id.btn_set) {
			intent = new Intent(this, ScanSetActivity.class);
			intent.putExtra(BaseActivity.MODULE_FLAG, 4);

		} else {
		}

		if (intent != null)
			startActivity(intent);

	}
	
	void initScanSet(){
		if(mIzkcService!=null){
			try {
				mIzkcService.openScan(ClientConfig.getBoolean(ClientConfig.OPEN_SCAN));
				mIzkcService.dataAppendEnter(ClientConfig.getBoolean(ClientConfig.DATA_APPEND_ENTER));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//该BroadcastReceiver的意图在于接收扫描按键（受系统控制的产品不起作用），屏幕打开, 屏幕关闭的广播；
    //屏幕打开需要打开扫描模块，唤醒扫描功能；
    //屏幕关闭须要关闭扫描模块，开启省电模式；
	int count = 0;
	public class RemoteControlReceiver extends BroadcastReceiver {

		private static final String TAG = "RemoteControlReceiver";
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			beginToReceiverData = false;
			Log.i(TAG, "System message " + action);
			if(action.equals("com.zkc.keycode")) {
				if(count++>0){
					count = 0;
					int keyValue = intent.getIntExtra("keyvalue", 0);
					Log.i(TAG, "KEY VALUE:"+keyValue);
					if (keyValue == 136 || keyValue == 135 || keyValue == 131) {
						Log.i(TAG, "Scan key down.........");
						try {
							mIzkcService.scan();
							mHandler.sendEmptyMessage(2);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else if (action.equals("android.intent.action.SCREEN_ON")) {
				Log.i(TAG, "Power off,Close scan modules power.........");
				if(mIzkcService!=null){
					beginToReceiverData = true;
					try {
						if(mIzkcService!=null)
							mIzkcService.openScan(true);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (action.equals("android.intent.action.SCREEN_OFF")) {
				Log.i(TAG, "ACTION_SCREEN_OFF,Close scan modules power.........");
				try {
					if(mIzkcService!=null)
						mIzkcService.openScan(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
				Log.i(TAG, "ACTION_SCREEN_ON,Open scan modules power.........");			
				try {
					if(mIzkcService!=null)
						mIzkcService.openScan(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	protected void onPause() {
		beginToReceiverData = true;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		try {
			mIzkcService.unregisterCallBack("Scanner", mCallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		unregisterReceiver(screenStatusReceiver);
		super.onDestroy();
	}
}
