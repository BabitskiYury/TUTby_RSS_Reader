package com.yura.tutbyrssreader.autodiscovery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yura.tutbyrssreader.Background;
import com.yura.tutbyrssreader.MainActivityViewModel;
import com.yura.tutbyrssreader.NetworkManager;
import com.yura.tutbyrssreader.SingleLiveEvent;
import com.yura.tutbyrssreader.api.ApiController;
import com.yura.tutbyrssreader.data.NewsData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AutodiscoveryActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> items;

    private final String regex = "href=\\S*xml\"|href=\\S*rss\"";

    public SingleLiveEvent<AutodiscoveryActivityViewModel.ViewCommand> viewCommands = new SingleLiveEvent<>();

    public AutodiscoveryActivityViewModel(@NonNull Application application) {
        super(application);
    }

    static class ViewCommand {
        static final class NoResults extends AutodiscoveryActivityViewModel.ViewCommand {
        }

        static final class CheckRss extends AutodiscoveryActivityViewModel.ViewCommand {
            public Boolean result;

            public CheckRss(Boolean result) {
                this.result = result;
            }
        }

        static final class ShowText extends AutodiscoveryActivityViewModel.ViewCommand {
            public String message;

            public ShowText(String message) {
                this.message = message;
            }
        }
    }

    public LiveData<List<String>> getLinks() {
        if (items == null) {
            items = new MutableLiveData<>();
        }
        return items;
    }

    public void loadData(String url) {
        Background background = new Background();
        background.execute(() -> {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = okHttpClient.newCall(request).execute();
                String data = response.body().string();

                ArrayList<String> links = new ArrayList<>();

                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher(data);
                while (matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();
                    links.add(data.substring(start + 6, end - 1));
                }
                if (links.isEmpty())
                    background.postOnUiThread(() -> viewCommands.setValue(new ViewCommand.NoResults()));
                else
                    background.postOnUiThread(() -> items.setValue(links));
            } catch (Exception e) {
                background.postOnUiThread(() -> viewCommands.setValue(new ViewCommand.NoResults()));
            }
        });
    }

    public void checkRssChannel(String url) {
        if(NetworkManager.isNetworkAvailable(getApplication())){{
            Background background = new Background();
            background.execute(() -> {
                try {
                    int index = url.indexOf("/", 8) + 1;
                    String baseUrl = url.substring(0, index);
                    String link = url.substring(index);

                    ApiController apiController = new ApiController(baseUrl);
                    List<NewsData> data = apiController.loadIndexRss(link);

                    if (data.isEmpty())
                        background.postOnUiThread(() -> viewCommands.setValue(new AutodiscoveryActivityViewModel.ViewCommand.CheckRss(false)));
                    else
                        background.postOnUiThread(() -> viewCommands.setValue(new AutodiscoveryActivityViewModel.ViewCommand.CheckRss(true)));
                } catch (Exception e) {
                    background.postOnUiThread(() -> viewCommands.setValue(new AutodiscoveryActivityViewModel.ViewCommand.CheckRss(false)));
                }
            });
        }}
        else
            viewCommands.setValue(new AutodiscoveryActivityViewModel.ViewCommand.ShowText("No internet connection."));
    }
}
