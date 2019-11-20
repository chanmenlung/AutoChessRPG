package com.longtail360.autochessrpg.entity;

import java.util.List;

public class CardForBuying {
    public long id;
    public long adventureId;
    public long cardId;

    public Card card;

    public CardForBuying() {

    }
    public CardForBuying(long adventureId, long cardId){
        this.adventureId = adventureId;
        this.cardId = adventureId;
    }
    public static List<CardForBuying> listAll() {
        List<CardForBuying> result = GameContext.gameContext.cardForBuyingDAO.listAll();
        for(CardForBuying card : result) {
            card.card = GameContext.gameContext.cardDAO.get(card.cardId);
        }
        return result;
    }
}
