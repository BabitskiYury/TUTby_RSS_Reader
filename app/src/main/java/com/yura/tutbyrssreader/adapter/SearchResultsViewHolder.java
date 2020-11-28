package com.yura.tutbyrssreader.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yura.tutbyrssreader.R;
import com.yura.tutbyrssreader.listeners.SearchListItemButtonClickListener;

public class SearchResultsViewHolder extends RecyclerView.ViewHolder {

    private final TextView link;
    private final Button chooseButton;

    public SearchResultsViewHolder(@NonNull View itemView) {
        super(itemView);
        link = itemView.findViewById(R.id.urlTextView);
        chooseButton = itemView.findViewById(R.id.chooseButton);
    }

    public void bind(String item, SearchListItemButtonClickListener searchListItemButtonClickListener) {
        link.setText(item);
        chooseButton.setOnClickListener(v -> searchListItemButtonClickListener.onButtonClick(item));
    }
}

