package com.shg.manhourapp2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

import com.shg.manhourapp2.R;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class DetailDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_detail, null);

        builder.setView(view);
        builder.setTitle("工时信息");
        builder.setIcon(android.R.drawable.ic_lock_idle_alarm);
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);


        return builder.create();
    }
}
