package com.longtail360.autochessrpg.adventure;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

/**
 * Created by chanmenlung on 15/2/2019.
 */

public class CardActionEngine extends CharacterActionEngine{
    public Context context;
    public AdvContext advContext;
    public MyCard cardInBattle;

    public CardActionEngine(Context context, AdvContext advContext, MyCard card){
        this.context = context;
        this.advContext = advContext;
        this.cardInBattle = card;
    }

    public ActionResult randomNormalAttackMonster() {
        int randomAttackWhichMonster = advContext.mRandom.nextInt(advContext.battleContext.monsterActions.size());
        Monster monster = advContext.battleContext.monsterActions.get(randomAttackWhichMonster).monster;
        return normalAttackMonster(monster);
    }
    
    public ActionResult normalAttackMonster(Monster monster) {
        ActionResult result = new ActionResult();
        result.icon1 = cardInBattle.card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        if(monster != null) {
            int hurt = cardInBattle.card.attack;
            if (cardInBattle.card.clazz.equals(Card.CLAZZ_MAGE)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_mageNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_WARRIOR)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_warriorNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_PRIEST)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_priestNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_HUNTER)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_hunterNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_KNIGHT)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_knightNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_SHAMAN)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_shamanNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_ROGUE)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_rogueNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_WARLOCK)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_warlockNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }
            else if(cardInBattle.card.clazz.equals(Card.CLAZZ_WARRIOR)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", cardInBattle.card.name);
                result.content = context.getString(R.string.battle_warriorNormalAttack).replace("{card}", cardInBattle.card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
                monster.hp = monster.hp - hurt;
            }

            if(monster.hp <= 0) {
                MonsterActionEngine focusAction = null;
                for(MonsterActionEngine monsterActionEngine : advContext.battleContext.monsterActions){
                    if(monsterActionEngine.monster == monster){
                        focusAction = monsterActionEngine;
                    }
                }
                advContext.battleContext.deadMonsterActions.add(focusAction);
                advContext.battleContext.monsterActions.remove(focusAction);
            }
        }
        return result;
    }

    private Card findLowestHpCard() {
        Card result = advContext.team.get(0).card;
        for(MyCard card : advContext.team) {
            if(card.card.hp < result.hp) {
                result = card.card;
            }
        }
        return result;
    }

}
