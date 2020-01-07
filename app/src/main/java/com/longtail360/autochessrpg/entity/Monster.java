package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class Monster extends Character{
    public long id;
    public String code;
    public String name;
    public int hp;
    public int attack;
    public int defense;

    public static void init(Context context) {
        String[] names = context.getResources().getStringArray(R.array.monsterNames);
        Monster monster;
        for(int i=0; i<names.length; i++) {
            monster = new Monster();
            monster.name = names[i];
            monster.code = "m"+(i+1);
            monster.attack = 1;
            monster.defense = 1;
            monster.hp = 10;
            GameContext.gameContext.monsterDAO.insert(monster);
        }
    }
}
