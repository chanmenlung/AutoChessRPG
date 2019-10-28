package com.longtail360.autochessrpg.entity;

public class Card {
    public long id;
    public int locked;
    public String race;
    public String job;
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
    public int level_1_hp;
    public int level_2_hp;
    public int level_3_hp;
    public int level_1_mp;
    public int level_2_mp;
    public int level_3_mp;
    public int level_1_attack;
    public int level_2_attack;
    public int level_3_attack;
    public int level_1_defense;
    public int level_2_defense;
    public int level_3_defense;

    public Card(){}
    public Card(int locked, String race, String job, String skillCode, String name, String customName, String head, String customHead,
                String image, String customImage, String customSkillName, String customSkillBattleDesc, int numChessForUpgrd, int level_1_hp,
                int level_2_hp, int level_3_hp, int level_1_mp, int level_2_mp, int level_3_mp, int level_1_attack, int level_2_attack,
                int level_3_attack, int level_1_defense, int level_2_defense, int level_3_defense) {
        this.id = id;
        this.locked = locked;
        this.race = race;
        this.job = job;
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
        this.level_1_hp = level_1_hp;
        this.level_2_hp = level_2_hp;
        this.level_3_hp = level_3_hp;
        this.level_1_mp = level_1_mp;
        this.level_2_mp = level_2_mp;
        this.level_3_mp = level_3_mp;
        this.level_1_attack = level_1_attack;
        this.level_2_attack = level_2_attack;
        this.level_3_attack = level_3_attack;
        this.level_1_defense = level_1_defense;
        this.level_2_defense = level_2_defense;
        this.level_3_defense = level_3_defense;
    }
}
