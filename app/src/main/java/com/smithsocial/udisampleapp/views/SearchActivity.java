package com.smithsocial.udisampleapp.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smithsocial.udisampleapp.R;

public class SearchActivity extends AppCompatActivity implements SearchView.UpdateUI {
    private ProgressBar progressBar;
    private TextView noDeviceFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.search_view_progress_bar);
        noDeviceFound = (TextView) findViewById(R.id.search_no_device_text_view);

    }

    @Override
    public void showProgress() {
        noDeviceFound.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDevice() {
        // set the device here
    }

    @Override
    public void noDevice() {
        noDeviceFound.setVisibility(View.VISIBLE);
    }
}
