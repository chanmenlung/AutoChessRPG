package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class Item {
    public static String ITEM_HP_UP = "item_red_potion_small";
    public static String ITEM_CD_DOWN = "item_red_potion_middle";
    public static String ITEM_ATTACK_UP = "item_red_potion_large";
    public static String ITEM_FIRE = "item_fire";
    public static String ITEM_ELECTRICITY = "item_electricity";
    public static String ITEM_POTION = "item_potion";
    public static String ITEM_ICE = "item_ice";
    public static String ITEM_SUMMON_MONSTER_SMALL = "item_summon_monster_s";
    public static String ITEM_SUMMON_MONSTER_LARGE = "item_smmon_monster_l";
    public static String ITEM_PERFUME = "item_perfume";

    public long id;
    public String itemCode;
    public String imageName;
    public String name;
    public String desc;

    public Item() {}
    public Item(String itemCode, String imageName, String name, String desc){
        this.itemCode = itemCode;
        this.name = name;
        this.imageName = imageName;
        this.desc = desc;
    }
    public static void init(Context context) {
        Item item;
        item = new Item(ITEM_HP_UP,"item_red_potion_small", context.getString(R.string.item_itemHpUp), context.getString(R.string.item_itemHpUp_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_CD_DOWN,"item_blue_potion_small", context.getString(R.string.item_itemCdDown), context.getString(R.string.item_itemCdDown_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_ATTACK_UP,"item_riceball", context.getString(R.string.item_itemAttackUp), context.getString(R.string.item_itemAttackUp_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_FIRE, "item_dynamite",context.getString(R.string.item_itemFire), context.getString(R.string.item_itemFire_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_ELECTRICITY, "item_jewelstone",context.getString(R.string.item_itemElectricity), context.getString(R.string.item_itemElectricity_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_POTION, "item_potion_cube",context.getString(R.string.item_itemPotion), context.getString(R.string.item_itemPotion_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_ICE,"item_ice", context.getString(R.string.item_itemIce), context.getString(R.string.item_itemIce_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_SUMMON_MONSTER_SMALL, "item_fairy",context.getString(R.string.item_itemSummonMonsterSmall), context.getString(R.string.item_itemSummonMonsterSmall_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_SUMMON_MONSTER_LARGE, "item_scale",context.getString(R.string.item_itemSummonMonsterLarge), context.getString(R.string.item_itemSummonMonsterLarge_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_PERFUME, "item_aphrodisiac",context.getString(R.string.item_itemPerfume), context.getString(R.string.item_itemPerfume_desc));
        GameContext.gameContext.itemDAO.insert(item);
    }
}
