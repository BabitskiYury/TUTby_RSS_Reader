package com.yura.tutbyrssreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yura.tutbyrssreader.R;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.listeners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;

public class TutByAdapter extends RecyclerView.Adapter<TutByViewHolder> {

    private final ArrayList<NewsData> items = new ArrayList<>();
    private RecyclerItemClickListener listener;

    public TutByAdapter(RecyclerItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TutByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TutByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutByViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(Collection<NewsData> newsData) {
        items.clear();
        items.addAll(newsData);
        notifyDataSetChanged();
    }
}

class TutByViewHolder extends RecyclerView.ViewHolder {
    private final ConstraintLayout itemLayout;
    private final ImageView image;
    private final TextView title;
    private final TextView date;

    public TutByViewHolder(@NonNull View itemView) {
        super(itemView);
        itemLayout = itemView.findViewById(R.id.item_constraintLayout);
        image = itemView.findViewById(R.id.itemImage_imageView);
        title = itemView.findViewById(R.id.itemTitle_textView);
        date = itemView.findViewById(R.id.itemDate_textView);
    }

    public void bind(NewsData item, RecyclerItemClickListener listener) {
        Picasso.with(image.getContext()).load(item.getImgUrl()).into(image);
        title.setText(item.getTitle());
        date.setText(item.getPubDate());
        itemLayout.setOnClickListener(v -> listener.onRecyclerItemClick(item));
    }
}
