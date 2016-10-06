package com.smithsocial.udisampleapp.presenters;

import android.widget.EditText;
import android.widget.ListView;

public abstract class SearchPresenter {

    public abstract void onResume();
    public abstract void onDestroy();
    public abstract void reactToSearch(EditText editText);
    public abstract void reactToList(ListView listView);

}
