package com.yura.tutbyrssreader.adapter;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
        items.addAll(newsData);
        notifyDataSetChanged();
    }
}

class TutByViewHolder extends RecyclerView.ViewHolder {

    private final ConstraintLayout itemLayout;
    private final TextView title;
    private final ImageButton actionMenu;

    public TutByViewHolder(@NonNull View itemView) {
        super(itemView);
        itemLayout = itemView.findViewById(R.id.item_constraintLayout);
        title = itemView.findViewById(R.id.itemTitle_textView);
        actionMenu = itemView.findViewById(R.id.actionMenu);
    }

    public void bind(NewsData item, RecyclerItemClickListener recyclerItemClickListener,
                     PopupSelectItemListener popupSelectItemListener) {
        title.setText(item.getTitle());
        itemLayout.setOnClickListener(v -> recyclerItemClickListener.onRecyclerItemClick(item));

        actionMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(new ContextThemeWrapper(v.getContext(),R.style.popupMenu),v);
            popupMenu.inflate(R.menu.item_popup_menu);

            popupMenu.setOnMenuItemClickListener(action -> {
                popupSelectItemListener.onPopupItemSelect((String)action.getTitle(),item);
                return true;
            });
            popupMenu.show();
        });
    }
}
