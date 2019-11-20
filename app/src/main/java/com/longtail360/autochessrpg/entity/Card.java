package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;
import com.longtail360.autochessrpg.entity.skill.ElectricAllBigHurt;
import com.longtail360.autochessrpg.entity.skill.ElectricAllHurt;
import com.longtail360.autochessrpg.entity.skill.ElectricSingleBigHurt;
import com.longtail360.autochessrpg.entity.skill.ElectricSingleHurt;
import com.longtail360.autochessrpg.entity.skill.FireAllBigHurt;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private static String tag = "card_tag";
    public static String RACE_HUMAN = "human";
    public static String RACE_ELF = "elf";
    public static String RACE_DRAGON = "dragon";
    public static String RACE_UNDEAD = "undead";
    public static String RACE_ORCS = "orcs";
    public static String RACE_GNOME = "gnome";
    public static String RACE_DIVINITY = "divinity";
    public static String RACE_MARINE = "marine";
    public static String RACE_DWARF = "dwarf";
    public static String RACE_GOO = "goo";

    public static String CLAZZ_MAGE = "mage";
    public static String CLAZZ_WARRIOR = "warrior";
    public static String CLAZZ_PRIEST = "priest";
    public static String CLAZZ_HUNTER = "hunter";
    public static String CLAZZ_KNIGHT = "knight";
    public static String CLAZZ_SHAMAN = "shaman";
    public static String CLAZZ_ROGUE = "rogue";
    public static String CLAZZ_WARLOCK = "warlock";
    public long id;
    public String code;
    public int locked;
    public String race;
    public String clazz;
    public String skillCode;
    public String name;
    public String customName;
    public String head;
    public String customHead;
    public String image;
    public String customImage;
    public String customSkillName;
    public String customSkillBattleDesc;
    public int numChessForUpgrd;
    public int hp;
    public int attack;
    public int defense;
    public int price;
    public int rarity; //0=basic, 1=normal, 2=rare, 3=epic, 4=legendary
    public BaseSkill skill;
    public Card(){}
    public Card(String code, int locked, String race, String clazz, String skillCode, String name, String customName, String head, String customHead,
                String image, String customImage, String customSkillName, String customSkillBattleDesc, int numChessForUpgrd, int level_1_hp,
                 int level_1_attack, int level_1_defense, int price, int rare) {
        this.id = id;
        this.code = code;
        this.locked = locked;
        this.race = race;
        this.clazz = clazz;
        this.skillCode = skillCode;
        this.name = name;
        this.customName = customName;
        this.head = head;
        this.customHead = customHead;
        this.image = image;
        this.customImage = customImage;
        this.customSkillName = customSkillName;
        this.customSkillBattleDesc = customSkillBattleDesc;
        this.numChessForUpgrd =numChessForUpgrd;
        this.hp = level_1_hp;
        this.attack = level_1_attack;
        this.defense = level_1_defense;
        this.price = price;
        this.rarity = rare;
    }

    public static List<Card> listAll() {
        List<Card> result = GameContext.gameContext.cardDAO.getAll();
        for(Card card : result){
            BaseSkill skill = GameContext.gameContext.skillDAO.getByKey(card.skillCode);
            card.skill = skill;
        }
        return result;
    }

    public static List<Card> getAllByRare(int rare) {
        List<Card> result =GameContext.gameContext.cardDAO.getAllByRare(rare);
        for(Card card : result){
            BaseSkill skill = GameContext.gameContext.skillDAO.getByKey(card.skillCode);
            card.skill = skill;
        }
        return result;
    }

    public static Card get(Long id){
        Card card = GameContext.gameContext.cardDAO.get(id);
        BaseSkill skill = GameContext.gameContext.skillDAO.getByKey(card.skillCode);
        if(skill == null){
            Logger.log(tag, "skill is null, skillCode:"+card.skillCode);
        }
        card.skill = skill;
        return card;
    }

    public static List<String> listRacesCn(Context context) {
        List<String> result = new ArrayList<>();
        result.add(context.getString(R.string.card_race_human));
        result.add(context.getString(R.string.card_race_elf));
        result.add(context.getString(R.string.card_race_dragon));
        result.add(context.getString(R.string.card_race_undead));
        result.add(context.getString(R.string.card_race_orcs));
        result.add(context.getString(R.string.card_race_gnome));
        result.add(context.getString(R.string.card_race_divinity));
        result.add(context.getString(R.string.card_race_marine));
        result.add(context.getString(R.string.card_race_dwarf));
        result.add(context.getString(R.string.card_race_goo));
        return result;
    }

    public static List<String> listRaces(Context context) {
        List<String> result = new ArrayList<>();
        result.add(RACE_HUMAN);
        result.add(RACE_ELF);
        result.add(RACE_DRAGON);
        result.add(RACE_UNDEAD);
        result.add(RACE_ORCS);
        result.add(RACE_GNOME);
        result.add(RACE_DIVINITY);
        result.add(RACE_MARINE);
        result.add(RACE_DWARF);
        result.add(RACE_GOO);
        return result;
    }

    public static List<String> listClazzCn(Context context) {
        List<String> result = new ArrayList<>();
        result.add(context.getString(R.string.card_clazz_mage));
        result.add(context.getString(R.string.card_clazz_warrior));
        result.add(context.getString(R.string.card_clazz_priest));
        result.add(context.getString(R.string.card_clazz_hunter));
        result.add(context.getString(R.string.card_clazz_knight));
        result.add(context.getString(R.string.card_clazz_shaman));
        result.add(context.getString(R.string.card_clazz_rogue));
        result.add(context.getString(R.string.card_clazz_warlock));
        return result;
    }

    public static List<String> listClazz() {
        List<String> result = new ArrayList<>();
        result.add(CLAZZ_MAGE);
        result.add(CLAZZ_WARRIOR);
        result.add(CLAZZ_PRIEST);
        result.add(CLAZZ_HUNTER);
        result.add(CLAZZ_KNIGHT);
        result.add(CLAZZ_SHAMAN);
        result.add(CLAZZ_ROGUE);
        result.add(CLAZZ_WARLOCK);
        return result;
    }

    public static void init(Context context) {
        Card chess;
        Player player = GameContext.gameContext.getPlayer(context);
        chess = new Card("c1",player.unlockCards.contains("c1")?0:1, Card.RACE_DIVINITY, Card.CLAZZ_HUNTER, ElectricAllBigHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[0], null,
                "c1_head", null, "c1", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c2",player.unlockCards.contains("c2")?0:1, Card.RACE_DRAGON, Card.CLAZZ_KNIGHT, ElectricAllHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[1], null,
                "c2_head", null, "c2", null, null,null,
                1, 700, 10,5,30,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c3",player.unlockCards.contains("c3")?0:1, Card.RACE_DWARF, Card.CLAZZ_MAGE, ElectricSingleBigHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[2], null,
                "c3_head", null, "c3", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c4",player.unlockCards.contains("c4")?0:1, Card.RACE_ELF, Card.CLAZZ_PRIEST, ElectricSingleHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[3], null,
                "c4_head", null, "c4", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c5",player.unlockCards.contains("c5")?0:1, Card.RACE_ELF, Card.CLAZZ_ROGUE, FireAllBigHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[4], null,
                "c5_head", null, "c5", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c6",player.unlockCards.contains("c6")?0:1, Card.RACE_ELF, Card.CLAZZ_HUNTER, FireAllBigHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[5], null,
                "c6_head", null, "c6", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c7",player.unlockCards.contains("c7")?0:1, Card.RACE_ELF, Card.CLAZZ_SHAMAN, FireAllBigHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[6], null,
                "c7_head", null, "c7", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c8",player.unlockCards.contains("c8")?0:1, Card.RACE_ELF, Card.CLAZZ_WARLOCK, FireAllBigHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[7], null,
                "c8_head", null, "c8", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c9",player.unlockCards.contains("c9")?0:1, Card.RACE_ELF, Card.CLAZZ_WARRIOR, FireAllBigHurt.KEY, context.getResources().getStringArray(R.array.cardNames)[8], null,
                "c9_head", null, "c9", null, null,null,
                1, 700, 10,5, 20, 1);
        GameContext.gameContext.cardDAO.insert(chess);
    }
}
