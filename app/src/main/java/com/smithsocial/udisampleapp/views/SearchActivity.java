package com.smithsocial.udisampleapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.smithsocial.udisampleapp.R;
import com.smithsocial.udisampleapp.presenters.SearchPresenter;
import com.smithsocial.udisampleapp.presenters.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements SearchView.UpdateUI {
    private ProgressBar progressBar;
    private TextView noDeviceFound;
    private EditText editText;
    private Spinner searchPropertySpinner;
    private ListView listView;
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
        listView = (ListView) findViewById(R.id.search_device_list_view);
        searchPropertySpinner = (Spinner) findViewById(R.id.search_property_spinner);
        searchPresenter = new SearchPresenterImpl(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchPresenter.onResume();
        searchPresenter.reactToSearch(editText);
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        noDeviceFound.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDevices(final Map<String, String> devices, boolean searchChangedFlag) {
        if (searchChangedFlag && listView.getAdapter() != null){
            HashMapAdapter adapter = (HashMapAdapter) listView.getAdapter();
            adapter.clear();
        }
        List<Map.Entry<String, String >> deviceList = new ArrayList<>(devices.entrySet());
        listView.setVisibility(View.VISIBLE);
        if (listView.getAdapter() == null){
            // placeholder listview textview layout
            listView.setAdapter(new HashMapAdapter(this, R.layout.search_device_item, R.id.list_view_device_id, deviceList));
        } else {
            HashMapAdapter adapter = (HashMapAdapter) listView.getAdapter();
            adapter.addAll(deviceList);
            adapter.notifyDataSetChanged();
        }
        searchPresenter.reactToList(listView);

        // add an item to the end of the list?
        // when that item becomes visible, call to the search presenter to load the next ten items

    }

    @Override
    public void noDevice() {
        noDeviceFound.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        if (listView.getAdapter() != null){
            HashMapAdapter adapter = (HashMapAdapter) listView.getAdapter();
            adapter.clear();
        }
    }

    @Override
    public void goToDetails(String deviceId, String deviceName) {
        Intent intent = new Intent(this, DeviceDetailsActivity.class);
        intent.putExtra("device_id", deviceId);
        intent.putExtra("device_name", deviceName);
        startActivity(intent);
    }

    @Override
    public void listSearchFields(List<String> fields) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.single_spinner_item, fields);
        searchPropertySpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public String getSelectedSearchField(){
        return searchPropertySpinner.getSelectedItem().toString();
    }
}
