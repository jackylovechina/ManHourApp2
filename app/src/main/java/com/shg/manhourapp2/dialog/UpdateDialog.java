package com.shg.manhourapp2.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shg.manhourapp2.MainActivity;
import com.shg.manhourapp2.R;
import com.shg.manhourapp2.domain.DispatchItemBean;
import com.shg.manhourapp2.utils.DatePopupWindows;
import com.shg.manhourapp2.utils.DateTimeUtils;
import com.shg.manhourapp2.utils.ServerApi;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class UpdateDialog extends DialogFragment implements View.OnClickListener {
    private TextView updateStartTime_TV;
    private TextView updateEndTime_TV;
    private EditText updateRemark_ET;
    private TextView center_TV;
    private MainActivity activity;
    private DispatchItemBean dispatchItemBean;

    private String startTime_STR;
    private String endTime_STR;
    private String remark_STR;

    public void getVar(MainActivity activity, DispatchItemBean dispatchItemBean) {
        this.activity = activity;
        this.dispatchItemBean = dispatchItemBean;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_update, null);
        updateStartTime_TV = (TextView) view.findViewById(R.id.tv_update_startTime);
        updateEndTime_TV = (TextView) view.findViewById(R.id.tv_update_endTime);
        updateRemark_ET = (EditText) view.findViewById(R.id.et_update_remark);
        center_TV = (TextView) view.findViewById(R.id.tv_update_center);

        initTextView();

        updateStartTime_TV.setOnClickListener(this);
        updateEndTime_TV.setOnClickListener(this);

        builder.setView(view);
        builder.setTitle("实动工时");
        builder.setIcon(android.R.drawable.ic_lock_idle_alarm);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double computeManHourActual;
                getUpdateContent();
                if (startTime_STR.equals("开始时间") || endTime_STR.equals("结束时间")) {

                    Toast.makeText(activity, "请选择正确的时间", Toast.LENGTH_SHORT).show();

                    try {
                        Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                        field.setAccessible(true);
                        field.set(dialog, false);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                } else {

                    computeManHourActual = DateTimeUtils.computeManHourActual(startTime_STR, endTime_STR);

                    updateInfo(computeManHourActual, endTime_STR, remark_STR);


                    try {
                        Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                        field.setAccessible(true);
                        field.set(dialog, true);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog, true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        return builder.create();
    }

    private void initTextView() {

        updateStartTime_TV.setText(DateTimeUtils.getStartTime(DateTimeUtils.getDateTime(dispatchItemBean.completeDatetime), dispatchItemBean.manHourActual, "开始时间"));
        updateEndTime_TV.setText(DateTimeUtils.getDateTime(dispatchItemBean.completeDatetime, "结束时间"));
    }

    private void getUpdateContent() {

        startTime_STR = updateStartTime_TV.getText().toString();
        endTime_STR = updateEndTime_TV.getText().toString();
        remark_STR = updateRemark_ET.getText().toString();

    }

    private void updateInfo(double manHour, String endTime_STR, String remark_STR) {
        HttpManager update_manager = x.http();
        String url = ServerApi.Address;
        String order = ServerApi.UPDATE;
        RequestParams putParams = new RequestParams(url + order);


        DispatchItemBean d = new DispatchItemBean();
        d.manHourActualID = dispatchItemBean.manHourActualID;
        d.manHourActual = manHour;
        d.completeDatetime = endTime_STR;
        d.remark = remark_STR;

        Gson gson = new Gson();

        String viewModel = gson.toJson(d);

        Log.d("MyLog", viewModel);

        putParams.setAsJsonContent(true);
        putParams.setBodyContent(viewModel);

        update_manager.request(HttpMethod.PUT, putParams, new Callback.CommonCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {

                Log.d("MyLog", result + "");
                Toast.makeText(activity, "提交成功", Toast.LENGTH_SHORT).show();
                activity.onRefresh();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.d("MyLog", isOnCallback + "|" + ex.toString());
                Toast.makeText(activity, "提交失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_update_startTime:

                DatePopupWindows.show(getActivity(), updateStartTime_TV, center_TV, "工作开始时间");

                break;

            case R.id.tv_update_endTime:

                DatePopupWindows.show(getActivity(), updateEndTime_TV, center_TV, "工作结束时间");

                break;
        }

    }
}
