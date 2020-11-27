package com.yura.tutbyrssreader;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yura.tutbyrssreader.Api.ApiController;
import com.yura.tutbyrssreader.data.NewsData;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<NewsData>> news;

    public SingleLiveEvent<ViewCommand> viewCommands = new SingleLiveEvent<>();

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
            loadUsers();
        }
        return news;
    }

    public void loadUsers() {
        Log.d("423423432432", "loadU");
        Background background = new Background();
        background.execute(() -> {
            ApiController apiController = new ApiController();
            List<NewsData> data = apiController.loadIndexRss();
            if (data.isEmpty())
                background.postOnUiThread(() -> viewCommands.setValue(new ViewCommand.ShowText("Connection failed.")));
            else
                background.postOnUiThread(() -> news.setValue(data));
        });
    }
}
