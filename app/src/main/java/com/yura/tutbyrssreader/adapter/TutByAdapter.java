package com.yura.tutbyrssreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yura.tutbyrssreader.NewsState;
import com.yura.tutbyrssreader.R;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.listeners.PopupSelectItemListener;
import com.yura.tutbyrssreader.listeners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;

public class TutByAdapter extends RecyclerView.Adapter<TutByViewHolder> {

    private final ArrayList<NewsData> items = new ArrayList<>();
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
        newsData.forEach(item -> {
            if (item.state != NewsState.DONE)
                items.add(item);
        });
        notifyDataSetChanged();
    }
}

