package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.skill.ElectricAllBigHurt;

public class Dungeon {
    public long id;
    public long index;
    public String name;
    public String monsterIds;
    public int numFloor;
    public int numblockPerFloor;

    public Dungeon(){

    }
    public Dungeon(String name, String monsterIds, int numFloor, int numblockPerFloor){
        this.name = name;
        this.monsterIds = monsterIds;
        this.numFloor = numFloor;
        this.numblockPerFloor = numblockPerFloor;
    }
    public static void init(Context context) {
        String[] names = context.getResources().getStringArray(R.array.dungeonNames);
        Dungeon dungeon;
        dungeon = new Dungeon(names[0],"m1,m2,m3,m4,m5",5,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[1],"m6,m7,m8,m9,m10",6,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[2],"m11,m12,m13,m14,m15",7,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[3],"m16,m17,m18,m19,m20",8,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[4],"m21,m22,m23,m24,m25",9,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[5],"m26,m27,m28,m29,m30",10,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[6],"m31,m32,m33,m34,m35",11,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[7],"m36,m37,m38,m39,m40",12,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[8],"m41,m42,m43,m44,m45",13,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);
        dungeon = new Dungeon(names[9],"m46,m47,m48,m49,m50",14,10);
        GameContext.gameContext.dungeonDAO.insert(dungeon);

    }
}
