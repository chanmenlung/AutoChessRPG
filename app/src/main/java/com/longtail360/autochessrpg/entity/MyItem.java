package com.longtail360.autochessrpg.entity;

import java.util.ArrayList;
import java.util.List;

public class MyItem {
    public long id;
    public long adventureId;
    public long itemId;
    public int onBody;//1=onBody, -1=notOnBody
    public Item item;

    public static List<MyItem> listByOnBody(long adventureId) {
        List<MyItem> result = new ArrayList<>();
        List<MyItem> items = GameContext.gameContext.itemGotDAO.listByAdventureId(adventureId);
        for(MyItem item : items){
            if(item.onBody == 1){
                item.item = GameContext.gameContext.itemDAO.get(item.itemId);
                result.add(item);
            }
        }
        return result;
    }

    public static List<MyItem> listByNotOnBody(long adventureId) {
        List<MyItem> result = new ArrayList<>();
        List<MyItem> items = GameContext.gameContext.itemGotDAO.listByAdventureId(adventureId);
        for(MyItem item : items){
            if(item.onBody == -1){
                item.item = GameContext.gameContext.itemDAO.get(item.itemId);
                result.add(item);
            }
        }
        return result;
    }
}
