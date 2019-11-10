package com.longtail360.autochessrpg.entity;

public class Adventure {
    public long id;
    public int coin;
    public long currentDungeonId;
    public int finalMark;
    public int level;
    public int exp;
    public int hp;

    public Adventure() {}
    public Adventure(int coin, long currentDungeonId, int finalMark, int level, int exp, int hp) {
        this.coin = coin;
        this.currentDungeonId = currentDungeonId;
        this.finalMark = finalMark;
        this.level = level;
        this.exp = exp;
        this.hp = hp;

    }
}
