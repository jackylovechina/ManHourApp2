package com.shg.manhourapp2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shg.manhourapp2.R;
import com.shg.manhourapp2.utils.DatePopupWindows;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class UpdateDialog extends DialogFragment implements View.OnClickListener {
    private TextView updateStartTime_TV;
    private TextView center_TV;

//    public void getCenter(TextView center_TV) {
//
//        this.center_TV = center_TV;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_update, null);
        updateStartTime_TV = (TextView) view.findViewById(R.id.tv_update_startTime);
        center_TV = (TextView) view.findViewById(R.id.tv_update_center);

        updateStartTime_TV.setOnClickListener(this);

        builder.setView(view);
        builder.setTitle("实动工时");
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);

        return builder.create();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_update_startTime:

                DatePopupWindows.show(getActivity(), updateStartTime_TV, center_TV,"开始时间");

                break;
        }

    }
}
