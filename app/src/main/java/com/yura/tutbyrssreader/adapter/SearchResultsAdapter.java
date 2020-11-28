package com.yura.tutbyrssreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yura.tutbyrssreader.R;
import com.yura.tutbyrssreader.listeners.SearchListItemButtonClickListener;

import java.util.ArrayList;
import java.util.Collection;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsViewHolder> {

    private final ArrayList<String> items = new ArrayList<>();
    private final SearchListItemButtonClickListener searchListItemButtonClickListener;

    public SearchResultsAdapter(SearchListItemButtonClickListener searchListItemButtonClickListener) {
        this.searchListItemButtonClickListener = searchListItemButtonClickListener;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_list_item, parent, false);
        return new SearchResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position) {
        holder.bind(items.get(position), searchListItemButtonClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(Collection<String> links) {
        items.clear();
        items.addAll(links);
        notifyDataSetChanged();
    }
}
