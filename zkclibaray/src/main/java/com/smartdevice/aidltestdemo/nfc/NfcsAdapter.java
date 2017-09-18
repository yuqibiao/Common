package com.smartdevice.aidltestdemo.nfc;

import java.util.List;

import com.smartdevice.aidltestdemo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NfcsAdapter extends BaseAdapter {
	private List<Nfc> list;
	private Context context;
	public NfcsAdapter(List<Nfc> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater= LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.nfcitem, null);
		TextView textView=(TextView) view.findViewById(R.id.tv_nfc_content);
		textView.setText(list.get(position).getContent());
		 
		
		return view;
	}
	
}
