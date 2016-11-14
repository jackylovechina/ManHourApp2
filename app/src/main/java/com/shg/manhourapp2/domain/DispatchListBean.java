package com.shg.manhourapp2.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */

public class DispatchListBean implements Serializable{

    public String id;
    public String num;
    public String scheduledTime;
    public String complateTime;
    public String creatTime;
    public String dispatchException;
    public String groupName;
    public String manHourTypeName;
    public String workingProcedureName;
    public String departmentName;

    public List<DispatchItemBean> dispatchListItemsViewModel;
}
