package com.wipro.androidproficiencyexercise.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wipro.androidproficiencyexercise.R;
import com.wipro.androidproficiencyexercise.interfaces.AppInterfaces;
import com.wipro.androidproficiencyexercise.pojo.WSResponse;
import com.wipro.androidproficiencyexercise.view.adapter.RecyclerViewAdapter;

public class AndroidExerciseMainActivity extends AppBaseActivity implements AppInterfaces.ViewInterface {

    private final String TAG = AndroidExerciseMainActivity.this.getClass().getName();
    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    private RecyclerView.Adapter recyclerViewadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAppBar(getResources().getString(R.string.init_appbar_title)); //Set AppBar default title

        initRecycleView(); //init RecyclerView
    }

    private void initRecycleView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManagerOfrecyclerView);

        doNetworkCall(); //Call web-service to get data to display in list.
    }

    @Override
    public void updateNetworkResponseSuccess(WSResponse netRes) {
        dismissProgress();
        setAppBar(netRes.getTitle());

        recyclerViewadapter = new RecyclerViewAdapter(netRes.getRows(), this);
        recyclerview.setAdapter(recyclerViewadapter);
    }

    @Override
    public void updateNetworkResponseError(String netRes) {
        showAlert(netRes);
    }
}
