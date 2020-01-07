package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class BaseAction {
    private static String tag = "BaseAction";
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
        result.add(create (context, UseItemHpUp.KEY));
        result.add(create (context, UseItemAttackUp.KEY));
        result.add(create (context, UseItemCdDown.KEY));
        result.add(create (context, UseItemFire.KEY));
        result.add(create (context, UseItemIce.KEY));
        result.add(create (context, UseItemElectricity.KEY));
        result.add(create (context, UseItemPotion.KEY));
        result.add(create (context, UseItemSummonS.KEY));
        result.add(create (context, UseItemSummonL.KEY));
        result.add(create (context, UseItemPerfume.KEY));
        return result;
    }

    public static BaseAction create(Context context, String name) {
        BaseAction cnd = null;
        if (UseItemHpUp.KEY.equals (name)) {
            cnd = new UseItemHpUp (context);
        }
        else if (UseItemCdDown.KEY.equals (name)) {
            cnd = new UseItemCdDown (context);
        }
        else if (UseItemFire.KEY.equals (name)) {
            cnd = new UseItemFire (context);
        }
        else if (UseItemIce.KEY.equals (name)) {
            cnd = new UseItemIce (context);
        }
        else if (UseItemElectricity.KEY.equals (name)) {
            cnd = new UseItemElectricity (context);
        }
        else if (UseItemPerfume.KEY.equals (name)) {
            cnd = new UseItemPerfume (context);
        }
        else if (UseItemPotion.KEY.equals (name)) {
            cnd = new UseItemPotion (context);
        }
        else if (UseItemSummonL.KEY.equals (name)) {
            cnd = new UseItemSummonL (context);
        }
        else if (UseItemAttackUp.KEY.equals (name)) {
            cnd = new UseItemAttackUp (context);
        }
        else if (UseItemSummonS.KEY.equals (name)) {
            cnd = new UseItemSummonS (context);
        }
        else {
            Logger.log (tag, "Cannot new BaseAction:"+name);
        }
        return cnd;
    }

    public void convertParaToObject(Context context){

    }
}
