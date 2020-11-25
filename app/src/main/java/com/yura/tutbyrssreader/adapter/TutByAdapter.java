package com.yura.tutbyrssreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yura.tutbyrssreader.R;

public class TutByAdapter extends RecyclerView.Adapter<TutByViewHolder> {
    @NonNull
    @Override
    public TutByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TutByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutByViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class TutByViewHolder extends RecyclerView.ViewHolder {

    public TutByViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
