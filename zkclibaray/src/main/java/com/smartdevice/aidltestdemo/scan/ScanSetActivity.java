package com.smartdevice.aidltestdemo.scan;

import java.io.UnsupportedEncodingException;

import com.smartdevice.aidltestdemo.BaseActivity;
import com.smartdevice.aidltestdemo.R;
import com.smartdevice.aidltestdemo.util.ExecutorFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ScanSetActivity extends BaseActivity implements OnClickListener{
	
	private CheckBox checkBox_hex;
	private EditText editText_command;
	private Button button_sendcommand,btnRecoveryFactory;
	
	private ScrollView scrollView_setting;
	//初始化控件
	private CheckBox checkbox_openScan,checkbox_keyBordInput,checkbox_addEnter,checkbox_openSound,checkbox_openVibration,checkbox_continueScan,checkbox_repeatScanTip,checkbox_reset;

	private boolean runFlag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vp_twopage);
		initView();
		setCheckBoxEnable(false);
		
		ExecutorFactory.executeThread(new Runnable() {

			@Override
			public void run() {
				while (runFlag) {
					if (bindSuccessFlag) {
						mHandler.sendEmptyMessage(0);
						runFlag = false;
					}
				}
			}
		});
		
	}
	
	private void setCheckBoxEnable(boolean enable) {
		checkbox_openScan.setEnabled(enable);
		
	}

	Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				setCheckBoxEnable(true);
				break;
			default:
				break;
			}
			return false;
		}
	});

	private void initView() {
		
		scrollView_setting=(ScrollView)findViewById(R.id.scrollView_setting);
		
		checkBox_hex=(CheckBox)findViewById(R.id.checkBox_hex);
		
		editText_command=(EditText)findViewById(R.id.editText_command);
		
		button_sendcommand=(Button)findViewById(R.id.button_sendcommand);
		button_sendcommand.setOnClickListener(this);
		
		checkbox_openScan=(CheckBox)findViewById(R.id.checkbox_openScan);
		checkbox_openScan.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_openScan.setChecked(ClientConfig.getBoolean(ClientConfig.OPEN_SCAN));
		
		checkbox_keyBordInput=(CheckBox)findViewById(R.id.checkbox_keyBordInput);
		checkbox_keyBordInput.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_keyBordInput.setChecked(ClientConfig.getBoolean(ClientConfig.OPEN_SCAN));
		
		checkbox_addEnter=(CheckBox)findViewById(R.id.checkbox_addEnter);
		checkbox_addEnter.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_addEnter.setChecked(ClientConfig.getBoolean(ClientConfig.DATA_APPEND_ENTER));
		
		checkbox_openSound=(CheckBox)findViewById(R.id.checkbox_openSound);
		checkbox_openSound.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_openSound.setChecked(ClientConfig.getBoolean(ClientConfig.APPEND_RINGTONE));
		
		checkbox_openVibration=(CheckBox)findViewById(R.id.checkbox_openVibration);
		checkbox_openVibration.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_openVibration.setChecked(ClientConfig.getBoolean(ClientConfig.APPEND_VIBRATE));
		
		checkbox_continueScan=(CheckBox)findViewById(R.id.checkbox_continueScan);
		checkbox_continueScan.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_continueScan.setChecked(ClientConfig.getBoolean(ClientConfig.CONTINUE_SCAN));
		
		checkbox_repeatScanTip=(CheckBox)findViewById(R.id.checkbox_repeatScanTip);
		checkbox_repeatScanTip.setOnCheckedChangeListener(new checkBoxCheckedChangeListener());
		checkbox_repeatScanTip.setChecked(ClientConfig.getBoolean(ClientConfig.SCAN_REPEAT));
		btnRecoveryFactory = (Button) findViewById(R.id.btnRecoveryFactory);
		btnRecoveryFactory.setOnClickListener(this);
		
	}

	private void sendCommand() {
		try{
            String str=editText_command.getText().toString();
            if(checkBox_hex.isChecked()){
                if(mIzkcService!=null){
                    mIzkcService.sendCommand(StringToByteArray(str));
                }
            }else{
                try {
                    if(mIzkcService!=null){
                        mIzkcService.sendCommand(str.getBytes("US-ASCII"));
                    }
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            }catch (Exception e) {
                Log.e("ScanSetActivity", e.getMessage());
                Toast.makeText(ScanSetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
	}

	static public byte[] StringToByteArray(String strInput)
	{
		int l = strInput.length() / 2;  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            ret[i] = (byte) Integer  
                    .valueOf(strInput.substring(i * 2, i * 2 + 2), 16).byteValue();  
        }  
        return ret;
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btnRecoveryFactory) {
			try {
				mIzkcService.recoveryFactorySet(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		} else if (i == R.id.button_sendcommand) {
			sendCommand();

		}
	}

	class checkBoxCheckedChangeListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			try {
				int i = buttonView.getId();
				if (i == R.id.checkbox_openScan) {
					if (isChecked) {
						scrollView_setting.setVisibility(View.VISIBLE);
					} else {
						scrollView_setting.setVisibility(View.GONE);
					}
					mIzkcService.openScan(isChecked);
					ClientConfig.setValue(ClientConfig.OPEN_SCAN, isChecked);

				} else if (i == R.id.checkbox_keyBordInput) {
				} else if (i == R.id.checkbox_addEnter) {
					mIzkcService.dataAppendEnter(isChecked);
					ClientConfig.setValue(ClientConfig.DATA_APPEND_ENTER, isChecked);

				} else if (i == R.id.checkbox_openSound) {
					mIzkcService.appendRingTone(isChecked);
					ClientConfig.setValue(ClientConfig.APPEND_RINGTONE, isChecked);

				} else if (i == R.id.checkbox_openVibration) {
					ClientConfig.setValue(ClientConfig.APPEND_VIBRATE, isChecked);

				} else if (i == R.id.checkbox_continueScan) {
					mIzkcService.continueScan(isChecked);
					ClientConfig.setValue(ClientConfig.CONTINUE_SCAN, isChecked);

				} else if (i == R.id.checkbox_repeatScanTip) {
					mIzkcService.scanRepeatHint(isChecked);
					ClientConfig.setValue(ClientConfig.SCAN_REPEAT, isChecked);

				} else {
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
