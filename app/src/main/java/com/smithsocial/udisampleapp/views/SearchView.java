package com.smithsocial.udisampleapp.views;

import java.util.List;
import java.util.Map;

class SearchView {
    interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setDevices(Map<String, String> devices, boolean flag);
        void noDevice();
        void goToDetails(String deviceId, String deviceName);
        void listSearchFields(List<String> fields);
        String getSelectedSearchField();
        void loadNextResults();
    }
}
