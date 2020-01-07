package com.longtail360.autochessrpg.adventure;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.tactic.Tactics;

import org.json.JSONException;

/**
 * Created by chanmenlung on 15/2/2019.
 */

public class TeamActionEngine {
    private AdvContext advContext;
    private Context context;
    public TeamActionEngine(Context context, AdvContext advContext){
        this.context = context;
        this.advContext = advContext;
    }


    public ActionResult takeABreake() {
        ActionResult result = new ActionResult();
//        if(advContext.commonTactic.takeBreakValue == 1) {  //稍有危險
//            if(oneOfCardHpLower(0.7) || oneOfCardMpLower(0.7)) {
//                result.doThisAction = true;
//            }
//        }else if(advContext.commonTactic.takeBreakValue == 2) {  //適度
//            if(oneOfCardHpLower(0.5) || oneOfCardMpLower(0.5)) {
//                result.doThisAction = true;
//            }
//        }else if(advContext.commonTactic.takeBreakValue == 3) { //try best
//            if(oneOfCardHpLower(0.3) || oneOfCardMpLower(0.3)) {
//                result.doThisAction = true;
//            }
//        }else if(advContext.commonTactic.takeBreakValue == 4) {  //從不
//            result.doThisAction = false;
//        }

            result.doThisAction = true;
            restoreTeamHp(0.1);
			result.title = context.getString(R.string.adv_takeBreakContent);
			result.detail = context.getString(R.string.adv_restoreSomeHpMP);
			result.icon1 = "item_funnel";
            return result;
    }

    public void restoreTeamHp(double ratio) { //called by spring event, ratio value: 0-1
        for(int i=0; i<advContext.cardActions.size(); i++){
//        for(BaseCard card : advContext.teamCards) {

            MyCard card = advContext.cardActions.get(i).cardInBattle;
            if(card != null) {
                card.battleHp = (int)(card.battleHp + card.getTotalHp() * ratio);
                if(card.battleHp > card.getTotalHp()) {
                    card.battleHp = card.getTotalHp();
                }
                if(card.battleHp < 0){
                    card.battleHp = 0;
                }
            }
        }
    }


    public ActionResult excuteTactics() throws JSONException {
        ActionResult result = new ActionResult();
        for(Tactics ta : advContext.tacticsList){
            if(ta.doChecking(advContext)) {
                result = ta.action.action(context,advContext);
                if(result.doThisAction) {
                    return result;
                }
            }
        }
        if(result.doThisAction) {
            return result;
        }

//        result = excuteCommonTactics();
        return result;

    }
    public boolean checkAllDead() {
        if(advContext.cardActions.size() == 0){
            return true;
        }else {
            return false;
        }
    }

    //======================================
    private boolean oneOfCardHpLower(double ratio) {
        for(CardActionEngine cardAction : advContext.cardActions) {
            if(cardAction.cardInBattle.card != null) {
                if(cardAction.cardInBattle.battleHp < (cardAction.cardInBattle.card.hp * ratio)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean oneOfCardHpLower(int count,double ratio) {//one of them, two of them, three of them ....
        int countIndex = 0;
        for(CardActionEngine cardAction : advContext.cardActions) {
            if(cardAction.cardInBattle.battleHp < (cardAction.cardInBattle.card.hp * ratio)) {
                countIndex++;
                if(count == countIndex){
                    return true;
                }
            }
        }
        return false;
    }
}
