package com.yura.tutbyrssreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yura.tutbyrssreader.adapter.TutByAdapter;
import com.yura.tutbyrssreader.autodiscovery.AutodiscoveryActivity;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.dialogs.InfoDialog;
import com.yura.tutbyrssreader.listeners.PopupSelectItemListener;
import com.yura.tutbyrssreader.listeners.RecyclerItemClickListener;
import com.yura.tutbyrssreader.web_view.WebViewActivity;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel model;

    private TutByAdapter recyclerViewAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::loadNews);

        initRecyclerView();
        initFab();

        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainActivityViewModel.class);
        initViewCommand();
        model.getNews().observe(this, this::setItems);
    }

    private void initViewCommand() {
        model.viewCommands.observe(this, viewCommand -> {
            if (viewCommand.getClass() == MainActivityViewModel.ViewCommand.ShowText.class) {
                Toast.makeText(getBaseContext(), ((MainActivityViewModel.ViewCommand.ShowText) viewCommand).message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(
                this,
                AutodiscoveryActivity.class)
        ));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerItemClickListener listener = item -> startActivity(new Intent(
                this,
                WebViewActivity.class)
                .setData(Uri.parse(item.getLink())));

        PopupSelectItemListener popupSelectItemListener = (selectedAction, item) -> {
            if (selectedAction == getString(R.string.item_popup_info))
                showInfoPopup(item);
            else
                readPopup();
        };

        recyclerViewAdapter = new TutByAdapter(listener, popupSelectItemListener);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void loadNews() {
        model.loadData();
    }

    private void setItems(Collection news) {
        recyclerViewAdapter.setItems(news);
        swipeRefreshLayout.setRefreshing(false);

    }

    private void showInfoPopup(NewsData item) {
        InfoDialog dialog = new InfoDialog().newInstance(item);
        dialog.show(getFragmentManager(), "popup");
    }

    private void readPopup() {

    }
}