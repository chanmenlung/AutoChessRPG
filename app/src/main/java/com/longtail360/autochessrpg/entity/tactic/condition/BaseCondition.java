package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class BaseCondition{
    private static String tag = "BaseCondition";
    @Expose
    public String key;
    public String desc;
    public List<OptionItem> optionItems2; //dd2
    public List<OptionItem> optionItems3; //dd3
    @Expose
    public OptionItem selectOption2;
    @Expose
    public OptionItem selectOption2_1;
    @Expose
    public OptionItem selectOption3;

    @Expose
    public int operatorType = 2; // 1=and , 2=or
    @Expose
    public boolean negation = false;

    public boolean checking (AdvContext advContext){return false;}
    public String concatDesc (){return "";}

    public static List<BaseCondition> listAll(Context context) {
        List<BaseCondition> result = new ArrayList<>();
        result.add (create (context,AllManHp.KEY));
        result.add (create (context,AllMonsterHp.KEY));
        result.add (create (context,AnyoneHp.KEY));
        result.add (create (context,AnyOneMonsterHp.KEY));
        result.add (create (context,AnyTwoMonsterHp.KEY));
        result.add (create (context, NumberOfMonster.KEY));
        return result;
    }

    public static BaseCondition create(Context context,  String name) {
        BaseCondition cnd = null;
        if (AllManHp.KEY.equals (name)) {
            cnd = new AllManHp (context);
        }
        else if (AllMonsterHp.KEY.equals (name)) {
            cnd = new AllMonsterHp (context);
        }
        else if (AnyoneHp.KEY.equals (name)) {
            cnd = new AnyoneHp (context);
        }
        else if (AnyOneMonsterHp.KEY.equals (name)) {
            cnd = new AnyOneMonsterHp (context);
        }
        else if (AnyTwoMonsterHp.KEY.equals (name)) {
            cnd = new AnyTwoMonsterHp (context);
        }
        else if (NumberOfFloor.KEY.equals (name)) {
            cnd = new NumberOfFloor (context);
        }
        else if (NumberOfMonster.KEY.equals (name)) {
            cnd = new NumberOfMonster (context);
        }
        else {
            Logger.log (tag,"Cannot new BaseCondition:"+name);
        }
        return cnd;
    }
}
