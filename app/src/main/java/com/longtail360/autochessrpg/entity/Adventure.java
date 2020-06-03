package com.longtail360.autochessrpg.entity;

import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;

import java.util.List;

public class Adventure {
    public long id;
    public int coin;
    public long currentRootLogId;
    public int currentDungeonId;
    public int finalMark;
    public int level;
    public int exp;
    public int hp;
    public int lockBuyingCard;

    public RootLog currentRootLog;
    public Adventure() {}
    public Adventure(int coin, int currentDungeonId, int finalMark, int level, int exp, int hp) {
        this.coin = coin;
        this.currentDungeonId = currentDungeonId;
        this.finalMark = finalMark;
        this.level = level;
        this.exp = exp;
        this.hp = hp;

    }

    public void getExp(int value) {
        if(level == 10){
            return;
        }
        exp = exp + value;
        int totalExp = calTotalExpByLevel(level);
        if(exp >= totalExp){
            level = level + 1;
            if(level == 10){
                exp = 40;
            }
            exp = exp - totalExp;
        }
    }
    public int calTotalExpByLevel(int level){
        switch (level){
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 4;
            case 6:
                return 8;
            case 7:
                return 16;
            case 8:
                return 24;
            case 9:
                return 32;
            case 10:
                return 40;
        }
        return 40;
    }
}
