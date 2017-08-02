package com.shg.manhourapp2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shg.manhourapp2.R;
import com.shg.manhourapp2.utils.GlobalVar;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class UserInfoDialog extends DialogFragment {
    private TextView userName_TV;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("个人信息");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_userinfo, null);
        userName_TV = (TextView) view.findViewById(R.id.tv_userInfo_name);
        userName_TV.setText(GlobalVar.sysUser.employeeViewModel.name);
        builder.setView(view);

        return builder.create();
    }
}
