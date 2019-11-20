package com.longtail360.autochessrpg.entity;

import java.util.List;

public class CardInHand {
    public long id;
    public long adventureId;
    public long cardId;
    public int location;

    public Card card;

    public static List<CardInHand> listAll() {
        List<CardInHand> result = GameContext.gameContext.cardInHandDAO.listAll();
        for(CardInHand card : result) {
            card.card = GameContext.gameContext.cardDAO.get(card.cardId);
        }
        return result;
    }
}
