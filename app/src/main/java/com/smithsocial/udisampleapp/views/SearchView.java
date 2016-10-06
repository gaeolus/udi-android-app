package com.smithsocial.udisampleapp.views;

import java.util.List;

class SearchView {
    interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setDevice(String deviceId, String deviceName);
        void noDevice();
        void goToDetails(String deviceId, String deviceName);
        void listSearchFields(List<String> fields);
        String getSelectedSearchField();
    }
}
