package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;


/**
 * Created by chanmenlung on 12/10/2018.
 */

public class PopupBox extends FrameLayout {
    public TextView title;
    public TextView content;
    public View cancel;
    public View rightConfirm;
    public View centerConfirm;
    public View popupBox;
    public GridLayout popupBoxImageGrid;
    public PopupBox(Context context, int buttonNum) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.popup_box, this);
        popupBox = findViewById(R.id.popupBox);
        title = ((TextView)findViewById(R.id.popupTitle));
        content = ((TextView)findViewById(R.id.popupContent));
        cancel = findViewById(R.id.cancelButton);
        rightConfirm = findViewById(R.id.rightConfirmButton);
        centerConfirm = findViewById(R.id.centerConfirmButton);
        cancel.setVisibility(View.INVISIBLE);
        rightConfirm.setVisibility(View.INVISIBLE);
        centerConfirm.setVisibility(View.INVISIBLE);

        popupBoxImageGrid = (GridLayout) findViewById(R.id.popupBoxImageGrid);
        if(buttonNum == 0){
            centerConfirm.setVisibility(INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            rightConfirm.setVisibility(View.INVISIBLE);
        }else if(buttonNum == 1) {
            centerConfirm.setVisibility(View.VISIBLE);
        }else {
            cancel.setVisibility(View.VISIBLE);
            rightConfirm.setVisibility(View.VISIBLE);
        }
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.setVisibility(View.INVISIBLE);
            }
        });
        popupBoxImageGrid.setVisibility(GONE);
        popupBox.setVisibility(View.INVISIBLE);
    }

    public void show() {
        popupBox.setVisibility(View.VISIBLE);
    }
    public void hide() {
        popupBox.setVisibility(View.INVISIBLE);
    }
    public void reset(int buttonNum) {
        cancel.setVisibility(View.INVISIBLE);
        rightConfirm.setVisibility(View.INVISIBLE);
        centerConfirm.setVisibility(View.INVISIBLE);
        if(buttonNum == 0){
            centerConfirm.setVisibility(INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            rightConfirm.setVisibility(View.INVISIBLE);
        }else if(buttonNum == 1) {
            centerConfirm.setVisibility(View.VISIBLE);
        }else {
            cancel.setVisibility(View.VISIBLE);
            rightConfirm.setVisibility(View.VISIBLE);
        }
    }

    public void centerConfirmHideBox() {
        reset(1);
        centerConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void deleteAndCreateImageView(int rid){
        popupBoxImageGrid.removeAllViews();
        popupBoxImageGrid.setVisibility(View.VISIBLE);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(rid);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.height = dpToPx(50);
        lp.width = dpToPx(50);
        imageView.setLayoutParams(lp);
        popupBoxImageGrid.addView(imageView);


    }

    public int dpToPx(int dp) {
        float density = getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

}
