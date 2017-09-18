package com.smartdevice.aidltestdemo.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.smartdevice.aidltestdemo.R;
import com.smartdevice.aidltestdemo.util.FileUtil;

import java.io.File;


public class CmdDialog extends Dialog{

	private String cmdStr;
    private ListView lvCmdDisplay;
	private DialogCallBack callback;
	private Context mContext;
	String[] contents;

	public CmdDialog(Context context, DialogCallBack callback) {
		super(context, R.style.dialogTheme);
		this.mContext = context;
		this.callback = callback;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cmd_dialog);
		setCanceledOnTouchOutside(false);
		initView();
		initData();
		lvCmdDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				callback.submit((contents[position].split(","))[1]);
				dismiss();
			}
		});
	}

	private void initData() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "cmd.txt";
		//读取模板数据，按行保存
		File file = new File(path);
		String content="";
		String new_content="";
		if(file.exists()){
			//获取文件内容
			content = FileUtil.convertCodeAndGetText(file);
			contents = content.split("\\n");
			ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.adapter_listview, R.id.textview_itemname, contents);
			lvCmdDisplay.setAdapter(adapter);
			Log.e("data", content);
		}else{
			callback.submit("cmd.txt doesn't exist");
			dismiss();
			return;
		}
	}

	private void initView() {
		lvCmdDisplay = (ListView) findViewById(R.id.lvCmdDisplay);
	}

	public interface DialogCallBack {
		void submit(String cmd);
	}
}
