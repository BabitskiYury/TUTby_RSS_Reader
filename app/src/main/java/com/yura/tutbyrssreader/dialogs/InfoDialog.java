package com.yura.tutbyrssreader.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.yura.tutbyrssreader.R;
import com.yura.tutbyrssreader.data.NewsData;

public class InfoDialog extends DialogFragment {

    NewsData item;

    Dialog dialog;

    public InfoDialog newInstance(NewsData item) {
        Bundle args = new Bundle();
        args.putSerializable("item", item);

        InfoDialog dialog = new InfoDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (NewsData) getArguments().getSerializable("item");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        dialog = new Dialog(getActivity());
        dialog.setContentView(inflater.inflate(R.layout.info_dialog, null));
        dialog.show();

        TextView title = dialog.findViewById(R.id.popupTitleTextView);
        TextView date = dialog.findViewById(R.id.popupDateTextView);
        TextView description = dialog.findViewById(R.id.popupDescriptionTextView);

        title.setText(item.getTitle());
        date.setText(item.getPubDate());
        description.setText(item.getDescription());

        return dialog;
    }
}
