package com.smithsocial.udisampleapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smithsocial.udisampleapp.R;
import com.smithsocial.udisampleapp.presenters.SearchPresenter;
import com.smithsocial.udisampleapp.presenters.SearchPresenterImpl;

public class SearchActivity extends AppCompatActivity implements SearchView.UpdateUI {
    private ProgressBar progressBar;
    private TextView noDeviceFound;
    private EditText editText;
    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.search_view_progress_bar);
        noDeviceFound = (TextView) findViewById(R.id.search_no_device_text_view);
        editText = (EditText) findViewById(R.id.search_view_edit_text);
        searchPresenter = new SearchPresenterImpl(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchPresenter.onResume();
        searchPresenter.reactToSearch(editText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.onDestroy();
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

    @Override
    public void goToDetails(String deviceId, String deviceName) {
        Intent intent = new Intent(this, DeviceDetailsActivity.class);
        intent.putExtra("device_id", deviceId);
        intent.putExtra("device_name", deviceName);
        startActivity(intent);
    }
}
