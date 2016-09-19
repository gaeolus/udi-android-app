package com.smithsocial.udisampleapp.presenters;

import android.view.View;

import com.smithsocial.udisampleapp.views.SearchActivity;

public class SearchPresenterImpl extends SearchPresenter {
    private SearchActivity searchActivity;
    // private LoadDeviceFromApi loadDeviceFromApi;

    public SearchPresenterImpl(SearchActivity searchActivity){
        this.searchActivity = searchActivity;
    }

    @Override
    public void onResume() {
        if (searchActivity != null){
            // get the device ID from search Activity
        }
    }

    @Override
    public void onDestroy() {
        searchActivity = null;
    }

    @Override
    public void reactToSearch(String searchString) {
        // react to the search. it'll emit a string every time a character is entered
    }

    @Override
    public void reactToDeviceClick(View view) {
        // react to clicking on the device that's returned by the search
    }
}
