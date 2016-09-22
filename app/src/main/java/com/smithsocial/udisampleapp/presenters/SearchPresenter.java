package com.smithsocial.udisampleapp.presenters;

import android.view.View;
import android.widget.EditText;

public abstract class SearchPresenter {

    public abstract void onResume();
    public abstract void onDestroy();
    public abstract void reactToSearch(EditText editText);
    public abstract void reactToDeviceClick(View view);

}
