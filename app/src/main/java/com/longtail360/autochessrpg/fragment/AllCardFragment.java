package com.longtail360.autochessrpg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.prefab.CardDescItem;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.List;

public class AllCardFragment extends BaseFragment implements CardDescItem.CallBack{
    String tag = "AllCardFragment";
    private ViewGroup cardListLayout;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        Logger.log(tag,"init-AllCardFragment");
//        View view = inflater.inflate(R.layout.all_card, container, false);
//        cardListLayout = view.findViewById(R.id.cardListLayout);
//        List<Card> cards = GameContext.gameContext.cardDAO.getAll();
//        Logger.log(tag,"cards-size:"+cards.size());
//        for(Card card : cards){
//            CardDescItem item  = new CardDescItem(getContext(), this, card);
//            cardListLayout.addView(item);
//        }
//        return view;
//    }

    @Override
    public void doActionAfterClickDetail(CardDescItem focusItem, Card card) {

    }

    @Override
    public void doActionAfterUnlockCard(CardDescItem focusItem, Card card) {

    }
}
