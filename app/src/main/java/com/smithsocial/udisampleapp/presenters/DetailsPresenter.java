package com.smithsocial.udisampleapp.presenters;

import android.widget.CheckBox;

public abstract class DetailsPresenter {

    public abstract void onResume();
    public abstract void onDestroy();
    public abstract void reactToSave(CheckBox checkBox);

}
