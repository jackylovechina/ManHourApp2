package com.shg.manhourapp2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shg.manhourapp2.R;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class DateDialog extends DialogFragment {
    private TextView textView;
    private int whatDate;

    public void getVar(TextView textView, int whatDate) {
        this.textView = textView;
        this.whatDate = whatDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (whatDate) {
            case 1:
                builder.setTitle("开始时间");
                break;
            case 2:
                builder.setTitle("结束时间");
                break;
            default:
                builder.setTitle("选择时间");
                break;
        }
        builder.setView(view);

        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);


        return builder.create();
    }
}
