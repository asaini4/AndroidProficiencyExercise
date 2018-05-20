package com.wipro.androidproficiencyexercise.interfaces;

import com.wipro.androidproficiencyexercise.pojo.WSResponse;

public interface  AppInterfaces {

    interface Action {
        void doAction();
    }

    interface ModelInterface {
        void updateNetworkfModelResponseSuccess(WSResponse modelRes);
        void updateNetworkfModelResponseError(String modelRes);
    }

    interface ViewInterface {
        void updateNetworkResponseSuccess(WSResponse netRes);
        void updateNetworkResponseError(String netRes);
    }
}
