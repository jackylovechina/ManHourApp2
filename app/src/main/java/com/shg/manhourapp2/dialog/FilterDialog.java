package com.shg.manhourapp2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.shg.manhourapp2.MainActivity;
import com.shg.manhourapp2.R;
import com.shg.manhourapp2.utils.GlobalVar;


import static android.R.attr.filter;
import static com.google.gson.internal.UnsafeAllocator.create;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class FilterDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private Switch filterIsComp_SW;
    private LinearLayout filterCondition_LL;
    private EditText filterWorkProcedure_ET;
    private RadioGroup filterRegion_RG;
    private RadioButton filter7early_RB;
    private RadioButton filter30early_RB;
    private RadioButton filterCustom_RB;
    private LinearLayout filterCustom_LL;
    private TextView filterStartDate_TV;
    private TextView filterEndDate_TV;

    private MainActivity mainActivity;


    public void setFilter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_filter, null);

        initView(view);

        filterIsComp_SW.setChecked(GlobalVar.ISCOMP);
        filterIsComp_SW.setOnCheckedChangeListener(this);
        filterRegion_RG.setOnCheckedChangeListener(this);

        filterStartDate_TV.setOnClickListener(this);
        filterEndDate_TV.setOnClickListener(this);

        if (GlobalVar.ISCOMP)
            filterCondition_LL.setVisibility(View.VISIBLE);
        else
            filterCondition_LL.setVisibility(View.GONE);

        if (filterCustom_RB.isChecked())
            filterCustom_LL.setVisibility(View.VISIBLE);
        else
            filterCustom_LL.setVisibility(View.GONE);

        builder.setTitle("条件筛选");
        builder.setIcon(android.R.drawable.ic_menu_edit);
        builder.setView(view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                GlobalVar.ISCOMP = filterIsComp_SW.isChecked();
                GlobalVar.SEVEN_EARLY = filter7early_RB.isChecked();
                GlobalVar.THIRTY_EARLY = filter30early_RB.isChecked();
                GlobalVar.CUSTOM = filterCustom_RB.isChecked();

                mainActivity.onRefresh();

                if (GlobalVar.ISCOMP) {

                    mainActivity.setTitle("已完成派工单");

                } else {

                    mainActivity.setTitle("未完成派工单");

                }

                Log.d("MyLog", GlobalVar.ISCOMP + "");
            }
        });
        builder.setNegativeButton("取消", null);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.sw_filter_isComp:

                if (isChecked)

                    filterCondition_LL.setVisibility(View.VISIBLE);
                else
                    filterCondition_LL.setVisibility(View.GONE);

                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {


            case R.id.rb_filter_custom:

                if (filterCustom_RB.isChecked())
                    filterCustom_LL.setVisibility(View.VISIBLE);
                else
                    filterCustom_LL.setVisibility(View.GONE);

                break;
            default:
                if (filter7early_RB.isChecked() || filter30early_RB.isChecked())
                    filterCustom_LL.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_filter_startDate:
                DateDialog startDateDialog = new DateDialog();
                startDateDialog.getVar(filterStartDate_TV, 1);
                startDateDialog.show(getFragmentManager(), "startTime");
                break;

            case R.id.tv_filter_endDate:

                DateDialog endDateDialog = new DateDialog();
                endDateDialog.getVar(filterEndDate_TV, 2);
                endDateDialog.show(getFragmentManager(), "endTime");

                break;

        }

    }

    private void initView(View view) {

        filterIsComp_SW = (Switch) view.findViewById(R.id.sw_filter_isComp);
        filterCondition_LL = (LinearLayout) view.findViewById(R.id.ll_filter_condition);
        filterRegion_RG = (RadioGroup) view.findViewById(R.id.rg_filter_region);
        filter7early_RB = (RadioButton) view.findViewById(R.id.rb_filter_7early);
        filter30early_RB = (RadioButton) view.findViewById(R.id.rb_filter_30early);
        filterCustom_RB = (RadioButton) view.findViewById(R.id.rb_filter_custom);
        filterCustom_LL = (LinearLayout) view.findViewById(R.id.ll_filter_custom);

        filterStartDate_TV = (TextView) view.findViewById(R.id.tv_filter_startDate);
        filterEndDate_TV = (TextView) view.findViewById(R.id.tv_filter_endDate);

    }

}
