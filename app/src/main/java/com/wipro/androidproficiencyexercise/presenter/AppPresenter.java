package com.wipro.androidproficiencyexercise.presenter;

import com.wipro.androidproficiencyexercise.interfaces.AppInterfaces;
import com.wipro.androidproficiencyexercise.pojo.AppPojo;
import com.wipro.androidproficiencyexercise.pojo.WSResponse;

public class AppPresenter implements AppInterfaces.ModelInterface {

    private AppInterfaces.ViewInterface view;

    private AppPresenterFactory appPresFactory;

    public AppPresenter (AppInterfaces.ViewInterface view, AppPresenterFactory appPresFactory) {
        this.view = view;
        this.appPresFactory = appPresFactory;
    }

    public void doModelAction (String modelType, AppPojo appPojoObj) {
        AppInterfaces.Action action = appPresFactory.getModel(modelType, this, appPojoObj);
        action.doAction();
    }

    @Override
    public void updateNetworkfModelResponseSuccess(WSResponse modelRes) {
        view.updateNetworkResponseSuccess(modelRes);
    }

    @Override
    public void updateNetworkfModelResponseError(String modelRes) {
        view.updateNetworkResponseError(modelRes);
    }
}
