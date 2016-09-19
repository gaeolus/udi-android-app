package com.smithsocial.udisampleapp.presenters;

import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

import com.smithsocial.udisampleapp.views.HomeActivity;

public class HomePresenterImpl extends HomePresenter {
    private HomeActivity homeActivity;
    //private LocalDevices localDevices;

    public HomePresenterImpl(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }

    @Override
    public void onResume() {
        if (homeActivity != null){
            // show progress bar
        }
        // get devices
        // when devices come back, load them into homeActivity's listview
    }

    @Override
    public void onDestroy() {
        homeActivity = null;
    }

    @Override
    public void reactToList(ListView listView) {
        // react to clicks from the listview here
    }

    @Override
    public void reactToFab(FloatingActionButton fab) {
        // react to clicks of the FAB here
    }
}
