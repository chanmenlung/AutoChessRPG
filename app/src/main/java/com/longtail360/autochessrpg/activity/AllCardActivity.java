package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.prefab.CardDescItem;
import com.longtail360.autochessrpg.prefab.CardDetail;
import com.longtail360.autochessrpg.prefab.HeadBackButton;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class AllCardActivity extends ExternalResActivity implements CardDescItem.CallBack{
    private String tag = "AllCardActivity";
    private ViewGroup cardListLayout;
    private HeadBackButton allCardBackBt;
    private Spinner raceSpinner;
    private Spinner clazzSpinner;
    private List<CardDescItem> cardDescItems = new ArrayList<>();
    private List<String> raceNames;
    private List<String> raceNamesCn;
    private List<String> clazzNames;
    private List<String> clazzNamesCn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_card);
        thisLayout = findViewById(R.id.thisLayout);

        allCardBackBt = findViewById(R.id.backBt);
        allCardBackBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        View buyCrystalBt = findViewById(R.id.buyCrystalBt);
        buyCrystalBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOtherActivity(BuyCrystalActivity.class);
            }
        });
        allCardBackBt.imageView.setVisibility(View.VISIBLE);
        allCardBackBt.imageView.setImageResource(R.drawable.item_crystal);
        allCardBackBt.setText(GameContext.gameContext.getPlayer(this).crystal+"");

        cardListLayout = findViewById(R.id.cardListLayout);
        raceNames = Card.listRaces(this);
        raceNamesCn = Card.listRacesCn(this);
        clazzNames = Card.listClazz();
        clazzNamesCn = Card.listClazzCn(this);
        clazzNames.add(0,"all");
        raceNames.add(0,"all");
        raceNamesCn.add(0, getString(R.string.allCard_race));
        clazzNamesCn.add(0, getString(R.string.allCard_clazz));
        List<Card> cards = Card.listAll();
        Logger.log(tag,"cards-size:"+cards.size());
        for(Card card : cards){
            CardDescItem item  = new CardDescItem(this, this, card);
            cardListLayout.addView(item);
            cardDescItems.add(item);
        }
        raceSpinner = findViewById(R.id.raceSpinner);
        clazzSpinner = findViewById(R.id.clazzSpinner);
        initRaceSpinner();
        initClazzSpinner();
        initPopupBox();
        popupBox.reset(2);

    }

    private void initRaceSpinner() {
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(this,
                R.layout.filter_card_spinner_item_left, raceNamesCn);
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(raceAdapter);

        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(CardDescItem item : cardDescItems) {
                    item.setVisibility(View.VISIBLE);
                }
                if(i == 0){//select all
                    return;
                }
                String selectRace = raceNames.get(i);
                for(CardDescItem item : cardDescItems) {
                    if(!item.card.race.equals(selectRace)){
                        item.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initClazzSpinner() {
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(this,
                R.layout.filter_card_spinner_item_right, clazzNamesCn);
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clazzSpinner.setAdapter(raceAdapter);

        clazzSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(CardDescItem item : cardDescItems) {
                    item.setVisibility(View.VISIBLE);
                }
                if(i == 0){//select all
                    return;
                }
                String selectRace = clazzNames.get(i);
                for(CardDescItem item : cardDescItems) {
                    if(!item.card.clazz.equals(selectRace)){
                        item.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void doActionAfterClickDetail(CardDescItem focusItem, Card card){
        if(cardDetail == null){
            cardDetail = new CardDetail(this, card.id);
            thisLayout.addView(cardDetail);
        }
        cardDetail.cardDescItem = focusItem;
        cardDetail.loadCard(card.id);
        cardDetail.showDetail(true, true);
    }

    @Override
    public void doActionAfterUnlockCard(CardDescItem focusItem, Card card) {
        allCardBackBt.setText(GameContext.gameContext.getPlayer(this).crystal+"");
    }

}
