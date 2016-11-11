package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shg.manhourapp2.R;

import java.util.List;

import domain.DispatchItemBean;
import domain.DispatchListBean;
import utils.DateTimeUtils;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class MyElvAdapter extends BaseExpandableListAdapter {

    private List<DispatchListBean> mDispatchListBean;
    private List<DispatchItemBean> mDispatchItemBean;

    public MyElvAdapter(List<DispatchListBean> mDispatchListBean) {
        this.mDispatchListBean = mDispatchListBean;
    }

    @Override
    public int getGroupCount() {
        return mDispatchListBean.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDispatchListBean.get(groupPosition).dispatchListItemsViewModel.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDispatchListBean.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDispatchListBean.get(groupPosition).dispatchListItemsViewModel.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_dispatch_group, parent, false);

            groupViewHolder = new GroupViewHolder();

            groupViewHolder.DispatchListImage_IV = (ImageView) convertView.findViewById(R.id.iv_dispatchList_imageView);
            groupViewHolder.DispatchListNum_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_dispatchListNum);
            groupViewHolder.ScheduledTime_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_scheduledTime);
            groupViewHolder.CreatTime_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_creatTime);
            groupViewHolder.DepartmentName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_departmentName);
            groupViewHolder.GroupName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_groupName);
            groupViewHolder.WorkingProcedureName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_workingProcedureName);
            groupViewHolder.ManHourTypeName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_manHourTypeName);
            groupViewHolder.DispatchException_TV = (TextView) convertView.findViewById(R.id.tv_dispatchList_dispatchException);

            convertView.setTag(groupViewHolder);

        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();

        }

        switch (1) {
            case 1:
                groupViewHolder.DispatchListImage_IV.setImageResource(R.drawable.uncomp_72px);
                break;
            case 2:
                groupViewHolder.DispatchListImage_IV.setImageResource(R.drawable.comp_72px);
                break;
            default:
                groupViewHolder.DispatchListImage_IV.setImageResource(R.drawable.comp_72px);
                break;
        }
        groupViewHolder.DispatchListNum_TV.setText("派工单编号:" + mDispatchListBean.get(groupPosition).num);
        groupViewHolder.ScheduledTime_TV.setText("计划时间:" + DateTimeUtils.getDateTime(mDispatchListBean.get(groupPosition).scheduledTime));
        groupViewHolder.CreatTime_TV.setText("创建时间:" + DateTimeUtils.getDateTime(mDispatchListBean.get(groupPosition).creatTime));
        groupViewHolder.DepartmentName_TV.setText("部门:" + mDispatchListBean.get(groupPosition).departmentName);
        groupViewHolder.GroupName_TV.setText("班组:" + mDispatchListBean.get(groupPosition).groupName);
        groupViewHolder.WorkingProcedureName_TV.setText("工序:" + mDispatchListBean.get(groupPosition).workingProcedureName);
        groupViewHolder.ManHourTypeName_TV.setText("类型:" + mDispatchListBean.get(groupPosition).manHourTypeName);
        if (mDispatchListBean.get(groupPosition).dispatchException != null && mDispatchListBean.get(groupPosition).dispatchException.length() != 0)
            groupViewHolder.DispatchException_TV.setText("派工单异常:" + mDispatchListBean.get(groupPosition).dispatchException);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder;
        if (convertView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_dispatch_child, parent, false);

            childViewHolder = new ChildViewHolder();

            childViewHolder.ItemListConstructionSiteName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchItem_ConstructionSiteName);
            childViewHolder.ItemListEquipmentName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchItem_EquipmentName);
            childViewHolder.ItemListMaterialName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchItem_MaterialName);
            childViewHolder.ItemListVolume_TV = (TextView) convertView.findViewById(R.id.tv_dispatchItem_Volume);
            childViewHolder.ItemListShiftName_TV = (TextView) convertView.findViewById(R.id.tv_dispatchItem_ShiftName);

            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.ItemListConstructionSiteName_TV.setText("场地:" + mDispatchListBean.get(groupPosition).dispatchListItemsViewModel.get(childPosition).constructionSiteName);
        childViewHolder.ItemListEquipmentName_TV.setText("设备:" + mDispatchListBean.get(groupPosition).dispatchListItemsViewModel.get(childPosition).equipmentName);
        childViewHolder.ItemListMaterialName_TV.setText("物料:" + mDispatchListBean.get(groupPosition).dispatchListItemsViewModel.get(childPosition).materialName);
        childViewHolder.ItemListVolume_TV.setText("物量:" + mDispatchListBean.get(groupPosition).dispatchListItemsViewModel.get(childPosition).volume);
        childViewHolder.ItemListShiftName_TV.setText("班次:" + mDispatchListBean.get(groupPosition).dispatchListItemsViewModel.get(childPosition).shiftName);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupViewHolder {

        ImageView DispatchListImage_IV;
        TextView DispatchListNum_TV;
        TextView ScheduledTime_TV;
        TextView CreatTime_TV;
        TextView DepartmentName_TV;
        TextView GroupName_TV;
        TextView WorkingProcedureName_TV;
        TextView ManHourTypeName_TV;
        TextView DispatchException_TV;

    }

    private static class ChildViewHolder {
        TextView ItemListConstructionSiteName_TV;
        TextView ItemListEquipmentName_TV;
        TextView ItemListMaterialName_TV;
        TextView ItemListVolume_TV;
        TextView ItemListShiftName_TV;
    }
}
