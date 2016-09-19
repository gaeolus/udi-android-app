package com.smithsocial.udisampleapp.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.smithsocial.udisampleapp.R;
import com.smithsocial.udisampleapp.presenters.DetailsPresenter;
import com.smithsocial.udisampleapp.presenters.DetailsPresenterImpl;

public class DeviceDetailsActivity extends AppCompatActivity implements DetailsView.UpdateUI {
    private ProgressBar progressBar;
    private DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.details_progress_bar);
        detailsPresenter = new DetailsPresenterImpl(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        detailsPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailsPresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDeviceDetails() {
        // show the device
    }
}
