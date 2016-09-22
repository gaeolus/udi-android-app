package com.smithsocial.udisampleapp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smithsocial.udisampleapp.R;
import com.smithsocial.udisampleapp.presenters.DetailsPresenter;
import com.smithsocial.udisampleapp.presenters.DetailsPresenterImpl;

public class DeviceDetailsActivity extends AppCompatActivity implements DetailsView.UpdateUI {
    private ProgressBar progressBar;
    private TextView brandNameTextView;
    private TextView deviceIdTextView;
    private TextView sterilizationBoolTextView;
    private TextView expirationBoolTextView;
    private CheckBox deviceSavedCheckBox;
    private DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.details_progress_bar);
        brandNameTextView = (TextView) findViewById(R.id.details_brand_name);
        deviceIdTextView = (TextView) findViewById(R.id.details_device_id);
        sterilizationBoolTextView = (TextView) findViewById(R.id.details_sterilize_prior);
        expirationBoolTextView = (TextView) findViewById(R.id.details_expiration_bool);
        deviceSavedCheckBox = (CheckBox) findViewById(R.id.details_save_check_box);

        String deviceId = getIntent().getExtras().getString("device_id");
        //placeholder device name
        detailsPresenter = new DetailsPresenterImpl(this, "device name", deviceId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        detailsPresenter.onResume();
        detailsPresenter.reactToSave(deviceSavedCheckBox);
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
    public void setDeviceName(String name) {
        brandNameTextView.setText(name);
    }

    @Override
    public void setDeviceId(String deviceId) {
        deviceIdTextView.setText(deviceId);
    }

    @Override
    public void setDeviceExpirationBool(Boolean hasExpiration) {
        expirationBoolTextView.setText(hasExpiration.toString());
    }

    @Override
    public void setSterilizePriorToUse(Boolean hasSterilize) {
        sterilizationBoolTextView.setText(hasSterilize.toString());
    }

    @Override
    public void setDeviceIsSaved(Boolean deviceIsSaved){
        deviceSavedCheckBox.setChecked(deviceIsSaved);
    }

}
