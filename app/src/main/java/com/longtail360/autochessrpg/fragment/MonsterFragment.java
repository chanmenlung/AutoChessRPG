package com.longtail360.autochessrpg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.prefab.MonsterDescItem;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.List;

public class MonsterFragment extends BaseFragment {
    private String tag = "MonsterFragment";
    private ViewGroup monsterListLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.log(tag, "monsterFragment");
        View view = inflater.inflate(R.layout.all_monster, container, false);
        monsterListLayout = view.findViewById(R.id.monsterListLayout);
        List<Monster> monsterList = GameContext.gameContext.monsterDAO.getAll();
        Logger.log(tag, monsterList.size()+"");
        for(Monster m : monsterList) {
            MonsterDescItem item = new MonsterDescItem(getContext(), m);
            monsterListLayout.addView(item);
        }
        return view;
    }
}
