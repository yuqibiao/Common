package com.smartdevice.aidltestdemo.nfc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.smartdevice.aidltestdemo.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class NFCAcmdActivity extends Activity {

	private static final String TAG = null;
	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;

	private static boolean READ_LOCK = false;
	
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfca_cmd);
		
		textview=(TextView)findViewById(R.id.textView1);

		mAdapter = NfcAdapter.getDefaultAdapter(this);
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

		mFilters = new IntentFilter[] { ndef, };
		mTechLists = new String[][] {
				new String[] { MifareClassic.class.getName() },
				new String[] { NfcA.class.getName() } };

		// 得到是否检测到ACTION_TECH_DISCOVERED触发
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
			// 处理该intent
			resolveIntentNfcA(getIntent());
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		if (mAdapter != null && (!mAdapter.isEnabled())) {
			// showAlert(NFC_OFF, getString(R.string.error5));
		}

		if (mAdapter != null) {
			mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
					mTechLists);
		}

	}

	@Override
	public void onNewIntent(Intent intent) {
		resolveIntentNfcA(intent);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mAdapter != null) {
			mAdapter.disableForegroundDispatch(this);
		}
	}
	
	void resolveIntentNfcA(Intent intent) {
		if (READ_LOCK == false) {
			READ_LOCK = true;
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
				Tag tagFromIntent = intent
						.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				Log.i(TAG, Arrays.toString(tagFromIntent.getTechList()));
				textview.setText("");

				try {
					NfcA nfc = NfcA.get(tag);
					nfc.connect();
					textview.setText("connect to card");
					byte[] get_ramdom = { 0x00, (byte) 0x84, 0x00, 0x00, 0x04 };
					byte[] data_ramdom = nfc.transceive(get_ramdom);
					if (data_ramdom[0] != 0) {
						byte[] select_master={0x00,(byte) 0xA4,0x00,0x00,0x00};
						byte[] result = nfc.transceive(select_master);
						Log.i(TAG, "是否已写入数据" + result[0]);
						/*byte[] DATA_READ = { 0x1d, 0x00, 0x00, 0x00, 0x00,
								0x00, 0x08, 0x01, 0x08 };
						byte[] data_res = nfc.transceive(DATA_READ);
						Log.i(TAG, "读卡成功");
						
						if (result[0] != 0) {
							byte[] READ_UID = { 0x00,  0x36,  0x00,  0x00,  0x08 };
							byte[] uid_res = nfc.transceive(DATA_READ);
							Log.i(TAG, "读卡成功");
							textview.setText(gb2312ToString(uid_res));
						}*/
					}
					nfc.close();
				} catch (IOException e) {
					//e.printStackTrace();
					Log.e(TAG, "读卡失败"+e.getMessage());
				} catch (Exception e) {
					// TODO: handle exception
					//e.printStackTrace();
					Log.e(TAG, "读卡失败"+e.getMessage());
				} finally {

				}

			}
			READ_LOCK = false;
		}
	}

	void resolveIntentNfcB(Intent intent) {
		if (READ_LOCK == false) {
			READ_LOCK = true;
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
				Tag tagFromIntent = intent
						.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				Log.i(TAG, Arrays.toString(tagFromIntent.getTechList()));

				try {
					NfcB nfc = NfcB.get(tag);
					nfc.connect();
					textview.setText("connect to card");
					byte[] SELECT = { 0x05, 0x00, 0x00 };

					byte[] result = nfc.transceive(SELECT);
					Log.i(TAG, "是否已写入数据" + result[0]);
					if (result[0] != 0) {
						byte[] DATA_READ = { 0x1d, 0x00, 0x00, 0x00, 0x00,
								0x00, 0x08, 0x01, 0x08 };
						byte[] data_res = nfc.transceive(DATA_READ);
						Log.i(TAG, "读卡成功");
						
						if (result[0] != 0) {
							byte[] READ_UID = { 0x00,  0x36,  0x00,  0x00,  0x08 };
							byte[] uid_res = nfc.transceive(DATA_READ);
							Log.i(TAG, "读卡成功");
							textview.setText(gb2312ToString(uid_res));
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
					Log.e(TAG, "读卡失败");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {

				}

			}
			READ_LOCK = false;
		}
	}

	// 将数据转换为GB2312
	private String gb2312ToString(byte[] data) {
		String str = null;
		try {
			str = new String(data, "gb2312");// "utf-8"
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}
}
