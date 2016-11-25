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

                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();


                break;
        }
    }
}
