package com.yura.tutbyrssreader;

import android.os.Handler;

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
        Handler handler = new Handler();

        Runnable runnable = () -> {
            ApiController apiController = new ApiController();
            List<NewsData> data = apiController.loadIndexRss();
            handler.post(() -> {
                news.setValue(data);
            });
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
