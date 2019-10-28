package com.longtail360.autochessrpg.entity;

public class Adventure {
    public long id;
    public int coin;
    public long currentDungeonId;
    public int finalMark;

    public Adventure() {}
    public Adventure(int coin, long currentDungeonId, int finalMark) {
        this.coin = coin;
        this.currentDungeonId = currentDungeonId;
        this.finalMark = finalMark;
    }
}
