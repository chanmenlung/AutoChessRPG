package com.longtail360.autochessrpg.entity.tactic;


import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.condition.BaseCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class AndGroup {

    public List<BaseCondition> conds = new ArrayList<>();
    public AndGroup(){
    }

    public boolean doOperation(AdvContext advContext, Card myself) {
        boolean result = true;
        for (int i = 0; i < conds.size(); i++) {
            result = result && conds.get(i).checking (advContext,myself);
        }
        return result;
    }
}
