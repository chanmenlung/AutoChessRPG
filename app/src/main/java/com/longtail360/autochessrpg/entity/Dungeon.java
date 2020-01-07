package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.skill.ElectricAllBigHurt;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    public long id;
    public long index;
    public String name;
    public String monsterIds;
    public int numFloor;
    public int numAreaPerFloor;
    public int numBlockPerArea;

    private String[] monsterArray;
    public Dungeon(){

    }

    public String[] getMonsterArray() {
        if(monsterArray == null){
            monsterArray = monsterIds.split(",");
        }
        return monsterArray;
    }
    public Dungeon(long index, String name, String monsterIds, int numFloor, int numAreaPerFloor, int numBlockPerArea){
        this.index = index;
        this.name = name;
        this.monsterIds = monsterIds;
        this.numFloor = numFloor;
        this.numAreaPerFloor = numAreaPerFloor;
        this.numBlockPerArea = numBlockPerArea;
    }
    public static void init(Context context) {
        List<String> monsterIds = new ArrayList<String>();
        for(int i=0; i<57; i++) {
            int start = 5*i;
            StringBuilder str = new StringBuilder();
            str.append("m");
            str.append(start+1);
            str.append(",");
            str.append("m");
            str.append(start+2);
            str.append(",");
            str.append("m");
            str.append(start+3);
            str.append(",");
            str.append("m");
            str.append(start+4);
            str.append(",");
            str.append("m");
            str.append(start+5);
            monsterIds.add(str.toString());
        }
        int numFloorStart = 1;
        int numArea = 2;
        int numBlock = 3;
        String[] names = context.getResources().getStringArray(R.array.dungeonNames);
        Dungeon dungeon;

        for(int i=1; i<=10; i++) {
            dungeon = new Dungeon(i,names[i-1],monsterIds.get(i-1),numFloorStart+i,numArea,numBlock);
            GameContext.gameContext.dungeonDAO.insert(dungeon);
        }
        int j = 1;
        for(int i=11; i<=20; i++) {
            dungeon = new Dungeon(i,names[i-1],monsterIds.get(i-1),numFloorStart+1+j,numArea,numBlock);
            GameContext.gameContext.dungeonDAO.insert(dungeon);
            j++;
        }

        j = 1;
        for(int i=21; i<=30; i++) {
            dungeon = new Dungeon(i,names[i-1],monsterIds.get(i-1),numFloorStart+2+j,numArea,numBlock);
            GameContext.gameContext.dungeonDAO.insert(dungeon);
            j++;
        }
        j = 1;
        for(int i=31; i<=40; i++) {
            dungeon = new Dungeon(i,names[i-1],monsterIds.get(i-1),numFloorStart+3+j,numArea,numBlock);
            GameContext.gameContext.dungeonDAO.insert(dungeon);
            j++;
        }
        j = 1;
        for(int i=41; i<=50; i++) {
            dungeon = new Dungeon(i,names[i-1],monsterIds.get(i-1),numFloorStart+4+j,numArea,numBlock);
            GameContext.gameContext.dungeonDAO.insert(dungeon);
            j++;
        }
        j = 1;
        for(int i=51; i<=57; i++) {
            dungeon = new Dungeon(i,names[i-1],monsterIds.get(i-1),numFloorStart+5+j,numArea,numBlock);
            GameContext.gameContext.dungeonDAO.insert(dungeon);
            j++;
        }

    }
}
