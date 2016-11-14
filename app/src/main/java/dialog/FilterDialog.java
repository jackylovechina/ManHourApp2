package dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.shg.manhourapp2.MainActivity;
import com.shg.manhourapp2.R;

import utils.GlobalVar;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class FilterDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener {

    private Switch filterIsComp_SW;

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
        filterIsComp_SW = (Switch) view.findViewById(R.id.sw_filter_isComp);
        filterIsComp_SW.setChecked(GlobalVar.ISCOMP);
        filterIsComp_SW.setOnCheckedChangeListener(this);

        builder.setTitle("条件筛选");
        builder.setView(view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                GlobalVar.ISCOMP = filterIsComp_SW.isChecked();
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

        return builder.create();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.sw_filter_isComp:

//                GlobalVar.ISCOMP = isChecked;
                Log.d("MyLog", isChecked + "");

                break;
        }

    }
}
