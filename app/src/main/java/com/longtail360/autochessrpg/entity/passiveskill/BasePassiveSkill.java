package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;

import java.util.ArrayList;
import java.util.List;

public class BasePassiveSkill implements Comparable<BasePassiveSkill>{
    public String code;
    public int number1;
    public int number2;
    public int number3;
    public String desc1;
    public String desc2;
    public String desc3;
    public String raceClass;
    //==============================
    public boolean exist;
    public boolean isActive1;
    public boolean isActive2;
    public boolean isActive3;
    public int activeNumber;

    public void reset() {
        isActive1 = false;
        isActive2 = false;
        isActive3 = false;
        activeNumber = 0;
    }
	
	public ActionResult doActionOnBattleStart(Context context, AdvContext advContext) {return null;}
	public ActionResult doActionOnBattleEnd(Context context,AdvContext advContext) {return null;}
	public ActionResult doActionOnTurnStart(Context context,AdvContext advContext) {return null;}
	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {return null;}
	public ActionResult doActionOnCardAttackStart(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}
	public ActionResult doActionOnCardAttackEnd(Context context,AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}
	public ActionResult doActionOnMonsterAttackStart(Context context,AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}
	public ActionResult doActionOnMonsterAttackEnd(Context context,AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}

	
	public ActionResult active(AdvContext advContext) {return null;}
    public void doCheckingAndSetActiveNumber(List<MyCard> team) {
        activeNumber = 0;
        for(MyCard myCard : team){
            if(myCard.card.clazz.equals(this.raceClass)){
                activeNumber++;
            }
            if(myCard.card.race.equals(this.raceClass)){
                activeNumber++;
            }
        }
        if(activeNumber >= number1){
            isActive1 = true;
        }
        if(number2 > 0 && activeNumber >= number2){
            isActive2 = true;
        }
        if(number3 > 0 && activeNumber >= number3){
            isActive3 = true;
        }

    }

    public static List<BasePassiveSkill> listAll(Context context) {
        List<BasePassiveSkill> result = new ArrayList<>();
        result.add(new AttackDown(context));
        result.add(new AttackUp(context));
        result.add(new CdDown(context));
        result.add(new CriticalHitUp(context));
        result.add(new DefenseDown(context));
        result.add(new DefenseUp(context));
        result.add(new DodgeAttackUp(context));
        result.add(new GetGoodBoxUp(context));
        result.add(new HealPerTurn(context));
        result.add(new HpDown(context));
        result.add(new HpUp(context));
        result.add(new HpUpDown(context));
        result.add(new LifeSteal(context));
        result.add(new NotDeadPerBattle(context));
        result.add(new ReflectAttack(context));
        result.add(new StopAttackPerAttack(context));
        result.add(new StopAttackPerTurn(context));
        result.add(new SummonMonster(context));
        return result;
    }

    @Override
    public int compareTo(BasePassiveSkill basePassiveSkill) {
	    if(this.activeNumber > basePassiveSkill.activeNumber){
	        return -1;
        }else if(this.activeNumber == basePassiveSkill.activeNumber){
	        return  0;
        }else {
            return 1;
        }
    }
}
