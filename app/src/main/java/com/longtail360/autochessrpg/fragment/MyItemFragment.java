package com.longtail360.autochessrpg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.prefab.ItemIcon;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.List;

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
        initPopupBox(myItemLayout, getContext());
        onBodyItemsContainer = view.findViewById(R.id.onBodyItemsContainer);
        notOnBodyItemsContainer = view.findViewById(R.id.notOnBodyItemsContainer);
        List<MyItem> onBodyItems = MyItem.listByOnBody(GameContext.gameContext.adventure.id);
        Logger.log(tag,"onBodyItems-size:"+GameContext.gameContext.itemGotDAO.getAll().size());

        List<MyItem> myItemList = GameContext.gameContext.itemGotDAO.getAll();
        for(MyItem item : myItemList){
            Logger.log(tag,"item-advId:"+item.adventureId);
        }
        for(final MyItem item : onBodyItems){
            final ItemIcon icon = new ItemIcon(getContext(),item.item);
            icon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popupBox.title.setText(item.item.name);
                    popupBox.content.setText(item.item.desc);
                    popupBox.centerConfirmHideBox();
                    popupBox.show();
                    return true;
                }
            });
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(GameContext.gameContext.adventure.currentRootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
                        Logger.toast(getContext().getString(R.string.ui_myItem_advProgressing),getContext());
                        return;
                    }
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

        List<MyItem> notOnBodyItems = MyItem.listByNotOnBody(GameContext.gameContext.adventure.id);
        for(final MyItem item : notOnBodyItems){
            final ItemIcon icon = new ItemIcon(getContext(),item.item);
            icon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popupBox.title.setText(item.item.name);
                    popupBox.content.setText(item.item.desc);
                    popupBox.centerConfirmHideBox();
                    popupBox.show();
                    return true;
                }
            });
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(GameContext.gameContext.adventure.currentRootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
                        Logger.toast(getContext().getString(R.string.ui_myItem_advProgressing),getContext());
                        return;
                    }
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
