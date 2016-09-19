package com.smithsocial.udisampleapp.views;

import java.util.List;

public class HomeView {
    public interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setList(List<String> items);
        void noDevices();
    }
}
