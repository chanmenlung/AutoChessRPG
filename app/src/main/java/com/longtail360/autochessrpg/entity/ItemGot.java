package com.longtail360.autochessrpg.entity;

import java.util.ArrayList;
import java.util.List;

public class ItemGot {
    public long id;
    public long adventureId;
    public long itemId;
    public int onBody;//1=onBody, -1=notOnBody
    public Item item;

    public static List<ItemGot> listByOnBody(long adventureId) {
        List<ItemGot> result = new ArrayList<>();
        List<ItemGot> items = GameContext.gameContext.itemGotDAO.listByAdventureId(adventureId);
        for(ItemGot item : items){
            if(item.onBody == 1){
                item.item = GameContext.gameContext.itemDAO.get(item.itemId);
                result.add(item);
            }
        }
        return result;
    }

    public static List<ItemGot> listByNotOnBody(long adventureId) {
        List<ItemGot> result = new ArrayList<>();
        List<ItemGot> items = GameContext.gameContext.itemGotDAO.listByAdventureId(adventureId);
        for(ItemGot item : items){
            if(item.onBody == -1){
                item.item = GameContext.gameContext.itemDAO.get(item.itemId);
                result.add(item);
            }
        }
        return result;
    }
}
