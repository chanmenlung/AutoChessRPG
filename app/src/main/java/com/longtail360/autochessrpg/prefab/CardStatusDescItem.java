package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

public class CardStatusDescItem  extends LinearLayout {
    private String tag = "ItemLogDesc";
    public ImageView cardIcon;
    public TextView title;
    public TextView desc;

    public CardStatusDescItem(Context context, Card card, String content) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_status_desc_item, this);
        cardIcon = (ImageView)findViewById(R.id.cardIcon);
        title = (TextView)findViewById(R.id.title);
        desc = (TextView)findViewById(R.id.desc);
        desc.setText(content);
        CustomCard customCard = GameContext.gameContext.customCardDAO.getByCode(card.code);
        title.setText(customCard.customName);
        card.setHeadToImageView(context, cardIcon);
    }

}
