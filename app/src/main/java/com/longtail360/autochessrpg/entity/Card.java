package com.longtail360.autochessrpg.entity;

public class Card {
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

    public Card(){}
    public Card(String code, int locked, String race, String clazz, String skillCode, String name, String customName, String head, String customHead,
                String image, String customImage, String customSkillName, String customSkillBattleDesc, int numChessForUpgrd, int level_1_hp,
                 int level_1_attack, int level_1_defense) {
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
    }
}
