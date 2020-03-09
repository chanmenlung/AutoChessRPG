package com.longtail360.autochessrpg.listener;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

import com.longtail360.autochessrpg.activity.HomeActivity;
import com.longtail360.autochessrpg.utils.Logger;

/**
 * Created by chanmenlung on 7/10/2018.
 */

public class DragDropOnTouchListener implements View.OnTouchListener {
    private String tag = "DragDropOnTouchListener";
    public DragDropOnTouchListener() {
        super();
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Logger.log(tag, "motionEvent.getAction():"+motionEvent.getAction());
        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            // Get view object tag value.
            String tag = (String)view.getTag();
    //        Log.i("drag", tag);
            // Create clip data.
            ClipData clipData = ClipData.newPlainText("", tag);

            // Create drag shadow builder object.
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);


            /* Invoke view object's startDrag method to start the drag action.
               clipData : to be dragged data.
               dragShadowBuilder : the shadow of the dragged view.
            */
            view.startDrag(clipData, dragShadowBuilder, view, 0);


            // Hide the view object because we are dragging it.
            view.setVisibility(View.INVISIBLE);
            afterOnTouch();

        }else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_UP){
            view.setVisibility(View.VISIBLE);
        }
        return false;
    }

    public void afterOnTouch() {

    }

}
