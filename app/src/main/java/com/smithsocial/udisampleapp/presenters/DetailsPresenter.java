package com.smithsocial.udisampleapp.presenters;

import android.widget.Button;

public abstract class DetailsPresenter {

    public abstract void onResume();
    public abstract void onDestroy();
    public abstract void reactToSave(Button saveButton);

}
