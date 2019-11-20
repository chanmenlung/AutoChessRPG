package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.entity.GameContext;

public class BaseSkill {
    public long id;
    public String code;
    public String name;
    public String desc;
    public int cd;
    public String battleDesc;
    public String statusDesc;


    public static void init(Context context) {
        BaseSkill skill = new ElectricAllBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ElectricAllHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ElectricSingleBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ElectricSingleHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ElectricAllBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new FireAllBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new FireAllHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new FireSingleBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new FireSingleHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HealAll(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HealAllBig(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HealSingle(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HealSingleBig(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HitAllBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HitAllHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HitSingleBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new HitSingleHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new IceAllBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new IceAllHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new IceSingleBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new IceSingleHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new PotionAllBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new PotionAllHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new PotionSingleBigHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new PotionSingleHurt(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ValueUpAllAttack(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ValueUpAllDefense(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ValueUpAttack(context);
        GameContext.gameContext.skillDAO.insert(skill);
        skill = new ValueUpDefense(context);
        GameContext.gameContext.skillDAO.insert(skill);

    }
}
