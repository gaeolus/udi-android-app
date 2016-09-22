package com.smithsocial.udisampleapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smithsocial.udisampleapp.R;
import com.smithsocial.udisampleapp.presenters.HomePresenter;
import com.smithsocial.udisampleapp.presenters.HomePresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView.UpdateUI {
    private ListView listView;
    private ProgressBar progressBar;
    private TextView noDevicesTextView;
    private FloatingActionButton fab;
    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.device_list);
        progressBar = (ProgressBar) findViewById(R.id.home_progress_bar);
        noDevicesTextView = (TextView) findViewById(R.id.home_no_devices_text_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        homePresenter = new HomePresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        homePresenter.onResume();
        homePresenter.reactToFab(fab);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        listView.setVisibility(View.INVISIBLE);
        noDevicesTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setList(HashMap<String, String> items) {
        listView.setVisibility(View.VISIBLE);
        // placeholder listview textview layout
        List<String> list = new ArrayList<>();
        for (String s: items.values()) {
            list.add(s);
        }
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.simple_list_item_layout, list));
        homePresenter.reactToList(listView);
    }

    @Override
    public void noDevices(){
        noDevicesTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToSearch() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @Override
    public void goToDetails(String deviceId) {
        Intent intent = new Intent(this, DeviceDetailsActivity.class);
        intent.putExtra("device_id", deviceId);
        startActivity(intent);
    }
}
