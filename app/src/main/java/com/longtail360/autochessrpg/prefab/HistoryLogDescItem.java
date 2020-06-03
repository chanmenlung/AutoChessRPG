package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;

public class HistoryLogDescItem  extends LinearLayout {
    private String tag = "HistoryLogDescItem";
    private TextView btText;

    public HistoryLogDescItem(Context context, String text) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.history_log_desc_item, this);
        btText = (TextView) findViewById(R.id.btText);
        btText.setText(text);
    }
}
