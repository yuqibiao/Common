package com.afrid.common.ui.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afrid.common.MyApplication;
import com.afrid.common.R;
import com.afrid.common.bean.json.LoginRequest;
import com.afrid.common.bean.json.return_data.LoginReturn;
import com.afrid.common.global.Constant;
import com.afrid.common.net.APIMethodManager;
import com.afrid.common.net.IRequestCallback;
import com.afrid.common.utils.BTManager;
import com.afrid.swingu.utils.SwingUManager;
import com.google.gson.Gson;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MySPUtils;
import com.yyyu.baselibrary.utils.MyToast;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import rx.Subscription;

/**
 * 功能：登录界面
 *
 * @author yu
 * @version 1.0
 * @date 2017/5/9
 */

public class LoginActivity extends MyBaseActivity {


    @BindView(R.id.tb_login)
    Toolbar tb_login;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    private APIMethodManager apiMethodManager;

    private static final String TAG = "LoginActivity";
    private MyApplication application;
    private SwingUManager swingUManager;
    private BTManager btManager;
    private Subscription subscription;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        application = (MyApplication) getApplication();
        apiMethodManager = APIMethodManager.getInstance();

    }

    @Override
    protected void initView() {
        setSupportActionBar(tb_login);
        getSupportActionBar().setTitle(R.string.login);
        btManager = BTManager.getInstance(this);
        swingUManager = SwingUManager.getInstance(this);
    }

    @Override
    protected void initListener() {
    }


    public void toLogin(View view) {
        String username = etUsername.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            MyToast.showShort(this, resourceUtils.getStr(R.string.lg_input_empty_tip));
            return;
        }
        showLoadDialog(resourceUtils.getStr(R.string.lg_loading_tip));
        LoginRequest loginRequest = new LoginRequest();
        LoginRequest.RequestDataBean user = new LoginRequest.RequestDataBean();
        user.setUsername(username);
        user.setPwd(pwd);
        loginRequest.setRequestData(user);
        //保存用户信息
        subscription = apiMethodManager.login(mGson.toJson(loginRequest), new IRequestCallback<LoginReturn>() {
            @Override
            public void onSuccess(LoginReturn result) {
                hiddenLoadingDialog();
                int resultCode = result.getResultCode();
                String msg = result.getMsg();
                if (resultCode == 200) {
                    MyToast.showLong(LoginActivity.this, "登陆成功");
                    //保存用户信息
                    MySPUtils.put(LoginActivity.this, Constant.USER_INFO, mGson.toJson(result));
                    LoginReturn.ResultDataBean resultData = result.getResultData();
                    int user_id = resultData.getUser_ID();
                    String user_name = resultData.getUser_NAME();
                    List<String> readerIdList = resultData.getReaderIdList();
                    application.setUser_id(user_id);
                    application.setUser_name(user_name);
                    application.setReaderIdList(readerIdList);
                    MainActivity.startAction(LoginActivity.this);
                    finish();
                } else if (resultCode == 500) {
                    MyToast.showLong(LoginActivity.this, msg);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                hiddenLoadingDialog();
                MyToast.showShort(LoginActivity.this , "网络连接异常，请检查网络！");
                MyLog.e(TAG, "异常" + throwable.getMessage());
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (subscription!=null&&!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    /**
     * 跳转界面
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

}
