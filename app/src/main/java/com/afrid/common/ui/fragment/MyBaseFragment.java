package com.afrid.common.ui.fragment;

import com.yyyu.baselibrary.template.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public abstract class MyBaseFragment extends BaseFragment{

    private Unbinder mUnbind;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        mUnbind = ButterKnife.bind(this, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbind.unbind();
    }




}
