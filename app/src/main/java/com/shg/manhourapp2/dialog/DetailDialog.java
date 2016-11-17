package com.shg.manhourapp2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shg.manhourapp2.R;
import com.shg.manhourapp2.domain.DispatchItemBean;
import com.shg.manhourapp2.utils.DateTimeUtils;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class DetailDialog extends DialogFragment {

    private TextView detailStartTime_TV;
    private TextView detailEndTime_TV;
    private DispatchItemBean dispatchItemBean;


    public void getVar(DispatchItemBean dispatchItemBean) {

        this.dispatchItemBean = dispatchItemBean;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_detail, null);
        detailStartTime_TV = (TextView) view.findViewById(R.id.tv_detail_startTime);
        detailEndTime_TV = (TextView) view.findViewById(R.id.tv_detail_endTime);
        initView();

        builder.setView(view);
        builder.setTitle("工时信息");
        builder.setIcon(android.R.drawable.ic_lock_idle_alarm);
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);


        return builder.create();
    }

    private void initView() {

        detailStartTime_TV.setText(DateTimeUtils.getStartTime(DateTimeUtils.getDateTime(dispatchItemBean.completeDatetime), dispatchItemBean.manHourActual, "开始时间"));
        detailEndTime_TV.setText(DateTimeUtils.getDateTime(dispatchItemBean.completeDatetime, "结束时间"));

    }
}
