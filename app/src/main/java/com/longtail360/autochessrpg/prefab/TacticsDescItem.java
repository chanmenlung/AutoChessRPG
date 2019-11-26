package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.tactic.Tactics;

/**
 * Created by chanmenlung on 11/10/2018.
 */

public class TacticsDescItem extends FrameLayout {
    public TextView content;
    public TextView strategyIndex;
    public Tactics strategy;
    public TacticsDescItem(Context context, Tactics strategy, int index) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.tactics_desc_item, this);
        content = ((TextView)findViewById(R.id.strategyFullDesc));
        strategyIndex = ((TextView)findViewById(R.id.strategyIndex));
        strategyIndex.setText("No."+(index+1));
        this.strategy = strategy;
    }

    public void setText(String text) {
        content.setText(text);
    }
}
