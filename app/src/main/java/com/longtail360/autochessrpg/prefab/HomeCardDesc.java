package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.BaseActivity;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

public class HomeCardDesc extends FrameLayout  {
    private View cardDescLayout;
    private TextView cardDescLevel;
    private TextView cardDescValue;
    private TextView cardDescSkill;
    private TextView cardDescPrice;
    private View cardDescCancelBt;
    private View cardDescSellBt;
    private ImageView cardDescHead;
    private CardIcon icon;
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
        cardDescHead = findViewById(R.id.cardDescHead);
        cardDescCancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeCardDesc.this.setVisibility(View.GONE);
            }
        });
        cardDescSellBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeCardDesc.this.setVisibility(View.GONE);
                callBack.doActionAfterSellCard(icon);
            }
        });
    }

    public void reloadCard(Context context, CardIcon cardIcon) {
        this.icon = cardIcon;
        cardDescLevel.setText(context.getString(R.string.ui_home_cardDesc_level)+1);
        cardDescValue.setText(context.getString(R.string.ui_home_cardDesc_cardInfo)
                .replace("{hp}", cardIcon.myCard.battleHp+"/"+cardIcon.myCard.getTotalHp())
                .replace("{attack}", cardIcon.myCard.getAttack()+"")
                .replace("{defense}", cardIcon.myCard.card.defense+"")
        );
        cardDescSkill.setText(context.getString(R.string.ui_home_cardDesc_skill)+cardIcon.myCard.card.skill.desc);
        cardDescPrice.setText(context.getString(R.string.ui_home_cardDesc_sellCard)+cardIcon.myCard.getSellingPrice());
        cardIcon.myCard.card.setHeadToImageView(context, cardDescHead);
    }


    public interface CallBack {
        void doActionAfterSellCard(CardIcon cardIcon);
    }
}
