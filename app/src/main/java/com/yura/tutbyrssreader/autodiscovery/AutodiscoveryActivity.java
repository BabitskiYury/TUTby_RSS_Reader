package com.yura.tutbyrssreader.autodiscovery;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yura.tutbyrssreader.MainActivityViewModel;
import com.yura.tutbyrssreader.R;
import com.yura.tutbyrssreader.adapter.SearchResultsAdapter;
import com.yura.tutbyrssreader.listeners.SearchListItemButtonClickListener;

import java.util.Collection;

public class AutodiscoveryActivity extends AppCompatActivity {

    private AutodiscoveryActivityViewModel model;

    private SearchResultsAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private TextView searchResultTextView;
    private Button acceptButton;
    private EditText linkEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autodiscovery);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00070F")));

        searchResultTextView = findViewById(R.id.searchResultTextVIew);

        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AutodiscoveryActivityViewModel.class);
        model.getLinks().observe(this, this::setItems);

        initViewCommand();
        initRecyclerView();

        linkEditText = findViewById(R.id.linkEditText);

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> loadData(linkEditText.getText().toString()));

        acceptButton = findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(v -> saveLink(linkEditText.getText().toString()));
    }

    private void loadData(String link) {
        model.loadData(link);
        searchResultTextView.setVisibility(View.GONE);
    }

    private void saveLink(String url) {
        linkEditText.setEnabled(false);
        acceptButton.setEnabled(false);
        model.checkRssChannel(url);
    }

    private void setItems(Collection data) {
        recyclerViewAdapter.setItems(data);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void initViewCommand() {
        model.viewCommands.observe(this, viewCommand -> {
            if (viewCommand.getClass() == AutodiscoveryActivityViewModel.ViewCommand.NoResults.class) {
                recyclerView.setVisibility(View.GONE);
                searchResultTextView.setVisibility(View.VISIBLE);
            } else if (viewCommand.getClass() == AutodiscoveryActivityViewModel.ViewCommand.CheckRss.class) {
                if (((AutodiscoveryActivityViewModel.ViewCommand.CheckRss) viewCommand).result) {
                    setNewRss(linkEditText.getText().toString());
                } else {
                    Toast.makeText(this, "Failed to set rss channel.", Toast.LENGTH_SHORT).show();
                    linkEditText.setEnabled(true);
                    acceptButton.setEnabled(true);
                }

            }else if (viewCommand.getClass() == AutodiscoveryActivityViewModel.ViewCommand.ShowText.class) {
                Toast.makeText(getBaseContext(), ((AutodiscoveryActivityViewModel.ViewCommand.ShowText) viewCommand).message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.searchResultRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchListItemButtonClickListener listener = this::setNewRss;

        recyclerViewAdapter = new SearchResultsAdapter(listener);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setNewRss(String item) {
        SharedPreferences sPref = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        int index = item.indexOf("/", 8) + 1;
        String baseUrl = item.substring(0, index);
        String link = item.substring(index);

        ed.putString(getString(R.string.sprefs_base_url_string), baseUrl);
        ed.putString(getString(R.string.sprefs_link_string), link);

        ed.commit();

        Toast.makeText(this, "Link updated", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK);

        finish();
    }
}