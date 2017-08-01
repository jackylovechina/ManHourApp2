package com.shg.manhourapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shg.manhourapp2.domain.LoginViewModel;
import com.shg.manhourapp2.domain.SysUser;
import com.shg.manhourapp2.utils.GlobalVar;
import com.shg.manhourapp2.utils.ServerApi;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.shg.manhourapp2.R.id.login;
import static com.shg.manhourapp2.utils.GlobalVar.sysUser;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mET_userNameLogin, mET_passwordLogin;
    private Button mBT_signInLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setListener();


    }

    private void initView() {

        mET_userNameLogin = (EditText) findViewById(R.id.et_userName_login);
        mET_passwordLogin = (EditText) findViewById(R.id.et_password_login);
        mBT_signInLogin = (Button) findViewById(R.id.bt_signIn_login);
    }

    private void setListener() {

        mBT_signInLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_signIn_login:

                if (TextUtils.isEmpty(mET_userNameLogin.getText().toString())) {

                    mET_userNameLogin.setError(getString(R.string.error_field_required));
                    return;

                }
                LoginViewModel loginViewModel = new LoginViewModel();
                loginViewModel.LoginID = mET_userNameLogin.getText().toString();
                loginViewModel.Password = mET_passwordLogin.getText().toString();
                onLoginCheck(loginViewModel);


                break;
        }
    }

    private void onLoginCheck(LoginViewModel loginViewModel) {

        final HttpManager httpManager = x.http();

        String urlLogin = ServerApi.Login;
        String urlCheck = ServerApi.Check;

        RequestParams params = new RequestParams(urlLogin + urlCheck);

        Gson gson = new Gson();
        String viewModel = gson.toJson(loginViewModel);
        params.setAsJsonContent(true);
        params.setBodyContent(viewModel);

        httpManager.post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GlobalVar.TOKEN = result;
                Toast.makeText(LoginActivity.this, "登录中" + result, Toast.LENGTH_LONG).show();
                getAccountInfo(httpManager, result);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    private void getAccountInfo(HttpManager httpManager, String token) {
        RequestParams params = new RequestParams(ServerApi.Login + ServerApi.GetAccountInfo);
        params.addHeader("Token", token);
        httpManager.get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.d("re", result);
                Gson gson = new Gson();

                GlobalVar.sysUser = gson.fromJson(result, SysUser.class);
                Log.d("sysuser", sysUser.employeeViewModel.name);
                String strUser = gson.toJson(sysUser);
                Log.d("strUser", strUser);
                judgeUser();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("re", "error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void judgeUser() {
        int moduleNums = 0;
        int moduleListNums = GlobalVar.sysUser.roleModuleFuncViewModels.size();
        for (int i = 0; i < moduleListNums; i++) {
            if (GlobalVar.sysUser.roleModuleFuncViewModels.get(i).module.sysName.equals("ManHourActualForAndroid")) {
                moduleNums++;
            }
        }
        if (GlobalVar.sysUser.employeeViewModel == null) {
            Toast.makeText(LoginActivity.this, "员工信息未绑定", Toast.LENGTH_LONG).show();
        } else if (GlobalVar.sysUser.roleModuleFuncViewModels == null) {
            Toast.makeText(LoginActivity.this, "员工未绑定权限", Toast.LENGTH_LONG).show();
        } else if (moduleNums == 0) {
            Toast.makeText(LoginActivity.this, "员工无手机访问权限", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }
}
