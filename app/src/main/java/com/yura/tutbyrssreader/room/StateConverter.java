package com.yura.tutbyrssreader.room;

import androidx.room.TypeConverter;

import com.yura.tutbyrssreader.NewsState;

public class StateConverter {

    @TypeConverter
    public int fromState(NewsState state) {
        if (state == NewsState.READING)
            return 1;
        else if(state == NewsState.DONE)
            return 2;
        else
            return 0;
    }

    @TypeConverter
    public NewsState toState(int state) {
        if(state == 1)
            return NewsState.READING;
        else if(state == 2)
            return NewsState.DONE;
        else
            return NewsState.DEFAULT;
    }

}
