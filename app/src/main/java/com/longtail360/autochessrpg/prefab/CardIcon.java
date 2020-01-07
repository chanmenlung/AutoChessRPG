package com.longtail360.autochessrpg.prefab;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;

public class CardIcon extends FrameLayout {
    private String tag = "CardIcon";
    public MyCard myCard;
    private ImageView headImage;
    public View upgradeBt;
    private View star1;
    private View star2;
    private View star3;
    public CardIcon(Context context) {
        super(context);
    }

    public CardIcon(Context context, MyCard myCard, final CallBack callBack) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card_icon, this);
        this.myCard = myCard;
        headImage = findViewById(R.id.headImage);
        upgradeBt = findViewById(R.id.upgradeBt);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        loadHeadImage(context, myCard.card);

        upgradeBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.doActionAfterClickUpgrade(CardIcon.this);
            }
        });
        upgradeBt.setVisibility(GONE);
        updateStar();
    }

    public void updateStar(){
        if(myCard.level == 1){
            star1.setVisibility(VISIBLE);
            star2.setVisibility(GONE);
            star3.setVisibility(GONE);
        }
        if(myCard.level == 2){
            star1.setVisibility(GONE);
            star2.setVisibility(VISIBLE);
            star3.setVisibility(GONE);
        }
        if(myCard.level == 3){
            star1.setVisibility(GONE);
            star2.setVisibility(GONE);
            star3.setVisibility(VISIBLE);
        }
    }
    public void setLongClick(final CallBack callBack) {
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                callBack.doActionAfterLongClick(CardIcon.this);
                return false;
            }
        });
    }

    public void setDragAndDrop() {
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    String tag = (String)view.getTag();
                    ClipData clipData = ClipData.newPlainText("", tag);

                    // Create drag shadow builder object.
                    View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(clipData, dragShadowBuilder, view, 0);


                    // Hide the view object because we are dragging it.
                    view.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
    }


    public void loadHeadImage(Context context,Card card){
        Logger.log(tag, "cardCode:"+card.code);
        int headResourceId = ImageUtils.convertImageStringToInt(context, card.head);
        if(card.customHead != null) {
            File file = new File(card.customHead);
            if(file.exists()) {
                headImage.setImageURI(Uri.parse(card.customHead));
            }else {
                headImage.setImageResource(headResourceId);
            }
        }else {
            headImage.setImageResource(headResourceId);
        }
    }

    public interface CallBack {
        void doActionAfterLongClick(CardIcon cardIcon);
        void doActionAfterClickUpgrade(CardIcon cardIcon);
    }


}
