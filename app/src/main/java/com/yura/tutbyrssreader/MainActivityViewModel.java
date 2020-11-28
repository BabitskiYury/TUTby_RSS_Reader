package com.yura.tutbyrssreader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yura.tutbyrssreader.api.ApiController;
import com.yura.tutbyrssreader.data.NewsData;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<NewsData>> news;

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

    public LiveData<List<NewsData>> getNews() {
        if (news == null) {
            news = new MutableLiveData<>();
            loadData();
        }
        return news;
    }

    public void loadData() {
        Background background = new Background();
        background.execute(() -> {
            Application application = getApplication();

            SharedPreferences sPref = application.getSharedPreferences("Settings", Context.MODE_PRIVATE);

            String baseUrl = sPref.getString(application.getString(R.string.sprefs_base_url_string), "https://news.tut.by/");
            String link = sPref.getString(application.getString(R.string.sprefs_link_string), "rss/index.rss");

            ApiController apiController = new ApiController(baseUrl);
            List<NewsData> data = apiController.loadIndexRss(link);

            if (data.isEmpty())
                background.postOnUiThread(() -> viewCommands.setValue(new ViewCommand.ShowText("Connection failed.")));
            else
                background.postOnUiThread(() -> news.setValue(data));
        });
    }
}
