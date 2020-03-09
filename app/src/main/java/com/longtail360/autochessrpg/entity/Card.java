package com.longtail360.autochessrpg.entity;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.skill.*;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Card {
    private static String tag = "Card";
    public static String RACE_HUMAN = "human";//9 done
    public static String RACE_ELF = "elf";//4 done
    public static String RACE_SPIRIT = "spirit"; //4 done
    public static String RACE_UNDEAD = "undead"; //6
    public static String RACE_ORCS = "orcs"; //獸人 5 done
    public static String RACE_DEMON = "demon"; //惡魔 6
    public static String RACE_DIVINITY = "divinity"; //4 done
    public static String RACE_MARINE = "marine"; //6 done
    public static String RACE_DWARF = "dwarf"; //矮人 5 done
    public static String RACE_GOO = "goo"; //蟲族 6  done

    public static String CLAZZ_MAGE = "mage"; //10
    public static String CLAZZ_WARRIOR = "warrior";//10
    public static String CLAZZ_PRIEST = "priest";//5
    public static String CLAZZ_HUNTER = "hunter";//7
    public static String CLAZZ_KNIGHT = "knight";//6
    public static String CLAZZ_SHAMAN = "shaman";//2
    public static String CLAZZ_ROGUE = "rogue";//8
    public static String CLAZZ_WARLOCK = "warlock";//7
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
    //    public int level = 1;
    public int hp; //this is level 1 hp
    public int attack;
    public int defense;
    public int price;
    public int rarity; //1=basic, 2=normal, 3=rare, 4=epic, 5=legendary
    public BaseSkill skill;
    public Card(){}

    public Card(String code, int locked, String race, String clazz, String skillCode, String name, String customName, String head, String customHead,
                String image, String customImage, String customSkillName, String customSkillBattleDesc, int numChessForUpgrd, int level_1_hp,
                int level_1_attack, int level_1_defense, int rare) {
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
        this.rarity = rare;
        this.price = this.rarity * 10;
    }

    public int calHpByLevel(int level) {
        if(level == 2){
            return hp * 2;
        }
        if(level == 3){
            return hp  * 4;
        }
        return hp;
    }

    public int calAttackByLevel(int level) {
        if(level == 2){
            return attack * 2;
        }
        if(level == 3){
            return attack  * 4;
        }
        return attack;
    }

    public static List<Card> listAll(Context context) {
        List<Card> result = GameContext.gameContext.cardDAO.getAll();
        for(Card card : result){
            BaseSkill skill = BaseSkill.getByCode(context, card.skillCode);
            if(skill == null) {
                Logger.log(tag, card.skillCode);
            }
            card.skill = skill;
        }
        return result;
    }

    public static List<Card> getAllByRare(Context context,int rare) {
        List<Card> result =GameContext.gameContext.cardDAO.getAllByRare(rare);
        for(Card card : result){
            BaseSkill skill = BaseSkill.getByCode(context, card.skillCode);
            card.skill = skill;
        }
        return result;
    }

    public static Card get(Context context,Long id){
        Card card = GameContext.gameContext.cardDAO.get(id);
        BaseSkill skill = BaseSkill.getByCode(context, card.skillCode);
        if(skill == null){
            Logger.log(tag, "skill is null, skillCode:"+card.skillCode);
        }
        card.skill = skill;
        return card;
    }


    public void setHeadToImageView(Context context, ImageView imageView){
        int rid = ImageUtils.convertImageStringToInt(context, head);
        if(customHead != null) {
            File file = new File(customHead);
            if(file.exists()) {
                imageView.setImageURI(Uri.parse(customHead));
            }else {
                imageView.setImageResource(rid);
            }
        }else {
            if(rid !=0){
                imageView.setImageResource(rid);
            }
        }
    }

    public static List<String> listRacesCn(Context context) {
        List<String> result = new ArrayList<>();
        result.add(context.getString(R.string.card_race_human));
        result.add(context.getString(R.string.card_race_elf));
        result.add(context.getString(R.string.card_race_spirit));
        result.add(context.getString(R.string.card_race_undead));
        result.add(context.getString(R.string.card_race_orcs));
        result.add(context.getString(R.string.card_race_demon));
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
        result.add(RACE_SPIRIT);
        result.add(RACE_UNDEAD);
        result.add(RACE_ORCS);
        result.add(RACE_DEMON);
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
        List<String> skillNames = new ArrayList<>();
        skillNames.add("");
        skillNames.add(HealSingle.KEY);
        skillNames.add(HealAll.KEY);
        skillNames.add(HitSingleHurt.KEY);
        skillNames.add(FireAllHurt.KEY);
        skillNames.add(PotionAllHurt.KEY);
        skillNames.add(FireSingleHurt.KEY);
        skillNames.add(PotionAllBigHurt.KEY);
        skillNames.add(SummonMonsterHeal.KEY);
        skillNames.add(FireAllBigHurt.KEY);
        skillNames.add(HitAllAndDodgeAttackTeam.KEY);
        skillNames.add(HitAllBigHurt.KEY);
        skillNames.add(HealAllBig.KEY);
        skillNames.add(Connect2Monster.KEY);
        skillNames.add(HitSingleBigHurt.KEY);
        skillNames.add(SummonMonsterLarge.KEY);
        skillNames.add(HitAllHurt.KEY);
        skillNames.add(TauntAndHeal.KEY);
        skillNames.add(TauntAndDefenseUp.KEY);
        skillNames.add(ConnectMyselfAndMonster.KEY);
        skillNames.add(ValueUpAllAttack.KEY);
        skillNames.add(PotionSingleBigHurt.KEY);
        skillNames.add(PotionSingleHurt.KEY);
        skillNames.add(FireSingleBigHurt.KEY);
        skillNames.add(IceSingleBigHurt.KEY);
        skillNames.add(ConnectAllMonster.KEY);
        skillNames.add(ValueDownOneDefense.KEY);
        skillNames.add(ValueUpOneDefense.KEY);
        skillNames.add(HitDoubleTime.KEY);
        skillNames.add(ValueUpAttackDeadNumber.KEY);
        skillNames.add(ValueUpDefenseDeadNumber.KEY);
        skillNames.add(ValueUpAttackLowHp.KEY);
        skillNames.add(ValueUpAttackTurnNumber.KEY);
        skillNames.add(ValueUpDefenseTurnNumber.KEY);
        skillNames.add(ValueUpOneAttack.KEY);
        skillNames.add(ValueDownOneAttack.KEY);
        skillNames.add(SummonMonsterMiddle.KEY);
        skillNames.add(ValueUpAllDefense.KEY);
        skillNames.add(ValueDownAllAttack.KEY);
        skillNames.add(HitIgnoreDefenseSingle.KEY);
        skillNames.add(ValueDownAllDefense.KEY);
        skillNames.add(SummonMonsterSmall.KEY);
        skillNames.add(IceAllBigHurt.KEY);
        skillNames.add(IceAllHurt.KEY);
        skillNames.add(RandomAttackOfMonster.KEY);
        skillNames.add(IceSingleHurt.KEY);
        skillNames.add(ElectricSingleHurt.KEY);
        skillNames.add(ConnectMyselfAndAllMonster.KEY);
        skillNames.add(ZeroHurtOnMyself.KEY);
        skillNames.add(ReflectAttackOnMyself.KEY);
        skillNames.add(TauntAndDodgeAttackUp.KEY);
        skillNames.add(HitIgnoreDefenseSingleBig.KEY);
        skillNames.add(ValueUpDefenseLowHp.KEY);
        skillNames.add(HitPercentage.KEY);
        skillNames.add(HitSingleAndDodgeAttack.KEY);
        skillNames.add(ReflectAttackOnTeam.KEY);
        int numChessForUpgrd = 3;
        Card chess;
        Player player = GameContext.gameContext.getPlayer(context);

        chess = new Card("c1",player.unlockCards.contains("c1")?0:1, Card.RACE_HUMAN, Card.CLAZZ_PRIEST, skillNames.get(1), context.getResources().getStringArray(R.array.cardNames)[0], null,
                "c1_head", null, "c1", null, null,null,
                numChessForUpgrd, 500,10,5,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c2",player.unlockCards.contains("c2")?0:1, Card.RACE_HUMAN, Card.CLAZZ_PRIEST, skillNames.get(2), context.getResources().getStringArray(R.array.cardNames)[1], null,
                "c2_head", null, "c2", null, null,null,
                numChessForUpgrd, 420,10,6,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c3",player.unlockCards.contains("c3")?0:1, Card.RACE_MARINE, Card.CLAZZ_WARRIOR, skillNames.get(3), context.getResources().getStringArray(R.array.cardNames)[2], null,
                "c3_head", null, "c3", null, null,null,
                numChessForUpgrd, 550, 9,5,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c5",player.unlockCards.contains("c5")?0:1, Card.RACE_GOO, Card.CLAZZ_SHAMAN, skillNames.get(5),context.getResources().getStringArray(R.array.cardNames)[3], null,
                "c5_head", null, "c5", null, null,null,
                numChessForUpgrd,520,12,4,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c6",player.unlockCards.contains("c6")?0:1, Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(6), context.getResources().getStringArray(R.array.cardNames)[4], null,
                "c6_head", null, "c6", null, null,null,
                numChessForUpgrd, 570,11,4,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c17",player.unlockCards.contains("c17")?0:1, Card.RACE_HUMAN, Card.CLAZZ_HUNTER, skillNames.get(17), context.getResources().getStringArray(R.array.cardNames)[5], null,
                "c17_head", null, "c17", null, null,null,
                numChessForUpgrd, 500,10,5,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c19",player.unlockCards.contains("c19")?0:1, Card.RACE_ORCS, Card.CLAZZ_HUNTER, skillNames.get(19), context.getResources().getStringArray(R.array.cardNames)[6], null,
                "c19_head", null, "c19", null, null,null,
                numChessForUpgrd,520,8,6,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c26",player.unlockCards.contains("c26")?0:1, Card.RACE_DWARF, Card.CLAZZ_KNIGHT, skillNames.get(26), context.getResources().getStringArray(R.array.cardNames)[7], null,
                "c26_head", null, "c26", null, null,null,
                numChessForUpgrd,510,7,7,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c27",player.unlockCards.contains("c27")?0:1, Card.RACE_DEMON, Card.CLAZZ_KNIGHT, skillNames.get(27), context.getResources().getStringArray(R.array.cardNames)[8], null,
                "c27_head", null, "c27", null, null,null,
                numChessForUpgrd, 360,10,7,1);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c37",player.unlockCards.contains("c37")?0:1, Card.RACE_DWARF, Card.CLAZZ_KNIGHT, skillNames.get(37), context.getResources().getStringArray(R.array.cardNames)[9], null,
                "c37_head", null, "c37", null, null,null,
                numChessForUpgrd, 460,9,6,1);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c39",player.unlockCards.contains("c39")?0:1, Card.RACE_HUMAN, Card.CLAZZ_ROGUE, skillNames.get(39), context.getResources().getStringArray(R.array.cardNames)[10], null,
                "c39_head", null, "c39", null, null,null,
                numChessForUpgrd, 490,17,3,1);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c46",player.unlockCards.contains("c46")?0:1, Card.RACE_DWARF, Card.CLAZZ_MAGE, skillNames.get(46),context.getResources().getStringArray(R.array.cardNames)[11], null,
                "c46_head", null, "c46", null, null,null,
                numChessForUpgrd, 450,11,5,1);
        GameContext.gameContext.cardDAO.insert(chess);



        chess = new Card("c7",player.unlockCards.contains("c7")?0:1, Card.RACE_GOO, Card.CLAZZ_SHAMAN, skillNames.get(7), context.getResources().getStringArray(R.array.cardNames)[12], null,
                "c7_head", null, "c7", null, null,null,
                numChessForUpgrd, 580,13,4 ,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c8",player.unlockCards.contains("c8")?0:1, Card.RACE_SPIRIT, Card.CLAZZ_WARLOCK, skillNames.get(8), context.getResources().getStringArray(R.array.cardNames)[13], null,
                "c8_head", null, "c8", null, null,null,
                numChessForUpgrd, 600,10,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c10",player.unlockCards.contains("c10")?0:1, Card.RACE_ORCS, Card.CLAZZ_ROGUE, skillNames.get(10), context.getResources().getStringArray(R.array.cardNames)[14], null,
                "c10_head", null, "c10", null, null,null,
                numChessForUpgrd, 570, 13,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c11",player.unlockCards.contains("c11")?0:1, Card.RACE_ORCS, Card.CLAZZ_ROGUE, skillNames.get(11), context.getResources().getStringArray(R.array.cardNames)[15], null,
                "c11_head", null, "c11", null, null,null,
                numChessForUpgrd, 550,11,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c16",player.unlockCards.contains("c16")?0:1, Card.RACE_HUMAN, Card.CLAZZ_ROGUE, skillNames.get(16), context.getResources().getStringArray(R.array.cardNames)[16], null,
                "c16_head", null, "c16", null, null,null,
                numChessForUpgrd, 500,15,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c18",player.unlockCards.contains("c18")?0:1, Card.RACE_HUMAN, Card.CLAZZ_HUNTER, skillNames.get(18), context.getResources().getStringArray(R.array.cardNames)[17], null,
                "c18_head", null, "c18", null, null,null,
                numChessForUpgrd, 600,10,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c23",player.unlockCards.contains("c23")?0:1, Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(23), context.getResources().getStringArray(R.array.cardNames)[18], null,
                "c23_head", null, "c23", null, null,null,
                numChessForUpgrd, 750,10,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c24",player.unlockCards.contains("c24")?0:1, Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(24), context.getResources().getStringArray(R.array.cardNames)[19], null,
                "c24_head", null, "c24", null, null,null,
                numChessForUpgrd, 500,12,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c38",player.unlockCards.contains("c38")?0:1, Card.RACE_DEMON, Card.CLAZZ_WARRIOR, skillNames.get(38), context.getResources().getStringArray(R.array.cardNames)[20], null,
                "c38_head", null, "c38", null, null,null,
                numChessForUpgrd, 500,20,3,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c40",player.unlockCards.contains("c40")?0:1, Card.RACE_DWARF, Card.CLAZZ_KNIGHT, skillNames.get(40), context.getResources().getStringArray(R.array.cardNames)[21], null,
                "c40_head", null, "c40", null, null,null,
                numChessForUpgrd, 380,20,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c45",player.unlockCards.contains("c45")?0:1, Card.RACE_ORCS, Card.CLAZZ_MAGE, skillNames.get(45), context.getResources().getStringArray(R.array.cardNames)[22], null,
                "c45_head", null, "c45", null, null,null,
                numChessForUpgrd, 460,11,6,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c51",player.unlockCards.contains("c51")?0:1, Card.RACE_UNDEAD, Card.CLAZZ_ROGUE, skillNames.get(51), context.getResources().getStringArray(R.array.cardNames)[23], null,
                "c51_head", null, "c51", null, null,null,
                numChessForUpgrd,750,8,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c54",player.unlockCards.contains("c54")?0:1, Card.RACE_UNDEAD, Card.CLAZZ_WARRIOR, skillNames.get(54), context.getResources().getStringArray(R.array.cardNames)[24], null,
                "c54_head", null, "c54", null, null,null,
                numChessForUpgrd, 700,9,5,2);
        GameContext.gameContext.cardDAO.insert(chess);



        chess = new Card("c4",player.unlockCards.contains("c4")?0:1, Card.RACE_ELF, Card.CLAZZ_MAGE, skillNames.get(4), context.getResources().getStringArray(R.array.cardNames)[25], null,
                "c4_head", null, "c4", null, null,null,
                numChessForUpgrd, 600,11,6,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c9",player.unlockCards.contains("c9")?0:1, Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(9), context.getResources().getStringArray(R.array.cardNames)[26], null,
                "c9_head", null, "c9", null, null,null,
                numChessForUpgrd, 710,14,4,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c22",player.unlockCards.contains("c22")?0:1, Card.RACE_SPIRIT, Card.CLAZZ_SHAMAN, skillNames.get(22),context.getResources().getStringArray(R.array.cardNames)[27], null,
                "c22_head", null, "c22", null, null,null,
                numChessForUpgrd, 730,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c25",player.unlockCards.contains("c25")?0:1, Card.RACE_ELF, Card.CLAZZ_HUNTER, skillNames.get(25), context.getResources().getStringArray(R.array.cardNames)[28], null,
                "c25_head", null, "c25", null, null,null,
                numChessForUpgrd, 740,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c28",player.unlockCards.contains("c28")?0:1, Card.RACE_DWARF, Card.CLAZZ_ROGUE, skillNames.get(28), context.getResources().getStringArray(R.array.cardNames)[29], null,
                "c28_head", null, "c28", null, null,null,
                numChessForUpgrd, 530,25,3,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c32",player.unlockCards.contains("c32")?0:1, Card.RACE_MARINE, Card.CLAZZ_WARRIOR, skillNames.get(32), context.getResources().getStringArray(R.array.cardNames)[30], null,
                "c32_head", null, "c32", null, null,null,
                numChessForUpgrd, 610,11,6,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c35",player.unlockCards.contains("c35")?0:1, Card.RACE_DIVINITY, Card.CLAZZ_WARRIOR, skillNames.get(35), context.getResources().getStringArray(R.array.cardNames)[31], null,
                "c35_head", null, "c35", null, null,null,
                numChessForUpgrd, 670,12,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c42",player.unlockCards.contains("c42")?0:1, Card.RACE_ORCS, Card.CLAZZ_MAGE, skillNames.get(42), context.getResources().getStringArray(R.array.cardNames)[32], null,
                "c42_head", null, "c42", null, null,null,
                numChessForUpgrd,720,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c43",player.unlockCards.contains("c43")?0:1, Card.RACE_SPIRIT, Card.CLAZZ_MAGE, skillNames.get(43), context.getResources().getStringArray(R.array.cardNames)[33], null,
                "c43_head", null, "c43", null, null,null,
                numChessForUpgrd, 670,10,6,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c44",player.unlockCards.contains("c44")?0:1, Card.RACE_UNDEAD, Card.CLAZZ_SHAMAN,skillNames.get(44), context.getResources().getStringArray(R.array.cardNames)[34], null,
                "c44_head", null, "c44", null, null,null,
                numChessForUpgrd, 840,12,4,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c52",player.unlockCards.contains("c52")?0:1, Card.RACE_HUMAN, Card.CLAZZ_KNIGHT, skillNames.get(52), context.getResources().getStringArray(R.array.cardNames)[35], null,
                "c52_head", null, "c52", null, null,null,
                numChessForUpgrd, 1000,10,4,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c53",player.unlockCards.contains("c53")?0:1, Card.RACE_HUMAN, Card.CLAZZ_WARRIOR, skillNames.get(53), context.getResources().getStringArray(R.array.cardNames)[36], null,
                "c53_head", null, "c53", null, null,null,
                numChessForUpgrd, 720,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c55",player.unlockCards.contains("c55")?0:1, Card.RACE_SPIRIT, Card.CLAZZ_SHAMAN, skillNames.get(55), context.getResources().getStringArray(R.array.cardNames)[37], null,
                "c55_head", null, "c55", null, null,null,
                numChessForUpgrd,920,11,4,3);
        GameContext.gameContext.cardDAO.insert(chess);


        chess = new Card("c13",player.unlockCards.contains("c13")?0:1, Card.RACE_DEMON, Card.CLAZZ_HUNTER, skillNames.get(13), context.getResources().getStringArray(R.array.cardNames)[38], null,
                "c13_head", null, "c13", null, null,null,
                numChessForUpgrd,590,21,4,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c14",player.unlockCards.contains("c14")?0:1, Card.RACE_DEMON, Card.CLAZZ_WARRIOR, skillNames.get(14), context.getResources().getStringArray(R.array.cardNames)[39], null,
                "c14_head", null, "c14", null, null,null,
                numChessForUpgrd,660,19,4,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c20",player.unlockCards.contains("c20")?0:1, Card.RACE_MARINE, Card.CLAZZ_WARRIOR, skillNames.get(20), context.getResources().getStringArray(R.array.cardNames)[40], null,
                "c20_head", null, "c20", null, null,null,
                numChessForUpgrd, 800,9,7,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c30",player.unlockCards.contains("c30")?0:1, Card.RACE_HUMAN, Card.CLAZZ_KNIGHT, skillNames.get(30), context.getResources().getStringArray(R.array.cardNames)[41], null,
                "c30_head", null, "c30", null, null,null,
                numChessForUpgrd, 720,10,7,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c33",player.unlockCards.contains("c33")?0:1, Card.RACE_DEMON, Card.CLAZZ_KNIGHT, skillNames.get(33), context.getResources().getStringArray(R.array.cardNames)[42], null,
                "c33_head", null, "c33", null, null,null,
                numChessForUpgrd,900,11,5 ,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c34",player.unlockCards.contains("c34")?0:1, Card.RACE_DEMON, Card.CLAZZ_WARRIOR, skillNames.get(34), context.getResources().getStringArray(R.array.cardNames)[43], null,
                "c34_head", null, "c34", null, null,null,
                numChessForUpgrd, 630,20,4,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c36",player.unlockCards.contains("c36")?0:1, Card.RACE_UNDEAD, Card.CLAZZ_WARLOCK, skillNames.get(36), context.getResources().getStringArray(R.array.cardNames)[44], null,
                "c36_head", null, "c36", null, null,null,
                numChessForUpgrd, 560,30,3,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c41",player.unlockCards.contains("c41")?0:1, Card.RACE_ELF, Card.CLAZZ_WARLOCK, skillNames.get(41), context.getResources().getStringArray(R.array.cardNames)[45], null,
                "c41_head", null, "c41", null, null,null,
                numChessForUpgrd,1000,10,5,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c49",player.unlockCards.contains("c49")?0:1, Card.RACE_MARINE, Card.CLAZZ_SHAMAN, skillNames.get(49), context.getResources().getStringArray(R.array.cardNames)[46], null,
                "c49_head", null, "c49", null, null,null,
                numChessForUpgrd, 830,12,5,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c50",player.unlockCards.contains("c50")?0:1, Card.RACE_MARINE, Card.CLAZZ_HUNTER, skillNames.get(50), context.getResources().getStringArray(R.array.cardNames)[47], null,
                "c50_head", null, "c50", null, null,null,
                numChessForUpgrd, 830,10,6,4);
        GameContext.gameContext.cardDAO.insert(chess);


        chess = new Card("c12",player.unlockCards.contains("c12")?0:1, Card.RACE_DIVINITY, Card.CLAZZ_PRIEST, skillNames.get(12), context.getResources().getStringArray(R.array.cardNames)[48], null,
                "c12_head", null, "c12", null, null,null,
                numChessForUpgrd, 590,17,7,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c15",player.unlockCards.contains("c15")?0:1, Card.RACE_ELF, Card.CLAZZ_WARLOCK, skillNames.get(15), context.getResources().getStringArray(R.array.cardNames)[49], null,
                "c15_head", null, "c15", null, null,null,
                numChessForUpgrd,900,13,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c21",player.unlockCards.contains("c21")?0:1, Card.RACE_SPIRIT, Card.CLAZZ_SHAMAN, skillNames.get(21), context.getResources().getStringArray(R.array.cardNames)[50], null,
                "c21_head", null, "c21", null, null,null,
                numChessForUpgrd,830,12,7,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c29",player.unlockCards.contains("c29")?0:1, Card.RACE_DIVINITY, Card.CLAZZ_WARRIOR, skillNames.get(29), context.getResources().getStringArray(R.array.cardNames)[51], null,
                "c29_head", null, "c29", null, null,null,
                numChessForUpgrd,780,15,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c31",player.unlockCards.contains("c31")?0:1, Card.RACE_MARINE, Card.CLAZZ_WARRIOR,skillNames.get(31),context.getResources().getStringArray(R.array.cardNames)[52], null,
                "c31_head", null, "c31", null, null,null,
                numChessForUpgrd,1060,11,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c47",player.unlockCards.contains("c47")?0:1, Card.RACE_DIVINITY, Card.CLAZZ_HUNTER, skillNames.get(47), context.getResources().getStringArray(R.array.cardNames)[53], null,
                "c47_head", null, "c47", null, null,null,
                numChessForUpgrd,650,18,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c48",player.unlockCards.contains("c48")?0:1, Card.RACE_UNDEAD, Card.CLAZZ_WARLOCK, skillNames.get(48), context.getResources().getStringArray(R.array.cardNames)[54], null,
                "c48_head", null, "c48", null, null,null,
                numChessForUpgrd, 700,20,5,5);
        GameContext.gameContext.cardDAO.insert(chess);
    }
}