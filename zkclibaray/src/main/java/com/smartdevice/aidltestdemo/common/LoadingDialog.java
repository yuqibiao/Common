package com.smartdevice.aidltestdemo.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.smartdevice.aidltestdemo.R;

public class LoadingDialog extends Dialog {

	private View view;

	private TextView tvContent;

	public LoadingDialog(Context context) {
		this(context, R.style.dialogTheme);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);

		view = View.inflate(context, R.layout.dialog_loading, null);

		setCanceledOnTouchOutside(false);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		setContentView(view, lp);

		init(view);
	}

	private void init(View view) {

	}

	public void setAlpha(float f) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = f;
		getWindow().setAttributes(lp);
	}

	public void setContent(int resId) {
		if (tvContent == null) {
			tvContent = (TextView) view.findViewById(R.id.tv_loading_text);
		}

		tvContent.setText(resId);
	}

	public void setContent(String str) {
		if (tvContent == null) {
			tvContent = (TextView) view.findViewById(R.id.tv_loading_text);
		}
		tvContent.setText(str);
	}
}
