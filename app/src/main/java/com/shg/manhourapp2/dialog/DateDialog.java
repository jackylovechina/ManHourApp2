package com.shg.manhourapp2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.shg.manhourapp2.R;

import java.util.Calendar;

import static android.R.attr.y;
import static com.shg.manhourapp2.R.id.day;
import static com.shg.manhourapp2.R.id.month;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class DateDialog extends DialogFragment implements DatePicker.OnDateChangedListener {

    private DatePicker datePicker_DP;
    private TextView textView;
    private int whatDate;

    public void getVar(TextView textView, int whatDate) {
        this.textView = textView;
        this.whatDate = whatDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        datePicker_DP = (DatePicker) view.findViewById(R.id.dp_date_picker);

        initDatePicker();

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

    private void initDatePicker() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        int month = monthOfYear + 1;
        textView.setText(year + "-" + month + "-" + dayOfMonth);
        datePicker_DP.init(year, monthOfYear, dayOfMonth, this);

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        int month = monthOfYear + 1;
        textView.setText(year + "-" + month + "-" + dayOfMonth);
    }
}
