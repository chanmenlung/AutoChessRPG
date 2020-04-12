package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.BaseActivity;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.ImageUtils;

import java.io.File;

public class HomeCardDesc extends FrameLayout  {
    private View cardDescLayout;
    private TextView cardDescLevel;
    private TextView cardDescValue;
    private TextView cardDescSkill;
    private TextView cardDescPrice;
    private View cardDescCancelBt;
    private View cardDescSellBt;
    private View closeBt;
    private View bigImageBackground;
    private ImageView cardDescHead;
    private ImageView bigImage;
    private CardIcon icon;
    private TextView editText;
    public HomeCardDesc(Context context) {
        super(context);
    }

    public HomeCardDesc(final Context context, final CallBack callBack) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.home_card_desc, this);
        cardDescLayout = findViewById(R.id.cardDescLayout);
        cardDescLevel = findViewById(R.id.cardDescLevel);
        cardDescValue = findViewById(R.id.cardDescValue);
        cardDescSkill = findViewById(R.id.cardDescSkill);
        cardDescPrice = findViewById(R.id.cardInfoDescPrice);
        cardDescCancelBt = findViewById(R.id.cardDescCancelBt);
        cardDescSellBt = findViewById(R.id.cardDescSellBt);
        cardDescHead = findViewById(R.id.cardDescHead);
        editText = findViewById(R.id.cardNameEdit);
        bigImage = findViewById(R.id.bigImage);
        closeBt = findViewById(R.id.closeBt);
        bigImageBackground = findViewById(R.id.bigImageBackground);
        cardDescCancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                HomeCardDesc.this.setVisibility(View.GONE);
                callBack.doActionAfterClickCardDetail(icon);
            }
        });
        cardDescSellBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeCardDesc.this.setVisibility(View.GONE);
                callBack.doActionAfterSellCard(icon);
            }
        });
        closeBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeCardDesc.this.setVisibility(View.GONE);
            }
        });
    }

    public void reloadCard(Context context, CardIcon cardIcon) {
        Card card = cardIcon.myCard.getCard(context);
        CustomCard customCard = GameContext.gameContext.customCardDAO.getByCode(card.code);
        int resourceId = ImageUtils.convertImageStringToInt(context, card.image);
        this.icon = cardIcon;
        cardDescLevel.setText(context.getString(R.string.ui_home_cardDesc_level)+1);
        cardDescValue.setText(context.getString(R.string.ui_home_cardDesc_cardInfo)
                .replace("{hp}", cardIcon.myCard.battleHp+"/"+cardIcon.myCard.totalHp)
                .replace("{attack}", (cardIcon.myCard.battleAttack-1)+" - "+(cardIcon.myCard.battleAttack+1))
                .replace("{defense}", cardIcon.myCard.battleDefense+"")
        );
        cardDescSkill.setText(context.getString(R.string.ui_home_cardDesc_skill)+cardIcon.myCard.skill.getDesc(context));
        cardDescPrice.setText(context.getString(R.string.ui_home_cardDesc_sellCard).replace("{coin}",cardIcon.myCard.getSellingPrice()+""));
        card.setHeadToImageView(context, cardDescHead);
//        editText.setText(card.name);
        if(customCard.customImage != null && !customCard.customImage.equals("null")) {
            bigImage.setImageBitmap(ImageUtils.convertBase64ToImage(customCard.customImage));
        }else {
            bigImage.setImageResource(resourceId);
        }
        if(customCard.showCardBackground ==1) {
            bigImageBackground.setVisibility(VISIBLE);
        }else {
            bigImageBackground.setVisibility(INVISIBLE);
        }
        editText.setText(card.name);

    }


    public interface CallBack {
        void doActionAfterSellCard(CardIcon cardIcon);
        void doActionAfterClickCardDetail(CardIcon cardIcon);
    }
}
