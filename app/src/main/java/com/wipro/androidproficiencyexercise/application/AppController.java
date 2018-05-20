package com.wipro.androidproficiencyexercise.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.wipro.androidproficiencyexercise.interfaces.AppInterfaces;
import com.wipro.androidproficiencyexercise.presenter.AppPresenter;
import com.wipro.androidproficiencyexercise.presenter.AppPresenterFactory;


public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    public static AppController mInstance;
    public static ConnectivityManager conMgr;
    private volatile AppPresenterFactory appPresenterFactoryObject;

    @Override
    public void onCreate() {
        super.onCreate();

        getInstanceUsingDoubleLocking(); //Create singleton object for AppPresenterFactory

        if (mInstance == null) {
            mInstance = this;
        }

        //ConnectivityManager Object....
        if (conMgr == null) {
            conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    private void getInstanceUsingDoubleLocking() {
        if (appPresenterFactoryObject == null) {
            synchronized (AppController.class) {
                if (appPresenterFactoryObject == null) {
                    appPresenterFactoryObject = new AppPresenterFactory();
                }
            }
        }
    }

    private RequestQueue getRequestQueue(HttpStack hurlStack) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mInstance, hurlStack);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag, HttpStack hurlStack) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 15, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue(hurlStack).add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public AppPresenter getAppPresenterObject(AppInterfaces.ViewInterface activityContext) {
        if (activityContext != null) {
            return new AppPresenter(activityContext, appPresenterFactoryObject);
        }
        return null;
    }
}
