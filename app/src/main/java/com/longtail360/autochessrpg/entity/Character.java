package com.longtail360.autochessrpg.entity;

public class Character {
    public static String NONE_STATUS = "NONE_STATUS";
    public static String ELECTRICITY_STATUS = "ELECTRICITY_STATUS";
    public static String FIRE_STATUS = "FIRE_STATUS";
    public static String POTION_STATUS = "POTION_STATUS";
    public static String ICE_STATUS = "ICE_STATUS";

    public String label;
    public int buffCri;
    public int buffAgi;
    public int buffDefense;
    public int buffAttack;

    public boolean divineShield;
    public boolean reflectShield;
    public boolean skipAttack;
    public boolean taunt;
    public boolean windFury;
    public int agi; //battleAgi
    public int cri;//battleCri  CriticalHit
    public boolean noneStatus;
    public boolean eleStatus; //attack down
    public boolean fireStatus; //defense down 1
    public boolean potionStatus; //hp down per turn
    public boolean iceStatus; //skip one turn
    public boolean useSkill = true;

    public void resetStatus() {
        divineShield = false;
        reflectShield = false;
        skipAttack = false;
        taunt = false;
        noneStatus = false;
        eleStatus = false;
        fireStatus = false;
        potionStatus = false;
        iceStatus = false;
        windFury = false;
    }
}
