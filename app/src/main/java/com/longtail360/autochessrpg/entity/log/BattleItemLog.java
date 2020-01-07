package com.longtail360.autochessrpg.entity.log;

/**
 * Created by chanmenlung on 20/10/2018.
 */

public class BattleItemLog {
    public static int RED = 1;
    public static int GREEN = 2;
    public static int BLUE = 3;
    public static int YELLOW = 4;

    public long id;
    public String title;
    public String content;
    public String icon1;
    public String icon2;
    public int color;
    public BattleRootLog battleRootLog;
    public int icon1Type; //1 = internal, 2 = external

    public BattleItemLog(){
    }

    public BattleItemLog(String cardId, String title, String content){ //temp
    }

    public BattleItemLog(String title, String content, String color, String icon){
    }

    public static BattleItemLog createCardItem(String cardId, String title, String content) {
        BattleItemLog item = new BattleItemLog (cardId,title, content);
        return item;
    }

    public static BattleItemLog createCardAttack(String title, String content, String color, String icon) {
        BattleItemLog item = new BattleItemLog (title, content, color, icon);
        return item;
    }


}
