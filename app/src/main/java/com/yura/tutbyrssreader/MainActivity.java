package com.yura.tutbyrssreader;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.yura.tutbyrssreader.settings.SettingsActivity;
import com.yura.tutbyrssreader.web_view.WebViewActivity;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel model;

    private TutByAdapter recyclerViewAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Boolean datesChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00070F")));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::loadNews);

        initRecyclerView();
        initFab();

        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainActivityViewModel.class);
        initViewCommand();
        model.news.observe(this, this::setItems);
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
        fab.setOnClickListener(view -> startActivityForResult(new Intent(
                this,
                AutodiscoveryActivity.class),
                111
        ));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111 && resultCode == RESULT_OK)
            model.loadData();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerItemClickListener listener = item -> {
            if(NetworkManager.isNetworkAvailable(this)){
                startActivity(new Intent(
                        this,
                        WebViewActivity.class)
                        .setData(Uri.parse(item.getLink())));
                model.setItemState(item, NewsState.READING);
            }
            else
                Toast.makeText(this,"No internet connection.",Toast.LENGTH_SHORT).show();
        };

        PopupSelectItemListener popupSelectItemListener = (selectedAction, item) -> {
            if (selectedAction == getString(R.string.item_popup_info))
                showInfoPopup(item);
            else
                readPopup(item);
        };

        recyclerViewAdapter = new TutByAdapter(listener, popupSelectItemListener);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        getMenuInflater().inflate(R.menu.settings_menu, menu);

        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenu.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return true;
    }

    private void loadNews() {
        model.loadData();
    }

    private void setItems(Collection news) {
        recyclerViewAdapter.setItems(news);
        swipeRefreshLayout.setRefreshing(false);
        if(!datesChecked){
            model.checkDates();
            datesChecked = true;
        }
    }

    private void showInfoPopup(NewsData item) {
        InfoDialog dialog = new InfoDialog().newInstance(item);
        dialog.show(getFragmentManager(), "popup");
    }

    private void readPopup(NewsData item) {
        model.setItemState(item, NewsState.DONE);
    }
}