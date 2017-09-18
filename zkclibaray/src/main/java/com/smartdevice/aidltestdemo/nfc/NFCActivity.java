package com.smartdevice.aidltestdemo.nfc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.smartdevice.aidltestdemo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NFCActivity extends Activity {
	private NfcAdapter nfcAdapter;
	private TextView promt;
	private byte password[] = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff };
	public List<Nfc> list;
	private ListView listView;
	private Intent intents;

	private boolean isnews = true;
	private PendingIntent pendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	private Nfc mynfc;
	private String dataString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc);
		listView = (ListView) findViewById(R.id.listView1);
		promt = (TextView) findViewById(R.id.promt);
		// 获取默认的NFC控制器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);

		if (nfcAdapter == null) {
			promt.setText("Device can not support NFC！");
			finish();
			return;
		}
		if (!nfcAdapter.isEnabled()) {
			promt.setText("Please open NFC in system setting！");
			finish();
			return;
		}

		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		ndef.addCategory("*/*");
		mFilters = new IntentFilter[] { ndef };// 过滤器
		mTechLists = new String[][] {
				new String[] { MifareClassic.class.getName() },
				new String[] { NfcA.class.getName() } };// 允许扫描的标签类型

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 得到是否检测到ACTION_TECH_DISCOVERED触发
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
				mTechLists);
		if (isnews) {
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent()
					.getAction())) {
				// 处理该intent
				processIntent(getIntent());
				intents = getIntent();
				isnews = false;
			}
		}
	}

	// 字符序列转换为16进制字符串
	private String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);

			stringBuilder.append(buffer);

		}
		return stringBuilder.toString();
	}

	private String bytesToHexString2(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);

			stringBuilder.append(buffer);
			stringBuilder.append(" ");
		}
		return stringBuilder.toString();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		// 得到是否检测到ACTION_TECH_DISCOVERED触发
		// nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
		// mTechLists);
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			// 处理该intent
			processIntent(intent);
			intents = intent;
		}

	}

	class ListLongClick implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			mynfc = list.get(position);
			if (mynfc.type == 1) {
				LayoutInflater inflater = LayoutInflater.from(NFCActivity.this);
				View nfcwrite = inflater.inflate(R.layout.nfcwrite, null);
				final EditText data = (EditText) nfcwrite
						.findViewById(R.id.et_nfcdata);
				Builder builder = new Builder(NFCActivity.this);
				builder.setView(nfcwrite);
				builder.setTitle("Write to tag");
				builder.setPositiveButton("Write",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								dataString = data.getText().toString().trim();
								Tag tagFromIntent = intents
										.getParcelableExtra(NfcAdapter.EXTRA_TAG);
								MifareClassic mfc = MifareClassic
										.get(tagFromIntent);
								boolean auth = false;
								short sectorAddress = (short) mynfc
										.getSectorId();
								try {
									mfc.connect();

									auth = mfc.authenticateSectorWithKeyA(
											sectorAddress,
											MifareClassic.KEY_DEFAULT);
									// 必须为16字节不够自己补0
									byte[] d = dataString.getBytes();
									byte[] f = new byte[16];
									for (int j = 0; j < d.length; j++) {
										f[j] = d[j];
									}
									if (d.length < 16) {
										int j = 16 - d.length;
										int k = d.length;
										for (int j2 = 0; j2 < j; j2++) {
											f[k + j2] = (byte) 0x00;
										}
									}
									if (auth) {
										// 写入数据
										mfc.writeBlock(mynfc.getId(), f);
										mfc.close();
										Toast.makeText(NFCActivity.this,
												"Success", Toast.LENGTH_SHORT)
												.show();
										processIntent(getIntent());
									}
								} catch (IOException e) {
									Toast.makeText(NFCActivity.this, "Please put the tags on the equipment behind",
											3000).show();
									// TODO Auto-generated catch block
									e.printStackTrace();
								} finally {
									try {
										mfc.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}).setNegativeButton("Cancel", null);

				Dialog dialog = builder.create();
				dialog.show();
			}

			return false;
		}

	}

	/**
	 * 读取NFC信息数据
	 * 
	 * @param intent
	 */
	private void processIntent(Intent intent) {	
		boolean auth = false;
		String cardStr="";
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		cardStr="ID："+bytesToHexString(tag.getId());
        String[] techList = tag.getTechList();
        boolean haveMifareUltralight = false;
        cardStr+="\r\nTECH：";
        for (String tech : techList) {
        	cardStr+=tech+",";
            if (tech.indexOf("MifareClassic") >= 0) {
                haveMifareUltralight = true;
                break;
            }
        }
        if (!haveMifareUltralight) {
    		promt.setText(cardStr);
            Toast.makeText(this, "this card type is not MifareClassic", Toast.LENGTH_LONG).show();
            return;
        }
		
		MifareClassic mfc = MifareClassic.get(tag);
		
		try {
			String metaInfo = "";
			// Enable I/O operations to the tag from this TagTechnology object.
			mfc.connect();
			int type = mfc.getType();// 获取TAG的类型
			int sectorCount = mfc.getSectorCount();// 获取TAG中包含的扇区数
			String typeS = "";
			switch (type) {
			case MifareClassic.TYPE_CLASSIC:
				typeS = "TYPE_CLASSIC";
				break;
			case MifareClassic.TYPE_PLUS:
				typeS = "TYPE_PLUS";
				break;
			case MifareClassic.TYPE_PRO:
				typeS = "TYPE_PRO";
				break;
			case MifareClassic.TYPE_UNKNOWN:
				typeS = "TYPE_UNKNOWN";
				break;
			}
			byte[] myNFCID = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

			int before = (int) Long.parseLong(bytesToHexString(myNFCID), 16);

			int r24 = before >> 24 & 0x000000FF;
			int r8 = before >> 8 & 0x0000FF00;
			int l8 = before << 8 & 0x00FF0000;
			int l24 = before << 24 & 0xFF000000;

			metaInfo += "ID(dec):"
					+ Long.parseLong(
							Integer.toHexString((r24 | r8 | l8 | l24)), 16)
					+ "\nID(hex):" + bytesToHexString2(myNFCID) + "\nType："
					+ typeS + "\nSector：" + sectorCount + "\n Block："
					+ mfc.getBlockCount() + "\nSize： " + mfc.getSize() + "B";
			if (list == null) {
				list = new ArrayList<Nfc>();
			} else {
				list.clear();
			}

			for (int j = 0; j < sectorCount; j++) {
				auth = mfc.authenticateSectorWithKeyA(j,password);

				int bCount;
				int bIndex;
				if (auth) {
					list.add(new Nfc(j, 0, "Sector " + j + ": Authentication ok", j));

					// 读取扇区中的块
					bCount = mfc.getBlockCountInSector(j);
					bIndex = mfc.sectorToBlock(j);
					for (int i = 0; i < bCount; i++) {
						byte[] data = mfc.readBlock(bIndex);

						list.add(new Nfc(bIndex, 1, "Block " + bIndex + " : "
								+ bytesToHexString(data), j));
						bIndex++;
					}
				} else {

					list.add(new Nfc(j, 0, "Sector " + j + ": Authentication Failed", j));
				}
			}

			promt.setText(metaInfo);
			NfcsAdapter adapter = new NfcsAdapter(list, getApplicationContext());

			listView.setAdapter(adapter);
			listView.setOnItemLongClickListener(new ListLongClick());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (mfc != null) {
					mfc.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
