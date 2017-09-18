package com.afrid.common.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yyyu.baselibrary.template.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public abstract class MyBaseActivity extends BaseActivity{

    private Unbinder mUnbind;

    @Override
    public void beforeInit() {
        super.beforeInit();
        mUnbind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbind!=null){
            mUnbind.unbind();
        }
    }

    protected void replaceFrg(int resId , Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(resId, fragment);
        ft.commit();
    }

}
