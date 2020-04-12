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
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;

public class CardIcon extends FrameLayout {
    private String tag = "CardIcon";
    public MyCard myCard;
    private ImageView headImage;
    public View upgradeBt;
    private ImageView raceIcon;
    private ImageView classIcon;
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
        raceIcon = findViewById(R.id.raceIcon);
        classIcon = findViewById(R.id.classIcon);
        loadHeadImage(context,myCard.getCard(context));

        upgradeBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.doActionAfterClickUpgrade(CardIcon.this);
            }
        });
        upgradeBt.setVisibility(GONE);
        updateStar();
    }

    public void reload(Context context,MyCard myCard) {
        this.myCard = myCard;
        loadHeadImage(context, myCard.getCard(context));
        updateStar();
        upgradeBt.setVisibility(GONE);
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
                return true;
            }
        });
    }


    public void loadHeadImage(Context context,Card card){
        Logger.log(tag, "cardCode:"+card.code);
        CustomCard customCard = GameContext.gameContext.customCardDAO.getByCode(card.code);
        int headResourceId = ImageUtils.convertImageStringToInt(context, card.head);
        Logger.log(tag, "customHead:"+customCard.customHead);
        if(customCard != null && customCard.customHead != null && !customCard.customHead.equals("null")) {
            headImage.setImageBitmap(ImageUtils.convertBase64ToImage(customCard.customHead));
        }else {
            headImage.setImageResource(headResourceId);
        }
        if(card.clazz.equals(Card.CLAZZ_MAGE)){
            classIcon.setImageResource(R.drawable.class_mage);
        }else if(card.clazz.equals(Card.CLAZZ_WARRIOR)){
            classIcon.setImageResource(R.drawable.class_warrior);
        }else if(card.clazz.equals(Card.CLAZZ_PRIEST)){
            classIcon.setImageResource(R.drawable.class_priest);
        }else if(card.clazz.equals(Card.CLAZZ_HUNTER)){
            classIcon.setImageResource(R.drawable.class_hunter);
        }else if(card.clazz.equals(Card.CLAZZ_KNIGHT)){
            classIcon.setImageResource(R.drawable.class_knight);
        }else if(card.clazz.equals(Card.CLAZZ_SHAMAN)){
            classIcon.setImageResource(R.drawable.class_shaman);
        }else if(card.clazz.equals(Card.CLAZZ_ROGUE)){
            classIcon.setImageResource(R.drawable.class_rogue);
        }else if(card.clazz.equals(Card.CLAZZ_WARLOCK)){
            classIcon.setImageResource(R.drawable.class_warlock);
        }
        if(card.race.equals(Card.RACE_HUMAN)){
            raceIcon.setImageResource(R.drawable.race_human);
        }else if(card.race.equals(Card.RACE_ELF)){
            raceIcon.setImageResource(R.drawable.race_elf);
        }else if(card.race.equals(Card.RACE_SPIRIT)){
            raceIcon.setImageResource(R.drawable.race_spirit);
        }else if(card.race.equals(Card.RACE_UNDEAD)){
            raceIcon.setImageResource(R.drawable.race_undead);
        }else if(card.race.equals(Card.RACE_ORCS)){
            raceIcon.setImageResource(R.drawable.race_orc);
        }else if(card.race.equals(Card.RACE_DEMON)){
            raceIcon.setImageResource(R.drawable.race_demon);
        }else if(card.race.equals(Card.RACE_DIVINITY)){
            raceIcon.setImageResource(R.drawable.race_divinity);
        }else if(card.race.equals(Card.RACE_MARINE)){
            raceIcon.setImageResource(R.drawable.race_marine);
        }else if(card.race.equals(Card.RACE_DWARF)){
            raceIcon.setImageResource(R.drawable.race_dwarf);
        }else if(card.race.equals(Card.RACE_GOO)){
            raceIcon.setImageResource(R.drawable.race_goo);
        }
    }

    public interface CallBack {
        void doActionAfterLongClick(CardIcon cardIcon);
        void doActionAfterClickUpgrade(CardIcon cardIcon);
    }


}
