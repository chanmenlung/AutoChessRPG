package com.longtail360.autochessrpg.entity.tactic.target;

import android.content.Context;

import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Character;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class BaseTarget {
    private static String tag = "BaseTarget";
    public String key;
    public String desc;
    public List<OptionItem> optionItems; //dd3
    public OptionItem selectOption;
    protected Card card;
    public BaseTarget(Card card){
        this.card = card;
    }


    public Character find (Context context, AdvContext advContext) throws JSONException {return null;}

    public static List<BaseTarget> listMyTeamTargets(Context context, Card c) {
        List<BaseTarget> result = new ArrayList<>();
        return result;
    }
    public static List<BaseTarget> listMonsterTargets(Context context, Card c) {
        List<BaseTarget> result = new ArrayList<>();
        return result;
    }

    public static List<BaseTarget> listAll(Context context, Card c) {
        List<BaseTarget> result = new ArrayList<>();
        return result;
    }

    public static BaseTarget create(Context context, Card card, String name) {
        BaseTarget cnd = null;
        switch (name){
//            case AnyoneBackendTarget.KEY:
//                cnd = new AnyoneBackendTarget (context,card);
//                break;
//            case AnyoneFrontendTarget.KEY:
//                cnd = new AnyoneFrontendTarget (context,card);
//                break;
//            case AnyoneMiddleTarget.KEY:
//                cnd = new AnyoneMiddleTarget (context,card);
//                break;
//            case AnyoneMonsterTarget.KEY:
//                cnd = new AnyoneMonsterTarget (context,card);
//                break;
//            case AnyoneTarget.KEY:
//                cnd = new AnyoneTarget (context,card);
//                break;
//            case HpLessMonsterTarget.KEY:
//                cnd = new HpLessMonsterTarget (context,card);
//                break;
//            case HpLessTarget.KEY:
//                cnd = new HpLessMonsterTarget (context,card);
//                break;
//            case HpMostMonsterTarget.KEY:
//                cnd = new HpMostTarget (context,card);
//                break;
//            case HpMostTarget.KEY:
//                cnd = new HpMostTarget (context,card);
//                break;
//            case MpLessTarget.KEY:
//                cnd = new MpLessTarget (context,card);
//                break;
//            case MpMostTarget.KEY:
//                cnd = new MpMostTarget (context,card);
//                break;
//            case MySelfTarget.KEY:
//                cnd = new MySelfTarget (context,card);
//                break;
//            case NamingCardTarget.KEY:
//                cnd = new NamingCardTarget (context,card);
//                break;
        }
        if(cnd == null){
            Logger.log(tag,"Cannot instance by key:"+name);
        }
        return cnd;
    }
}
