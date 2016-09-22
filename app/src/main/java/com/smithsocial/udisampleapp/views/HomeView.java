package com.smithsocial.udisampleapp.views;

import java.util.HashMap;

class HomeView {
    interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setList(HashMap<String, String> items);
        void noDevices();
        void goToSearch();
        void goToDetails(String deviceId);
    }
}
