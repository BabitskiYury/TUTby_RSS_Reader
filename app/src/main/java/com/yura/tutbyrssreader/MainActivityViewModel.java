package com.yura.tutbyrssreader;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yura.tutbyrssreader.Api.ApiController;
import com.yura.tutbyrssreader.data.NewsData;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<NewsData>> news;

    public LiveData<List<NewsData>> getNews() {
        if (news == null) {
            news = new MutableLiveData<>();
            loadUsers();
        }
        return news;
    }

    public void loadUsers() {
        Background background = new Background();
        background.execute(() -> {
            ApiController apiController = new ApiController();
            List<NewsData> data = apiController.loadIndexRss();
            background.postOnUiThread(() -> news.setValue(data));
        });
    }
}
