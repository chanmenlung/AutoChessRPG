package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.utils.Logger;

/**
 * Created by chanmenlung on 11/10/2018.
 */

public class TacticsDescItem extends FrameLayout {
    private String tag = "TacticsDescItem";
    public TextView content;
    public TextView strategyIndex;
    public Tactics strategy;
    public CheckBox activeTactBt;
    public TacticsDescItem(final Context context, final Tactics strategy, int index) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.tactics_desc_item, this);
        content = ((TextView)findViewById(R.id.strategyFullDesc));
        strategyIndex = ((TextView)findViewById(R.id.strategyIndex));
        activeTactBt = findViewById(R.id.activeTactBt);
        strategyIndex.setText("No."+(index+1));
        this.strategy = strategy;
        Logger.log(tag, "strategy.active:"+strategy.active);
        if(strategy.active == 1){
            activeTactBt.setChecked(true);
        }
        activeTactBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(activeTactBt.isChecked()){
                    strategy.active = 1;
                    Logger.toast(context.getString(R.string.tactic_hadActive), context);
                }else {
                    Logger.toast(context.getString(R.string.tactic_hadInactive), context);
                    strategy.active = 0;
                }
                GameContext.gameContext.savePlayerData(getContext());
            }
        });
//        activeTactBt.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                if(activeTactBt.isChecked()){
//                    strategy.active = 1;
//                    Logger.toast(context.getString(R.string.tactic_hadActive), context);
//                }else {
//                    Logger.toast(context.getString(R.string.tactic_hadInactive), context);
//                    strategy.active = 0;
//                }
//                GameContext.gameContext.savePlayerData(getContext());
//                Logger.log(tag, "aaaaaaaaaa");
//            }
//        });


    }

    public void setText(String text) {
        content.setText(text);
        if(strategy.active == 1){
            activeTactBt.setChecked(true);
        }
    }


}
