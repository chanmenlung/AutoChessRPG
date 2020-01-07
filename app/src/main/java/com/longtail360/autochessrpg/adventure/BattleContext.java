package com.longtail360.autochessrpg.adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 12/2/2019.
 */

public class BattleContext {
    public boolean monsterStopAttack = false;
    public double monsterHurtFactor = 1;
    public boolean skillForbid = false;
    public List<MonsterActionEngine> monsterActions = new ArrayList<>();
    public List<MonsterActionEngine> deadMonsterActions = new ArrayList<>();
}
