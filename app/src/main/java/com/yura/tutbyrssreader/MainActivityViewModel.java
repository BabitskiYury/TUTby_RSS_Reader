package com.yura.tutbyrssreader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.room.NewsRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final Application application = getApplication();

    private final NewsRepository repository = new NewsRepository(application);

    public final LiveData<List<NewsData>> news = repository.getAllNews();

    public SingleLiveEvent<ViewCommand> viewCommands = new SingleLiveEvent<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    static class ViewCommand {
        static final class ShowText extends ViewCommand {
            public String message;

            public ShowText(String message) {
                this.message = message;
            }
        }
    }

    public void loadData() {
        if (NetworkManager.isNetworkAvailable(application)) {
            Background background = new Background();
            background.execute(() -> {
                SharedPreferences sPref = application.getSharedPreferences("Settings", Context.MODE_PRIVATE);

                String baseUrl = sPref.getString(application.getString(R.string.sprefs_base_url_string), "https://news.tut.by/");
                String link = sPref.getString(application.getString(R.string.sprefs_link_string), "rss/index.rss");

                repository.loadDataFromNetwork(baseUrl, link);
            });
        } else
            viewCommands.setValue(new ViewCommand.ShowText("No internet connection."));

    }

    public void checkDates(){
        SharedPreferences sPref = application.getSharedPreferences("Settings", Context.MODE_PRIVATE);

        int days = sPref.getInt(application.getString(R.string.sprefs_days_to_delete_string), 7);
        repository.checkDates(days);
    }

    public void setItemState(NewsData item, NewsState state) {
        item.state = state;
        repository.update(item);
    }
}
