package com.shg.manhourapp2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import com.shg.manhourapp2.adapter.MyElvAdapter;
import com.shg.manhourapp2.dialog.DetailDialog;
import com.shg.manhourapp2.dialog.UpdateDialog;
import com.shg.manhourapp2.domain.DispatchListBean;
import com.shg.manhourapp2.dialog.FilterDialog;
import com.shg.manhourapp2.utils.DatePopupWindows;
import com.shg.manhourapp2.utils.GlobalVar;
import com.shg.manhourapp2.utils.ServerApi;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ExpandableListView.OnChildClickListener {

    private SwipeRefreshLayout dispatchList_SRL;
    private ExpandableListView dispatchList_ELV;
    private FloatingActionButton fab;

    private List<DispatchListBean> mUnCompDispatchLists;

    String url;
    String order;
    private MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = this;
        GlobalVar.ISCOMP = false;

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainActivity.setTitle("未完成派工单");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setAlpha(0.6f);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilterDialog filterDialog = new FilterDialog();
                filterDialog.setFilter(mainActivity);
                filterDialog.show(getFragmentManager(), "filterDialog");
//                fab.setAlpha(1.0f);

            }
        });

        dispatchList_SRL = (SwipeRefreshLayout) findViewById(R.id.srl_dispatchList);

        dispatchList_SRL.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        dispatchList_ELV = (ExpandableListView) findViewById(R.id.elv_dispatchList);

        dispatchList_SRL.setOnRefreshListener(this);
        dispatchList_ELV.setOnChildClickListener(this);

        onRefresh();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {

        HttpManager httpManager = x.http();

        url = ServerApi.Address;
        if (GlobalVar.ISCOMP == false) {
            order = ServerApi.GET_NOCOMPLETE;
        } else {
            order = ServerApi.GET_COMPLETE;
        }

        RequestParams requestParams = new RequestParams(url + order);
        requestParams.addParameter("employeeID", "36368FBA-08B4-48A7-BDC4-8511EFCDD820");

        httpManager.get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                mUnCompDispatchLists = gson.fromJson(result, new TypeToken<List<DispatchListBean>>() {
                }.getType());
                dispatchList_ELV.setAdapter(new MyElvAdapter(mUnCompDispatchLists));

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.d("MyLog", isOnCallback + "|" + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dispatchList_SRL.setRefreshing(false);
            }
        });

    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        if (GlobalVar.ISCOMP) {

            DetailDialog detailDialog = new DetailDialog();
            detailDialog.getVar(mUnCompDispatchLists.get(groupPosition).dispatchListItemsViewModel.get(childPosition));
            detailDialog.show(getFragmentManager(), "detail");
        } else {
            UpdateDialog updateDialog = new UpdateDialog();
            updateDialog.getVar(mainActivity, mUnCompDispatchLists.get(groupPosition).dispatchListItemsViewModel.get(childPosition));
            updateDialog.show(getFragmentManager(), "update");
        }
        return false;
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
