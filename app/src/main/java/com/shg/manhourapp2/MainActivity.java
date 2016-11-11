package com.shg.manhourapp2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import adapter.MyElvAdapter;
import domain.DispatchListBean;
import utils.ServerApi;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener ,ExpandableListView.OnChildClickListener{

    private SwipeRefreshLayout dispatchList_SRL;
    private ExpandableListView dispatchList_ELV;
    private FloatingActionButton fab;
    private List<DispatchListBean> mUnCompDispatchLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dispatchList_SRL = (SwipeRefreshLayout) findViewById(R.id.srl_dispatchList);
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

        RequestParams requestParams = new RequestParams(ServerApi.Address + ServerApi.GET_NOCOMPLETE);
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

        Log.d("MyLog",mUnCompDispatchLists.get(groupPosition).dispatchListItemsViewModel.get(childPosition).volume);

        return false;
    }
}
