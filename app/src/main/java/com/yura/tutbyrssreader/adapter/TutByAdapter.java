package com.yura.tutbyrssreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yura.tutbyrssreader.NewsState;
import com.yura.tutbyrssreader.R;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.listeners.PopupSelectItemListener;
import com.yura.tutbyrssreader.listeners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TutByAdapter extends RecyclerView.Adapter<TutByViewHolder> implements Filterable {

    private final ArrayList<NewsData> items = new ArrayList<>();
    private final ArrayList<NewsData> allItems = new ArrayList<>();
    private final RecyclerItemClickListener recyclerItemClickListener;
    private final PopupSelectItemListener popupSelectItemListener;

    public TutByAdapter(RecyclerItemClickListener recyclerItemClickListener,
                        PopupSelectItemListener popupSelectItemListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.popupSelectItemListener = popupSelectItemListener;
    }

    @NonNull
    @Override
    public TutByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TutByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutByViewHolder holder, int position) {
        holder.bind(items.get(position), recyclerItemClickListener, popupSelectItemListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(Collection<NewsData> newsData) {
        items.clear();
        allItems.clear();
        newsData.forEach(item -> {
            if (item.state != NewsState.DONE)
                items.add(item);
        });
        allItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<NewsData> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()){
                filteredList.addAll(allItems);
            } else {
                for (NewsData item: allItems){
                    if(item.title.toLowerCase().contains(constraint.toString().toLowerCase()))
                        filteredList.add(item);
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((Collection<? extends NewsData>) results.values);
            notifyDataSetChanged();
        }
    };
}

