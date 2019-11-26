package com.longtail360.autochessrpg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.ItemGot;
import com.longtail360.autochessrpg.prefab.ItemIcon;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.List;

import static com.longtail360.autochessrpg.activity.BaseActivity.player;

public class MyItemFragment extends BaseFragment{
    private String tag = "MyItemFragment";
    private ViewGroup myItemLayout;
    private ViewGroup onBodyItemsContainer;
    private ViewGroup notOnBodyItemsContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.log(tag,"init-MyItemFragment");
        View view = inflater.inflate(R.layout.my_item, container, false);
        myItemLayout = view.findViewById(R.id.myItemLayout);
        onBodyItemsContainer = view.findViewById(R.id.onBodyItemsContainer);
        notOnBodyItemsContainer = view.findViewById(R.id.notOnBodyItemsContainer);
        List<ItemGot> onBodyItems = ItemGot.listByOnBody(GameContext.gameContext.adventure.id);
        for(final ItemGot item : onBodyItems){
            final ItemIcon icon = new ItemIcon(getContext(),item.item);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(icon.getParent() == notOnBodyItemsContainer){
                        notOnBodyItemsContainer.removeView(icon);
                        onBodyItemsContainer.addView(icon);
                        item.onBody = 1;
                        GameContext.gameContext.itemGotDAO.update(item);
                        Logger.toast(getContext().getString(R.string.ui_myItem_hadBrought),getContext());
                    }else {
                        onBodyItemsContainer.removeView(icon);
                        notOnBodyItemsContainer.addView(icon);
                        item.onBody = -1;
                        GameContext.gameContext.itemGotDAO.update(item);
                        Logger.toast(getContext().getString(R.string.ui_myItem_hadSaved),getContext());
                    }
                }
            });
            onBodyItemsContainer.addView(icon);
        }

        List<ItemGot> notOnBodyItems = ItemGot.listByNotOnBody(GameContext.gameContext.adventure.id);
        for(final ItemGot item : notOnBodyItems){
            final ItemIcon icon = new ItemIcon(getContext(),item.item);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(icon.getParent() == notOnBodyItemsContainer){
                        notOnBodyItemsContainer.removeView(icon);
                        onBodyItemsContainer.addView(icon);
                        item.onBody = 1;
                        GameContext.gameContext.itemGotDAO.update(item);
                        Logger.toast(getContext().getString(R.string.ui_myItem_hadBrought),getContext());
                    }else {
                        onBodyItemsContainer.removeView(icon);
                        notOnBodyItemsContainer.addView(icon);
                        item.onBody = -1;
                        GameContext.gameContext.itemGotDAO.update(item);
                        Logger.toast(getContext().getString(R.string.ui_myItem_hadSaved),getContext());
                    }
                }
            });
            notOnBodyItemsContainer.addView(icon);
        }
        return view;
    }
}
