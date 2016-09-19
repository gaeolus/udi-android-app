package com.smithsocial.udisampleapp.presenters;

import android.view.View;

public abstract class SearchPresenter {

    public abstract void onResume();
    public abstract void onDestroy();
    public abstract void reactToSearch(String searchString);
    public abstract void reactToDeviceClick(View view);

}
