package com.longtail360.autochessrpg.entity;

import java.util.List;

public class MyCard {
    public static int TYPE_IN_HAND = 1;
    public static int TYPE_IN_TEAM = 2;
    public static int TYPE_FOR_BUY = 3;
    public long id;
    public long adventureId;
    public long cardId;
    public int level =1;
    public int battleHp;
    public int type; //1=inHand, 2=inTeam
    public int location; //0-11
    public int locationX;//0-7
    public int locationY;//0-3
    public Card card;
    public static List<MyCard> listByAdvIdAndType(long adventureId, int type) {
        List<MyCard> result = GameContext.gameContext.myCardDAO.listByAdvIdAndType(adventureId, type);
        for(MyCard card : result) {
            card.card = Card.get(card.cardId);
        }
        return result;
    }

    public int getTotalHp() {
        if(level == 2){
            return card.hp * 2;
        }
        if(level == 3){
            return card.hp  * 4;
        }
        return card.hp;
    }

    public int getAttack() {
        if(level == 2){
            return card.attack * 2;
        }
        if(level == 3){
            return card.attack  * 4;
        }
        return card.attack;
    }

    public int getSellingPrice() {
        return card.price;
    }

    public String concateNameHpMpLevelExp() {
        StringBuilder result = new StringBuilder();
        result.append(card.name)
                .append(" HP:").append(battleHp).append("/").append(getTotalHp());
        return result.toString();
    }

}
