package com.wipro.androidproficiencyexercise.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wipro.androidproficiencyexercise.R;
import com.wipro.androidproficiencyexercise.application.AppController;
import com.wipro.androidproficiencyexercise.interfaces.AppInterfaces;
import com.wipro.androidproficiencyexercise.pojo.AppPojo;
import com.wipro.androidproficiencyexercise.utils.Constants;


public abstract class AppBaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Set Title of Custome AppBar
     *
     * @param title Title of AppBar
     */
    protected void setAppBar(String title) {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setCustomView(R.layout.custom_actionbar);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        View mCustomView = getSupportActionBar().getCustomView();
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(title);

        ImageButton imgNotifySync = (ImageButton) mCustomView
                .findViewById(R.id.imgNotifySync);
        imgNotifySync.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                doNetworkCall();
            }
        });

        mActionBar.setCustomView(mCustomView, params);
        mActionBar.setDisplayShowCustomEnabled(true);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    /**
     * General method to do network call
     */
    protected void doNetworkCall() {
        if (AppController.conMgr.getActiveNetworkInfo() != null
                && AppController.conMgr.getActiveNetworkInfo().isAvailable()
                && AppController.conMgr.getActiveNetworkInfo().isConnected()) {

            showProgress(getResources().getString(R.string.show_progress));

            AppPojo appPojoObj = new AppPojo.AppPojoBuilder()
                    .setUrl(Constants.URL)
                    .build();
            AppController.mInstance.getAppPresenterObject((AppInterfaces.ViewInterface) AppBaseActivity.this).doModelAction("network", appPojoObj);
        } else {
            showAlert(getResources().getString(R.string.internet_connectivity_check));
        }
    }

    /**
     * Showing progress with message
     *
     * @param msg message String
     */
    protected void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();
        mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name), msg);
    }

    /**
     * Dismiss Progress Dialog
     */
    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * Showing Alert Dialog with message
     *
     * @param msg Message String
     */
    protected void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }
}