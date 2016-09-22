package com.smithsocial.udisampleapp.views;

class SearchView {
    interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setDevice(String deviceId, String deviceName);
        void noDevice();
        void goToDetails(String deviceId, String deviceName);
    }
}
