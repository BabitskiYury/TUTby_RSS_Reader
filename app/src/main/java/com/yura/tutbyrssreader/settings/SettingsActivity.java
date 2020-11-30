package com.yura.tutbyrssreader.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yura.tutbyrssreader.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.settings_toolbar_text));

        initRadioGroup();
    }

    private void initRadioGroup(){
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        sPref = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        RadioButton week = findViewById(R.id.week);
        RadioButton month = findViewById(R.id.month);
        RadioButton three_month = findViewById(R.id.three_month);

        int prefs = sPref.getInt(getString(R.string.sprefs_days_to_delete_btn_string), 0);
        if(prefs == 0) week.setChecked(true);
        if(prefs == 1) month.setChecked(true);
        if(prefs == 2) three_month.setChecked(true);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.week) setDaysToDelete(0,7);
            if(checkedId == R.id.month) setDaysToDelete(1,30);
            if(checkedId == R.id.three_month) setDaysToDelete(2,90);
        });
    }

    private void setDaysToDelete(int prefsMode, int days) {
        SharedPreferences.Editor ed = sPref.edit();

        ed.putInt(getString(R.string.sprefs_days_to_delete_btn_string), prefsMode);
        ed.putInt(getString(R.string.sprefs_days_to_delete_string), days);
        ed.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}