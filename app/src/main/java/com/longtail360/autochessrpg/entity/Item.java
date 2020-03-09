package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.tactic.action.*;

public class Item {
    public static String ITEM_HP_UP = "item_red_potion_small";
    public static String ITEM_CD_DOWN = "item_blue_potion_small";
    public static String ITEM_ATTACK_UP = "item_riceball";
    public static String ITEM_FIRE = "item_dynamite";
    public static String ITEM_ELECTRICITY = "item_jewelstone";
    public static String ITEM_POTION = "item_potion_cube";
    public static String ITEM_ICE = "item_ice";
    public static String ITEM_SUMMON_MONSTER_HEAL = "item_fairy";
    public static String ITEM_SUMMON_MONSTER_LARGE = "item_scale";
    public static String ITEM_SUMMON_MONSTER_MIDDLE = "item_horn";
    public static String ITEM_SUMMON_MONSTER_SMALL = "item_jeinirune";

    public static String ITEM_PERFUME = "item_aphrodisiac";

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
        item = new Item(ITEM_HP_UP,ITEM_HP_UP, context.getString(R.string.item_itemHpUp), context.getString(R.string.item_itemHpUp_desc).replace("{value}", UseItemHpUp.deltaHp+""));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_CD_DOWN,ITEM_CD_DOWN, context.getString(R.string.item_itemCdDown), context.getString(R.string.item_itemCdDown_desc).replace("{value}", UseItemCdDown.deltaCd+""));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_ATTACK_UP,ITEM_ATTACK_UP, context.getString(R.string.item_itemAttackUp), context.getString(R.string.item_itemAttackUp_desc).replace("{value}", UseItemAttackUp.deltaAttack+""));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_FIRE, ITEM_FIRE,context.getString(R.string.item_itemFire), context.getString(R.string.item_itemFire_desc).replace("{value}", UseItemFire.hurt+""));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_ELECTRICITY, ITEM_ELECTRICITY,context.getString(R.string.item_itemElectricity), context.getString(R.string.item_itemElectricity_desc).replace("{value}", UseItemElectricity.hurt+""));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_POTION, ITEM_POTION,context.getString(R.string.item_itemPotion), context.getString(R.string.item_itemPotion_desc).replace("{value}", UseItemPotion.hurt+""));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_ICE,ITEM_ICE, context.getString(R.string.item_itemIce), context.getString(R.string.item_itemIce_desc).replace("{value}", UseItemIce.hurt+""));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_SUMMON_MONSTER_HEAL, ITEM_SUMMON_MONSTER_HEAL,context.getString(R.string.item_itemSummonMonsterHeal), context.getString(R.string.item_itemSummonMonsterHeal_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_SUMMON_MONSTER_LARGE, ITEM_SUMMON_MONSTER_LARGE,context.getString(R.string.item_itemSummonMonsterLarge), context.getString(R.string.item_itemSummonMonsterLarge_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_SUMMON_MONSTER_MIDDLE, ITEM_SUMMON_MONSTER_MIDDLE,context.getString(R.string.item_itemSummonMonsterMiddle), context.getString(R.string.item_itemSummonMonsterMiddle_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_SUMMON_MONSTER_SMALL, ITEM_SUMMON_MONSTER_SMALL,context.getString(R.string.item_itemSummonMonsterSmall), context.getString(R.string.item_itemSummonMonsterSmall_desc));
        GameContext.gameContext.itemDAO.insert(item);
        item = new Item(ITEM_PERFUME, ITEM_PERFUME,context.getString(R.string.item_itemPerfume), context.getString(R.string.item_itemPerfume_desc));
        GameContext.gameContext.itemDAO.insert(item);
    }
}
