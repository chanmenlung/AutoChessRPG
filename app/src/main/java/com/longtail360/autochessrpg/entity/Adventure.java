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
        exp = exp + value;
        int totalExp = calTotalExpByLevel(level);
        if(exp >= totalExp){
            level = level + 1;
            exp = exp - totalExp;
        }
    }
    public int calTotalExpByLevel(int level){
        if(level == 1 || level == 2){
            return 1;
        }

        int result = 2;
        for(int i=3; i<level;i++){
            result = result * 2;
        }
        return result;
    }
}
