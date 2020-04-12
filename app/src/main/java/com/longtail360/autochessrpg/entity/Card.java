package com.longtail360.autochessrpg.entity;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.skill.*;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import org.apache.commons.lang3.StringUtils;

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
    public String race;
    public String clazz;
    public String skillCode;
    public String name;

    public String head;

    public String image;
    public int numChessForUpgrd;
    //    public int level = 1;
    public int hp; //this is level 1 hp
    public int attack;
    public int defense;
    public int price;
    public int rarity; //1=basic, 2=normal, 3=rare, 4=epic, 5=legendary


    public BaseSkill skill;
    public Card(){}

    public Card(String code, String race, String clazz, String skillCode, String name, String head,
                String image,int numChessForUpgrd, int level_1_hp,
                int level_1_attack, int level_1_defense, int rare) {
        this.code = code;
        this.race = race;
        this.clazz = clazz;
        this.skillCode = skillCode;
        this.name = name;
        this.head = head;
        this.image = image;

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
            }else {

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
        CustomCard customCard = GameContext.gameContext.customCardDAO.getByCode(code);
        int rid = ImageUtils.convertImageStringToInt(context, head);
        if(customCard.customHead != null && !customCard.customHead.equals("null")) {
            imageView.setImageBitmap(ImageUtils.convertBase64ToImage(customCard.customHead));
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
        Player player = GameContext.gameContext.player;

        chess = new Card("c1", Card.RACE_HUMAN, Card.CLAZZ_PRIEST, skillNames.get(1), context.getResources().getStringArray(R.array.cardNames)[0],
                "c1_head", "c1",
                numChessForUpgrd, 500,10,5,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c2",Card.RACE_HUMAN, Card.CLAZZ_PRIEST, skillNames.get(2), context.getResources().getStringArray(R.array.cardNames)[1],
                "c2_head", "c2",
                numChessForUpgrd, 420,10,6,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c3", Card.RACE_MARINE, Card.CLAZZ_WARRIOR, skillNames.get(3), context.getResources().getStringArray(R.array.cardNames)[2],
                "c3_head", "c3",
                numChessForUpgrd, 550, 9,5,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c5", Card.RACE_GOO, Card.CLAZZ_SHAMAN, skillNames.get(5),context.getResources().getStringArray(R.array.cardNames)[3],
                "c5_head", "c5",
                numChessForUpgrd,520,12,4,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c6", Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(6), context.getResources().getStringArray(R.array.cardNames)[4],
                "c6_head",  "c6",
                numChessForUpgrd, 570,11,4,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c17", Card.RACE_HUMAN, Card.CLAZZ_HUNTER, skillNames.get(17), context.getResources().getStringArray(R.array.cardNames)[5],
                "c17_head",  "c17",
                numChessForUpgrd, 500,10,5,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c19", Card.RACE_ORCS, Card.CLAZZ_HUNTER, skillNames.get(19), context.getResources().getStringArray(R.array.cardNames)[6],
                "c19_head",  "c19",
                numChessForUpgrd,520,8,6,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c26",Card.RACE_DWARF, Card.CLAZZ_KNIGHT, skillNames.get(26), context.getResources().getStringArray(R.array.cardNames)[7],
                "c26_head",  "c26",
                numChessForUpgrd,510,7,7,1);
        GameContext.gameContext.cardDAO.insert(chess);

        chess = new Card("c27", Card.RACE_DEMON, Card.CLAZZ_KNIGHT, skillNames.get(27), context.getResources().getStringArray(R.array.cardNames)[8],
                "c27_head",  "c27",
                numChessForUpgrd, 360,10,7,1);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c37",Card.RACE_DWARF, Card.CLAZZ_KNIGHT, skillNames.get(37), context.getResources().getStringArray(R.array.cardNames)[9],
                "c37_head",  "c37",
                numChessForUpgrd, 460,9,6,1);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c39", Card.RACE_HUMAN, Card.CLAZZ_ROGUE, skillNames.get(39), context.getResources().getStringArray(R.array.cardNames)[10],
                "c39_head",  "c39",
                numChessForUpgrd, 490,17,3,1);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c46", Card.RACE_DWARF, Card.CLAZZ_MAGE, skillNames.get(46),context.getResources().getStringArray(R.array.cardNames)[11],
                "c46_head",  "c46",
                numChessForUpgrd, 450,11,5,1);
        GameContext.gameContext.cardDAO.insert(chess);



        chess = new Card("c7",Card.RACE_GOO, Card.CLAZZ_SHAMAN, skillNames.get(7), context.getResources().getStringArray(R.array.cardNames)[12],
                "c7_head",  "c7",
                numChessForUpgrd, 580,13,4 ,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c8", Card.RACE_SPIRIT, Card.CLAZZ_WARLOCK, skillNames.get(8), context.getResources().getStringArray(R.array.cardNames)[13],
                "c8_head", "c8",
                numChessForUpgrd, 600,10,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c10", Card.RACE_ORCS, Card.CLAZZ_ROGUE, skillNames.get(10), context.getResources().getStringArray(R.array.cardNames)[14],
                "c10_head",  "c10",
                numChessForUpgrd, 570, 13,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c11", Card.RACE_ORCS, Card.CLAZZ_ROGUE, skillNames.get(11), context.getResources().getStringArray(R.array.cardNames)[15],
                "c11_head",  "c11",
                numChessForUpgrd, 550,11,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c16",Card.RACE_HUMAN, Card.CLAZZ_ROGUE, skillNames.get(16), context.getResources().getStringArray(R.array.cardNames)[16],
                "c16_head", "c16",
                numChessForUpgrd, 500,15,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c18", Card.RACE_HUMAN, Card.CLAZZ_HUNTER, skillNames.get(18), context.getResources().getStringArray(R.array.cardNames)[17],
                "c18_head",  "c18",
                numChessForUpgrd, 600,10,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c23", Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(23), context.getResources().getStringArray(R.array.cardNames)[18],
                "c23_head",  "c23",
                numChessForUpgrd, 750,10,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c24", Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(24), context.getResources().getStringArray(R.array.cardNames)[19],
                "c24_head",  "c24",
                numChessForUpgrd, 500,12,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c38", Card.RACE_DEMON, Card.CLAZZ_WARRIOR, skillNames.get(38), context.getResources().getStringArray(R.array.cardNames)[20],
                "c38_head",  "c38",
                numChessForUpgrd, 500,20,3,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c40", Card.RACE_DWARF, Card.CLAZZ_KNIGHT, skillNames.get(40), context.getResources().getStringArray(R.array.cardNames)[21],
                "c40_head",  "c40",
                numChessForUpgrd, 380,20,4,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c45",Card.RACE_ORCS, Card.CLAZZ_MAGE, skillNames.get(45), context.getResources().getStringArray(R.array.cardNames)[22],
                "c45_head",  "c45",
                numChessForUpgrd, 460,11,6,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c51", Card.RACE_UNDEAD, Card.CLAZZ_ROGUE, skillNames.get(51), context.getResources().getStringArray(R.array.cardNames)[23],
                "c51_head",  "c51",
                numChessForUpgrd,750,8,5,2);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c54", Card.RACE_UNDEAD, Card.CLAZZ_WARRIOR, skillNames.get(54), context.getResources().getStringArray(R.array.cardNames)[24],
                "c54_head",  "c54",
                numChessForUpgrd, 700,9,5,2);
        GameContext.gameContext.cardDAO.insert(chess);



        chess = new Card("c4", Card.RACE_ELF, Card.CLAZZ_MAGE, skillNames.get(4), context.getResources().getStringArray(R.array.cardNames)[25],
                "c4_head",  "c4",
                numChessForUpgrd, 600,11,6,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c9",Card.RACE_GOO, Card.CLAZZ_MAGE, skillNames.get(9), context.getResources().getStringArray(R.array.cardNames)[26],
                "c9_head", "c9",
                numChessForUpgrd, 710,14,4,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c22", Card.RACE_SPIRIT, Card.CLAZZ_SHAMAN, skillNames.get(22),context.getResources().getStringArray(R.array.cardNames)[27],
                "c22_head",  "c22",
                numChessForUpgrd, 730,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c25", Card.RACE_ELF, Card.CLAZZ_HUNTER, skillNames.get(25), context.getResources().getStringArray(R.array.cardNames)[28],
                "c25_head", "c25",
                numChessForUpgrd, 740,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c28", Card.RACE_DWARF, Card.CLAZZ_ROGUE, skillNames.get(28), context.getResources().getStringArray(R.array.cardNames)[29],
                "c28_head",  "c28",
                numChessForUpgrd, 530,25,3,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c32",Card.RACE_MARINE, Card.CLAZZ_WARRIOR, skillNames.get(32), context.getResources().getStringArray(R.array.cardNames)[30],
                "c32_head",  "c32",
                numChessForUpgrd, 610,11,6,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c35",Card.RACE_DIVINITY, Card.CLAZZ_WARRIOR, skillNames.get(35), context.getResources().getStringArray(R.array.cardNames)[31],
                "c35_head",  "c35",
                numChessForUpgrd, 670,12,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c42",Card.RACE_ORCS, Card.CLAZZ_MAGE, skillNames.get(42), context.getResources().getStringArray(R.array.cardNames)[32],
                "c42_head",  "c42",
                numChessForUpgrd,720,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c43", Card.RACE_SPIRIT, Card.CLAZZ_MAGE, skillNames.get(43), context.getResources().getStringArray(R.array.cardNames)[33],
                "c43_head", "c43",
                numChessForUpgrd, 670,10,6,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c44", Card.RACE_UNDEAD, Card.CLAZZ_SHAMAN,skillNames.get(44), context.getResources().getStringArray(R.array.cardNames)[34],
                "c44_head",  "c44",
                numChessForUpgrd, 840,12,4,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c52", Card.RACE_HUMAN, Card.CLAZZ_KNIGHT, skillNames.get(52), context.getResources().getStringArray(R.array.cardNames)[35],
                "c52_head", "c52",
                numChessForUpgrd, 1000,10,4,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c53", Card.RACE_HUMAN, Card.CLAZZ_WARRIOR, skillNames.get(53), context.getResources().getStringArray(R.array.cardNames)[36],
                "c53_head",  "c53",
                numChessForUpgrd, 720,11,5,3);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c55", Card.RACE_SPIRIT, Card.CLAZZ_SHAMAN, skillNames.get(55), context.getResources().getStringArray(R.array.cardNames)[37],
                "c55_head",  "c55",
                numChessForUpgrd,920,11,4,3);
        GameContext.gameContext.cardDAO.insert(chess);


        chess = new Card("c13", Card.RACE_DEMON, Card.CLAZZ_HUNTER, skillNames.get(13), context.getResources().getStringArray(R.array.cardNames)[38],
                "c13_head",  "c13",
                numChessForUpgrd,590,21,4,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c14", Card.RACE_DEMON, Card.CLAZZ_WARRIOR, skillNames.get(14), context.getResources().getStringArray(R.array.cardNames)[39],
                "c14_head",  "c14",
                numChessForUpgrd,660,19,4,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c20", Card.RACE_MARINE, Card.CLAZZ_WARRIOR, skillNames.get(20), context.getResources().getStringArray(R.array.cardNames)[40],
                "c20_head",  "c20",
                numChessForUpgrd, 800,9,7,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c30",Card.RACE_HUMAN, Card.CLAZZ_KNIGHT, skillNames.get(30), context.getResources().getStringArray(R.array.cardNames)[41],
                "c30_head",  "c30",
                numChessForUpgrd, 720,10,7,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c33", Card.RACE_DEMON, Card.CLAZZ_KNIGHT, skillNames.get(33), context.getResources().getStringArray(R.array.cardNames)[42],
                "c33_head", "c33",
                numChessForUpgrd,900,11,5 ,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c34",Card.RACE_DEMON, Card.CLAZZ_WARRIOR, skillNames.get(34), context.getResources().getStringArray(R.array.cardNames)[43],
                "c34_head",  "c34",
                numChessForUpgrd, 630,20,4,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c36", Card.RACE_UNDEAD, Card.CLAZZ_WARLOCK, skillNames.get(36), context.getResources().getStringArray(R.array.cardNames)[44],
                "c36_head",  "c36",
                numChessForUpgrd, 560,30,3,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c41", Card.RACE_ELF, Card.CLAZZ_WARLOCK, skillNames.get(41), context.getResources().getStringArray(R.array.cardNames)[45],
                "c41_head",  "c41",
                numChessForUpgrd,1000,10,5,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c49", Card.RACE_MARINE, Card.CLAZZ_SHAMAN, skillNames.get(49), context.getResources().getStringArray(R.array.cardNames)[46],
                "c49_head",  "c49",
                numChessForUpgrd, 830,12,5,4);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c50", Card.RACE_MARINE, Card.CLAZZ_HUNTER, skillNames.get(50), context.getResources().getStringArray(R.array.cardNames)[47],
                "c50_head", "c50",
                numChessForUpgrd, 830,10,6,4);
        GameContext.gameContext.cardDAO.insert(chess);


        chess = new Card("c12", Card.RACE_DIVINITY, Card.CLAZZ_PRIEST, skillNames.get(12), context.getResources().getStringArray(R.array.cardNames)[48],
                "c12_head",  "c12",
                numChessForUpgrd, 590,17,7,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c15", Card.RACE_ELF, Card.CLAZZ_WARLOCK, skillNames.get(15), context.getResources().getStringArray(R.array.cardNames)[49],
                "c15_head", "c15",
                numChessForUpgrd,900,13,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c21", Card.RACE_SPIRIT, Card.CLAZZ_SHAMAN, skillNames.get(21), context.getResources().getStringArray(R.array.cardNames)[50],
                "c21_head",  "c21",
                numChessForUpgrd,830,12,7,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c29", Card.RACE_DIVINITY, Card.CLAZZ_WARRIOR, skillNames.get(29), context.getResources().getStringArray(R.array.cardNames)[51],
                "c29_head",  "c29",
                numChessForUpgrd,780,15,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c31", Card.RACE_MARINE, Card.CLAZZ_WARRIOR,skillNames.get(31),context.getResources().getStringArray(R.array.cardNames)[52],
                "c31_head",  "c31",
                numChessForUpgrd,1060,11,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c47", Card.RACE_DIVINITY, Card.CLAZZ_HUNTER, skillNames.get(47), context.getResources().getStringArray(R.array.cardNames)[53],
                "c47_head", "c47",
                numChessForUpgrd,650,18,6,5);
        GameContext.gameContext.cardDAO.insert(chess);
        chess = new Card("c48", Card.RACE_UNDEAD, Card.CLAZZ_WARLOCK, skillNames.get(48), context.getResources().getStringArray(R.array.cardNames)[54],
                "c48_head",  "c48",
                numChessForUpgrd, 700,20,5,5);
        GameContext.gameContext.cardDAO.insert(chess);
    }
}