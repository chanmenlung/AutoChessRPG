package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class BaseAction {
    @Expose
    public String key;
    @Expose
    public String desc;
    public String optionItem2Title;
    public List<OptionItem> optionItems2; //dd2
    @Expose
    public OptionItem selectOption2;

    @Expose
    public OptionItem selectOption2_1;

    public String optionItem3Title;
    public List<OptionItem> optionItems3; //dd3
    @Expose
    public OptionItem selectOption3;
    public String preLabel3; //對象
    public String postLabel3; //對象


    public ActionResult action (Context context, AdvContext advContext) throws JSONException {return null;}
    public String concatDesc (Context context){return "";}

//    public void doLogicAfterAllCardDoAllAction(Context context, AdvContext advContext, CharacterActionEngine myselfAction){}
//    public static List<BaseAction> listAll(Context context, BaseCard c) {
//        List<BaseAction> result = new ArrayList<>();
//        result.add (create (context,c, DoNoneAction.KEY));
//        return result;
//    }
    public static List<BaseAction> listAll(Context context) {
        List<BaseAction> result = new ArrayList<>();
        result.add(create (context, UseSmallRedPotion.KEY));
        return result;
    }

    public static BaseAction create(Context context, String name) {
        BaseAction cnd = null;
        if (UseSmallRedPotion.KEY.equals (name)) {
            cnd = new UseSmallRedPotion (context);
        }
//        else if (DoNoneAction.KEY.equals (name)) {
//            cnd = new DoNoneAction (context,card);
//        }
//        else {
//            Logger.log ("Cannot new BaseAction:"+name);
//        }
        return cnd;
    }

    public void convertParaToObject(Context context){

    }
}
