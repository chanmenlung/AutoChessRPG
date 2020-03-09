package com.longtail360.autochessrpg.entity.tactic;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.tactic.action.BaseAction;
import com.longtail360.autochessrpg.entity.tactic.action.UseItemAttackUp;
import com.longtail360.autochessrpg.entity.tactic.action.UseItemHpUp;
import com.longtail360.autochessrpg.entity.tactic.condition.AllManHp;
import com.longtail360.autochessrpg.entity.tactic.condition.BaseCondition;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class Tactics implements Comparable<Tactics> {
    private String tag = "Tactics";
    @Expose
    public int order;
//    public String desc = "bbb";
    @Expose
    public List<BaseCondition> conditions = new ArrayList<>();
    @Expose
    public BaseAction action;
    @Expose
    public int active;

    public Tactics(Context context){
        BaseCondition cond = BaseCondition.create(context, AllManHp.KEY);
        if(cond == null){
            Logger.log(tag, "cond is null");
        }
        active = 1;
        conditions.add (cond);
        action = new UseItemHpUp(context);
    }

    public String concatStr(Context context) {
        StringBuilder str = new StringBuilder();
        str.append (context.getString(R.string.tactic_if));
        int count = conditions.size();
        if(count > 0) {
            BaseCondition cnd = conditions.get(0);
            str.append ("<");
            str.append (cnd.concatDesc());
            if(cnd.negation){
                str.append (context.getString(R.string.tactic_negation));
            }
            str.append (">");
        }
        for (int i = 1; i < count; i++) {
            BaseCondition cnd = conditions.get(i);
            if (cnd.operatorType == 1) {
                str.append (context.getString(R.string.tactic_and));
            }else {
                str.append ("、"+context.getString(R.string.tactic_or));
            }
            str.append ("<");
            str.append (cnd.concatDesc());
            if(cnd.negation){
                str.append (context.getString(R.string.tactic_negation));
            }
            str.append (">");
        }
        str.append ("，"+context.getString(R.string.tactic_then));
        str.append ("<");
        str.append (action.concatDesc(context));
        str.append (">");
        return str.toString ();
    }

    public boolean doChecking(AdvContext advContext) {
        List<AndGroup> ands = new ArrayList<>();
        AndGroup andGroup = new AndGroup ();

        for (int i = 0; i < conditions.size(); i++) {
            andGroup.conds.add (conditions.get(i));
            if (conditions.get(i).operatorType == 2) { //if and
                ands.add (andGroup);
                andGroup = new AndGroup ();
            }
        }
        boolean result = false;
        for (int i = 0; i < ands.size(); i++) {
            result = result || ands.get(i).doOperation (advContext);
        }
        return result;
    }
//
    @Override
    public int compareTo(Tactics o) {
        if(o.order > o.order){
            return 1;
        }else if(o.order == o.order){
            return 0;
        }else {
            return -1;
        }
    }
}
