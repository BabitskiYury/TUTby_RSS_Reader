package com.yura.tutbyrssreader;

import android.os.Handler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Background {

    private final ExecutorService service = new ScheduledThreadPoolExecutor(5);

    private final Handler uiHandler = new Handler();

    public void postOnUiThread(final Runnable runnable) {
        uiHandler.post(runnable);
    }

    public Future<?> execute(Runnable runnable) {
        return service.submit(runnable);
    }
    public <T> Future<T> submit(Callable<T> runnable) {
        return service.submit(runnable);
    }
}
