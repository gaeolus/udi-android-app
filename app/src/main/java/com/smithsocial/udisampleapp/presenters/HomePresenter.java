package com.smithsocial.udisampleapp.presenters;

import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

public abstract class HomePresenter {

    public abstract void onResume();
    public abstract void onDestroy();
    public abstract void reactToList(ListView listView);
    public abstract void reactToFab(FloatingActionButton fab);

}
