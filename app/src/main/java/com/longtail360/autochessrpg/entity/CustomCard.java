package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.activity.MainActivity;

import java.io.Serializable;
import java.util.List;

public class CustomCard implements Serializable {
    public long id;
    @Expose
    public int locked;
    @Expose
    public String code;
    @Expose
    public String customName ="";
    @Expose
    public String customHead = "null";
    @Expose
    public String customImage = "null";
    @Expose
    public String customSkillName = "";
    @Expose
    public String customSkillBattleDesc="";
    public int showCardBackground = 1;

    public static void init(Context context) {
        List<Card> cards = Card.listAll(context);
        for(Card card : cards) {
            CustomCard customCard = new CustomCard();
            customCard.locked = 1;
            customCard.code = card.code;
            customCard.customName = card.name;
            GameContext.gameContext.customCardDAO.insert(customCard);
        }
    }
}
