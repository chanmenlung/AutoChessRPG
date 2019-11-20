package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.utils.ImageUtils;

import java.io.File;

public class CardIcon extends FrameLayout {
    public Card card;
    private ImageView headImage;
    public CardIcon(Context context) {
        super(context);
    }

    public CardIcon(Context context, Card card) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_icon, this);
        this.card = card;
        headImage = findViewById(R.id.headImage);
        loadHeadImage(context, card);

    }

    public void loadHeadImage(Context context,Card card){
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
}
