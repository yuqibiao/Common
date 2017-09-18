package com.smartdevice.aidltestdemo.language;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartdevice.aidltestdemo.R;

import java.util.List;

/**
 * Created by leoxu on 2017/4/17.
 */

public class SpinnerAdapterLanguage extends BaseAdapter {
    private List<LanguageModel> languageModelList;
    private Context mContext;
    private int view;

    public SpinnerAdapterLanguage(Context _mContext, int _view, List<LanguageModel> _languageModelList) {
        this.mContext = _mContext;
        this.view=_view;
        this.languageModelList = _languageModelList;
    }

    @Override
    public int getCount() {
        return languageModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return languageModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(view, null);
        if (convertView != null) {
            TextView _TextView1 = (TextView) convertView.findViewById(R.id.textview_itemname);
            _TextView1.setText(languageModelList.get(position).description + "(" + languageModelList.get(position).language + ")");
        }
        return convertView;
    }
}
