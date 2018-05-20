package com.wipro.androidproficiencyexercise.presenter;

import com.wipro.androidproficiencyexercise.interfaces.AppInterfaces;
import com.wipro.androidproficiencyexercise.model.NetworkModel;
import com.wipro.androidproficiencyexercise.pojo.AppPojo;

public class AppPresenterFactory {

    public AppInterfaces.Action getModel(String modelType, AppPresenter appPresenter, AppPojo appPojoObj){

        if(modelType == null){
            return null;
        }

        if(modelType.equalsIgnoreCase("network")) {
            return new NetworkModel(appPresenter, appPojoObj);
        }

        return null;
    }
}
