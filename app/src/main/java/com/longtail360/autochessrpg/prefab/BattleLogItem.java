package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.Logger;

public class BattleLogItem extends FrameLayout {
    private String tag = "BattleLogItem";
    private TextView content;
    private TextView title;
    private ImageView icon1;
    private ImageView icon2;
    private ImageView featherView;
    private View line;
    public BattleLogItem(Context context, BattleItemLog bLog) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.battle_log_item, this);
        line = findViewById(R.id.line);
        title = ((TextView)findViewById(R.id.title));
        content = ((TextView)findViewById(R.id.content));
        title.setText(bLog.title);
        icon1 = (ImageView)findViewById(R.id.icon1);
        Logger.log(tag,"icon1aaa:"+bLog.icon1);
        Logger.log(tag,"bLog.getIcon1Type():"+bLog.icon1Type);
        if(bLog.icon1Type== RootLog.ICON1_TYPE_CARD){
//            Card card = BaseActivity.player.findCardFromMyCardsByUuid(bLog.icon1);
            Card card = GameContext.gameContext.cardDAO.get(Long.parseLong(bLog.icon1));
            card.setHeadToImageView(context, icon1);
        }else {
            int resourceId = getResources().getIdentifier(bLog.icon1, "drawable",
                    context.getPackageName());
            icon1.setImageResource(resourceId);
        }

        if(bLog.icon2 != null){
            icon2 = (ImageView)findViewById(R.id.icon2);
            int resourceId2 = getResources().getIdentifier(bLog.icon2, "drawable",
                    context.getPackageName());
            icon2.setImageResource(resourceId2);
            icon2.setVisibility(VISIBLE);
        }
        if(bLog.content != null){
            line.setVisibility(VISIBLE);
            content.setText(bLog.content);
            content.setVisibility(VISIBLE);
        }

        if(bLog.color == BattleItemLog.RED){ //red
            title.setTextColor(Color.parseColor("#FFD81B60"));
            content.setTextColor(Color.parseColor("#FFD81B60"));
        }else if(bLog.color  == BattleItemLog.GREEN){ //green
            title.setTextColor(Color.parseColor("#FF008577"));
            content.setTextColor(Color.parseColor("#FFD81B60"));
        }else if(bLog.color  == BattleItemLog.BLUE){ //blue
            title.setTextColor(Color.parseColor("#FF0027f7"));
            content.setTextColor(Color.parseColor("#FFD81B60"));
        }
    }
}
