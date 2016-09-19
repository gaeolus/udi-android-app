package com.smithsocial.udisampleapp.views;

public class SearchView {
    public interface UpdateUI{
        void showProgress();
        void hideProgress();
        void setDevice();
        void noDevice();
    }
}
