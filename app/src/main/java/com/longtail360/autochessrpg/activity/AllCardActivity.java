package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.prefab.CardDescItem;
import com.longtail360.autochessrpg.prefab.CardDetail;
import com.longtail360.autochessrpg.prefab.HeadBackButton;
import com.longtail360.autochessrpg.prefab.TestLayout;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.List;

public class AllCardActivity extends ExternalResActivity implements CardDescItem.CallBack{
    private String tag = "AllCardActivity";
    private ViewGroup cardListLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_card);
        thisLayout = findViewById(R.id.thisLayout);

        HeadBackButton allCardBackBt = findViewById(R.id.backBt);
        allCardBackBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        allCardBackBt.imageView.setVisibility(View.VISIBLE);
        allCardBackBt.imageView.setImageResource(R.drawable.item_crystal);
        allCardBackBt.setText(GameContext.gameContext.getPlayer(this).crystal+"");

        cardListLayout = findViewById(R.id.cardListLayout);
        List<Card> cards = GameContext.gameContext.cardDAO.getAll();
        Logger.log(tag,"cards-size:"+cards.size());
        for(Card card : cards){
            CardDescItem item  = new CardDescItem(this, this, card);
            cardListLayout.addView(item);
        }


    }



    @Override
    public void doActionAfterClickDetail(CardDescItem focusItem, Card card){
        if(cardDetail == null){
            cardDetail = new CardDetail(this, card);
            thisLayout.addView(cardDetail);
        }
        cardDetail.cardDescItem = focusItem;
        cardDetail.loadCard(card);
        cardDetail.showDetail(true, true);
    }

}
