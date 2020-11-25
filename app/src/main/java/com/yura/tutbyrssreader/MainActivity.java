package com.yura.tutbyrssreader;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yura.tutbyrssreader.Api.ApiController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiController apiController = new ApiController();
        apiController.loadIndexRss();
    }
}