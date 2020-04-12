package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.BaseActivity;
import com.longtail360.autochessrpg.activity.BuyCrystalActivity;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;

/**
 * Created by chanmenlung on 23/1/2019.
 */

public class CardDescItem extends LinearLayout {

    private ImageView cardIcon;
    private TextView raceValue;
    private TextView clazzValue;
    private TextView hpValue;
    private TextView attackValue;
    private TextView defenseValue;
    private TextView nameValue;
    private TextView skillValue;
    private View detailBt;
    private View lockIcon;
    public Card card;
    private CustomCard customCard;

    public CardDescItem(final BaseActivity activity, final CallBack callBack, final Card card) {
        super(activity);
        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_desc_item, this);
        cardIcon = findViewById(R.id.cardIcon);
        raceValue = findViewById(R.id.raceValue);
        clazzValue = findViewById(R.id.clazzValue);
        hpValue = findViewById(R.id.hpValue);
        attackValue = findViewById(R.id.attackValue);
        defenseValue = findViewById(R.id.defenseValue);
        nameValue = findViewById(R.id.nameValue);
        skillValue = findViewById(R.id.skillValue);
        lockIcon = findViewById(R.id.lockIcon);
        detailBt = findViewById(R.id.detailBt);
        customCard = GameContext.gameContext.customCardDAO.getByCode(card.code);
        detailBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.doActionAfterClickDetail(CardDescItem.this, card);
            }
        });
        lockIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.popupBox.reset(2);
                activity.popupBox.title.setText(activity.getString(R.string.cardDescItem_isLockCard)
                        .replace("{crystal}", Setting.CRYSTAL_FOR_UNLOCK_CARD+""));
                activity.popupBox.content.setText(activity.getString(R.string.cardDescItem_reasonForUnlockCard)
                        .replace("{currentCrystal}", GameContext.gameContext.player.crystal+""));
                activity.popupBox.show();
                activity.popupBox.rightConfirm.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(GameContext.gameContext.player.crystal > Setting.CRYSTAL_FOR_UNLOCK_CARD ){
                            CustomCard customCard =GameContext.gameContext.customCardDAO.getByCode(card.code);
                            GameContext.gameContext.player.crystal = GameContext.gameContext.player.crystal - Setting.CRYSTAL_FOR_UNLOCK_CARD;
                            customCard.locked = 0;
                            GameContext.gameContext.customCardDAO.update(customCard);
                            activity.popupBox.hide();
                            lockIcon.setVisibility(INVISIBLE);
                            callBack.doActionAfterUnlockCard(CardDescItem.this, card);
                            Logger.toast(activity.getString(R.string.cardDescItem_hadUnlock), activity);
                        }else {
                            activity.popupBox.reset(2);
                            activity.popupBox.title.setText(activity.getString(R.string.cardDetail_notEnoughCrystal));
                            activity.popupBox.content.setText(activity.getString(R.string.cardDetail_doesGoToBuyCrystal));
                            activity.popupBox.show();
                            activity.popupBox.rightConfirm.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    activity.startOtherActivity(BuyCrystalActivity.class);
                                }
                            });
                        }
                    }
                });
            }
        });
        loadCard(activity, card);
    }

    public void loadCard(Context context, Card card) {
        this.card = card;
        this.customCard = GameContext.gameContext.customCardDAO.getByCode(card.code);
        loadHeadImage(context, card, customCard);
        if(card.race.equals(Card.RACE_DIVINITY)){
            raceValue.setText(context.getString(R.string.card_race_divinity));
        }else if(card.race.equals(Card.RACE_SPIRIT)){
            raceValue.setText(context.getString(R.string.card_race_spirit));
        }else if(card.race.equals(Card.RACE_DWARF)){
            raceValue.setText(context.getString(R.string.card_race_dwarf));
        }else if(card.race.equals(Card.RACE_ELF)){
            raceValue.setText(context.getString(R.string.card_race_elf));
        }else if(card.race.equals(Card.RACE_DEMON)){
            raceValue.setText(context.getString(R.string.card_race_demon));
        }else if(card.race.equals(Card.RACE_GOO)){
            raceValue.setText(context.getString(R.string.card_race_goo));
        }else if(card.race.equals(Card.RACE_HUMAN)){
            raceValue.setText(context.getString(R.string.card_race_human));
        }else if(card.race.equals(Card.RACE_MARINE)){
            raceValue.setText(context.getString(R.string.card_race_marine));
        }else if(card.race.equals(Card.RACE_ORCS)){
            raceValue.setText(context.getString(R.string.card_race_orcs));
        }else if(card.race.equals(Card.RACE_UNDEAD)){
            raceValue.setText(context.getString(R.string.card_race_undead));
        }

        if(card.clazz.equals(Card.CLAZZ_HUNTER)){
            clazzValue.setText(context.getString(R.string.card_clazz_hunter));
        }else if(card.clazz.equals(Card.CLAZZ_KNIGHT)){
            clazzValue.setText(context.getString(R.string.card_clazz_knight));
        }else if(card.clazz.equals(Card.CLAZZ_MAGE)){
            clazzValue.setText(context.getString(R.string.card_clazz_mage));
        }else if(card.clazz.equals(Card.CLAZZ_PRIEST)){
            clazzValue.setText(context.getString(R.string.card_clazz_priest));
        }else if(card.clazz.equals(Card.CLAZZ_ROGUE)){
            clazzValue.setText(context.getString(R.string.card_clazz_rogue));
        }else if(card.clazz.equals(Card.CLAZZ_SHAMAN)){
            clazzValue.setText(context.getString(R.string.card_clazz_shaman));
        }else if(card.clazz.equals(Card.CLAZZ_WARLOCK)){
            clazzValue.setText(context.getString(R.string.card_clazz_warlock));
        }else if(card.clazz.equals(Card.CLAZZ_WARRIOR)){
            clazzValue.setText(context.getString(R.string.card_clazz_warrior));
        }

        hpValue.setText(card.hp+"");
        attackValue.setText((card.attack-1)+" - "+(card.attack+1));
        defenseValue.setText(card.defense+"");
        nameValue.setText(card.name+"");
        skillValue.setText(card.skill.getDesc(context));
        if(customCard.locked == 1){
            lockIcon.setVisibility(VISIBLE);
        }else {
            lockIcon.setVisibility(INVISIBLE);
        }
    }
    public void loadHeadImage(Context context,Card card, CustomCard customCard){
        int headResourceId = ImageUtils.convertImageStringToInt(context, card.head);
        if(customCard.customHead != null) {
            File file = new File(customCard.customHead);
            if(file.exists()) {
                cardIcon.setImageURI(Uri.parse(customCard.customHead));
            }else {
                cardIcon.setImageResource(headResourceId);
            }
        }else {
            cardIcon.setImageResource(headResourceId);
        }
    }

    public interface CallBack {
        void doActionAfterClickDetail(CardDescItem focusItem, Card card);
        void doActionAfterUnlockCard(CardDescItem focusItem, Card card);
    }


}
