package com.longtail360.autochessrpg.listener;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.longtail360.autochessrpg.activity.HomeActivity;
import com.longtail360.autochessrpg.prefab.CardIcon;
import com.longtail360.autochessrpg.utils.Logger;

/**
 * Created by chanmenlung on 7/10/2018.
 */

public class DragDropOnDragListener implements View.OnDragListener {
    private String tag = "DragDropOnDragListener";
    public DragDropOnDragListener(Context context) {
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        // Get the drag drop action.
        int dragAction = dragEvent.getAction();

        if(dragAction == dragEvent.ACTION_DRAG_STARTED)
        {
            // Check whether the dragged view can be placed in this target view or not.
            Logger.log(tag,"ACTION_DRAG_STARTED:");
            ClipDescription clipDescription = dragEvent.getClipDescription();

            if (clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                // Return true because the target view can accept the dragged object.
                return true;

            }
        }else if(dragAction == dragEvent.ACTION_DRAG_ENTERED)
        {
            Logger.log(tag,"ACTION_DRAG_ENTERED:");
            // When the being dragged view enter the target view, change the target view background color.
            changeTargetViewBackground(view, Color.GRAY);

            return true;
        }else if(dragAction == dragEvent.ACTION_DRAG_EXITED)
        {
            Logger.log(tag,"ACTION_DRAG_EXITED:");
            // When the being dragged view exit target view area, clear the background color.
            resetTargetViewBackground(view);

            return true;
        }else if(dragAction == dragEvent.ACTION_DRAG_ENDED)
        {
//            Logger.log("ACTION_DRAG_ENDED:");
            // When the drop ended reset target view background color.
//            resetTargetViewBackground(view);

            // Get drag and drop action result.
            boolean result = dragEvent.getResult();

//            CardIcon cardIcon = (CardIcon)dragEvent.getLocalState();
//            cardIcon.setVisibility(View.VISIBLE);
            getCardIcon().setVisibility(View.VISIBLE);
            return true;

        }else if(dragAction == dragEvent.ACTION_DROP)
        {
            Logger.log(tag,"ACTION_DROP:");
            // When drop action happened.

            // Get clip data in the drag event first.
            ClipData clipData = dragEvent.getClipData();

            // Get drag and drop item count.
            int itemCount = clipData.getItemCount();

            // If item count bigger than 0.
            if(itemCount > 0) {
                resetTargetViewBackground(view);
//                CardIcon cardIcon = (CardIcon)dragEvent.getLocalState();
                if(getCardIcon() == null){
                    Logger.log(tag, "cardIcon is null");
                }else {
                    Logger.log(tag, "cardIcon is not null");
                }
                ViewGroup owner = (ViewGroup) getCardIcon().getParent();
                ViewGroup newContainer = (ViewGroup) view;

                if(newContainer.getChildCount() > 0) {
                    getCardIcon().setVisibility(View.VISIBLE);
                }else { //success drop
                    Logger.log(tag,"success-drop");
//                    owner.removeView(cardIcon);
//                    newContainer.addView(cardIcon);
//                    cardIcon.setVisibility(View.VISIBLE);
                    onSuccessDrop(owner,newContainer, getCardIcon());
                }
                return true;
            }

        }else if(dragAction == dragEvent.ACTION_DRAG_LOCATION)
        {
            Logger.log(tag,"ACTION_DRAG_LOCATION:");
            return true;
        }else
        {
            Logger.log(tag,"Drag and drop unknow action type.:");
        }

        return false;
    }

    public void onSuccessDrop(ViewGroup oldContainer, ViewGroup newContainer, CardIcon cardIcon) {
//        cardIcon.card.gridIndex = (int)newContainer.getTag();
//        BaseActivity.player.buffTeamCard();
    }
    /* Reset drag and drop target view's background color. */
    private void resetTargetViewBackground(View view)
    {
        // Clear color filter.
        view.getBackground().clearColorFilter();

        // Redraw the target view use original color.
        view.invalidate();
    }


    /* Change drag and drop target view's background color. */
    private void changeTargetViewBackground(View view, int color)
    {
        /* When the being dragged view enter the target view, change the target view background color.
        *
        *  color : The changed to color value.
        *
        *  mode : When to change the color, SRC_IN means source view ( dragged view ) enter target view.
        * */
        view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        // Redraw the target view use new color.
        view.invalidate();
    }

    public CardIcon getCardIcon() {
        return null;
    }
}
