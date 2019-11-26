package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class BaseCondition {
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
    public Card card;
    public BaseCondition(Card card){
        this.card = card;
    }

    public boolean checking (AdvContext advContext, Card myself){return false;}
    public String concatDesc (){return "";}

    public static List<BaseCondition> listAll(Context context) {
        List<BaseCondition> result = new ArrayList<>();
//        result.add (create (context,c, AllManHp.KEY));
//        result.add (create (context,c, AllManMp.KEY));
//        result.add (create (context,c, AnyoneHp.KEY));
//        result.add (create (context,c, AnyoneMp.KEY));
//        result.add (create (context,c, MySelfHp.KEY));
//        result.add (create (context,c, MySelfMp.KEY));
//        result.add (create (context,c, NamingHp.KEY));
//        result.add (create (context,c, NamingMp.KEY));
//        result.add (create (context,c, NumberOfLayer.KEY));
//        result.add (create (context,c, NumberOfMonster.KEY));
        return result;
    }

    public static BaseCondition create(Context context,  String name) {
        BaseCondition cnd = null;
//        if (AllManHp.KEY.equals (name)) {
//            cnd = new AllManHp (context,card);
//        } else if (AllManMp.KEY.equals (name)) {
//            cnd = new AllManMp (context,card);
//        }else if (AnyoneHp.KEY.equals (name)) {
//            cnd = new AnyoneHp (context,card);
//        }else if (AnyoneMp.KEY.equals (name)) {
//            cnd = new AnyoneMp (context,card);
//        } else if (MySelfHp.KEY.equals (name)) {
//            cnd = new MySelfHp (context,card);
//        } else if (MySelfMp.KEY.equals (name)) {
//            cnd = new MySelfMp (context,card);
//        } else if (NamingHp.KEY.equals (name)) {
//            cnd = new NamingHp (context,card);
//        } else if (NamingMp.KEY.equals (name)) {
//            cnd = new NamingMp (context,card);
//        } else if (NumberOfLayer.KEY.equals (name)) {
//            cnd = new NumberOfLayer (context,card);
//        } else if (NumberOfMonster.KEY.equals (name)) {
//            cnd = new NumberOfMonster (context,card);
//        }  else {
//            Logger.log ("Cannot new BaseCondition:"+name);
//        }
        return cnd;
    }
}
