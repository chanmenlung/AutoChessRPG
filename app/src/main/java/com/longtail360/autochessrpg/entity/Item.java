package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class Item {
    public static String RED_POTION_SMALL = "item_red_potion_small";
    public static String RED_POTION_MIDDLE = "item_red_potion_middle";
    public static String RED_POTION_LARGE = "item_red_potion_large";
    public long id;
    public String itemCode;
    public String name;

    public Item() {}
    public Item(String itemCode, String name){
        this.itemCode = itemCode;
        this.name = name;
    }
    public static void init(Context context) {
        Item item;
        item = new Item(RED_POTION_SMALL, context.getString(R.string.item_redPotionSmall));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(RED_POTION_MIDDLE, context.getString(R.string.item_redPotionMiddle));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(RED_POTION_LARGE, context.getString(R.string.item_redPotionLarge));
        GameContext.gameContext.itemDAO.insert(item);
    }
}
