package com.smithsocial.udisampleapp.views;

class SearchView {
    interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setDevice();
        void noDevice();
        void goToDetails(String deviceId);
    }
}
