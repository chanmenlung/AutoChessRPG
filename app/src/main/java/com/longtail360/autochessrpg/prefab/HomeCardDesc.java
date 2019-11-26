package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.BaseActivity;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;

public class HomeCardDesc extends FrameLayout  {
    private View cardDescLayout;
    private TextView cardDescLevel;
    private TextView cardDescValue;
    private TextView cardDescSkill;
    private TextView cardDescPrice;
    private View cardDescCancelBt;
    private View cardDescSellBt;
    private Card card;
    private ViewGroup parentLayout;
    public HomeCardDesc(Context context) {
        super(context);

    }

    public HomeCardDesc(final Context context, final CallBack callBack) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.home_card_desc, this);
        cardDescLayout = findViewById(R.id.cardDescLayout);
        cardDescLevel = findViewById(R.id.cardDescLevel);
        cardDescValue = findViewById(R.id.cardDescValue);
        cardDescSkill = findViewById(R.id.cardDescSkill);
        cardDescPrice = findViewById(R.id.cardInfoDescPrice);
        cardDescCancelBt = findViewById(R.id.cardDescCancelBt);
        cardDescSellBt = findViewById(R.id.cardDescSellBt);

        cardDescCancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeCardDesc.this.setVisibility(View.GONE);
            }
        });
        cardDescSellBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentLayout.removeAllViews();
                GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin + card.price;
                GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
                HomeCardDesc.this.setVisibility(View.GONE);
                callBack.doActionAfterSellCard(parentLayout,card);
            }
        });
    }

    public void reloadCard(Context context, ViewGroup parentLayout, Card card) {
        this.card = card;
        this.parentLayout = parentLayout;
        cardDescLevel.setText(context.getString(R.string.ui_home_cardDesc_level)+card.level);
        cardDescValue.setText(context.getString(R.string.ui_home_cardDesc_cardInfo)
                .replace("{hp}", card.hp+"")
                .replace("{attack}", card.attack+"")
                .replace("{defense}", card.defense+"")
        );
        cardDescSkill.setText(context.getString(R.string.ui_home_cardDesc_skill)+card.skill.desc);
        cardDescPrice.setText(context.getString(R.string.ui_home_cardDesc_sellCard)+card.price);
    }


    public interface CallBack {
        void doActionAfterSellCard(ViewGroup parentLayout, Card card);
    }
}
