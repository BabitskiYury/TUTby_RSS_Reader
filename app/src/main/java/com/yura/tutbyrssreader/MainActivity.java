package com.yura.tutbyrssreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yura.tutbyrssreader.adapter.TutByAdapter;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.listeners.RecyclerItemClickListener;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel model;

    private TutByAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();

        model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        model.getNews().observe(this, news -> recyclerViewAdapter.setItems(news));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerItemClickListener listener = item -> startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(item.getLink())
        ));

        recyclerViewAdapter = new TutByAdapter(listener);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}