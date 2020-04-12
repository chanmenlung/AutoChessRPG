package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.BuyCrystalActivity;
import com.longtail360.autochessrpg.activity.ExternalResActivity;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.utils.AnimationUtil;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CardDetail extends FrameLayout {
    private String tag = "CardDetail";
    private CustomCard customCard;
    private Card dbCard;
    private ImageView bigImage;
    private ImageView headImage;
    private ViewGroup cardDetailLayout;
    private View bigImageBackground;
    private CheckBox backgroundCheckBox;
    public ExternalResActivity activity;
    public CardDescItem cardDescItem;
    private View backBt;
//    private View randomImageBt;
    private View importBigImageBt;
    private View cropCardHeadBt;
    private String tempImageBase64;
    private String tempHeadBase64;
    private View saveBt;
    private EditText editText;

    public CardDetail(Context context) {
        super(context);
    }

    public CardDetail(ExternalResActivity activity,String cardCode) {
        super(activity);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_detail, this);
        cardDetailLayout = findViewById(R.id.cardDetailLayout);
        editText = findViewById(R.id.cardNameEdit);
        backBt =findViewById(R.id.backBt);
        bigImage = findViewById(R.id.bigImage);
        headImage = findViewById(R.id.headImage);
        importBigImageBt = findViewById(R.id.importBigImageBt);
        cropCardHeadBt = findViewById(R.id.cropCardHeadBt);
        saveBt = findViewById(R.id.saveBt);
        bigImageBackground = findViewById(R.id.bigImageBackground);
        backgroundCheckBox = findViewById(R.id.backgroundCheckBox);
        setListener();

        loadCard(cardCode);
    }

    public void loadCard(String cardCode) {
        if(cardCode != null) {
            dbCard = GameContext.gameContext.cardDAO.getByCode(cardCode);
            customCard = GameContext.gameContext.customCardDAO.getByCode(cardCode);
            tempImageBase64 = customCard.customImage;
            tempHeadBase64 = customCard.customHead;
            if(customCard.showCardBackground == 1) {
                backgroundCheckBox.setChecked(true);
                bigImageBackground.setVisibility(VISIBLE);
            }else {
                backgroundCheckBox.setChecked(false);
                bigImageBackground.setVisibility(INVISIBLE);
            }
            Logger.log(tag, tempHeadBase64);
            if(customCard.customName == null || customCard.customName.isEmpty()){
                editText.setText(dbCard.name);
            }else {
                editText.setText(customCard.customName);
            }
            loadCardImageAndHeadImage();
        }
    }

    private void setListener() {
        backBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardDescItem != null){
                    cardDescItem.loadHeadImage(activity,dbCard, customCard);
                }
//                BaseActivity.savePlayerData(activity);
                showDetail(false, true);
            }
        });

        backgroundCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backgroundCheckBox.isChecked()){
                    customCard.showCardBackground = 1;
                }else {
                    customCard.showCardBackground = 0;
                }
                if(customCard.showCardBackground ==1) {
                    bigImageBackground.setVisibility(VISIBLE);
                }else {
                    bigImageBackground.setVisibility(INVISIBLE);
                }
            }
        });


        saveBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customCard.locked == 1){
                    activity.popupBox.reset(2);
                    activity.popupBox.title.setText(activity.getString(R.string.cardDetail_cardHadLocked));
                    activity.popupBox.content.setText(activity.getString(R.string.cardDetail_isUnlockCard)
                            .replace("{crystal}",  Setting.CRYSTAL_FOR_UNLOCK_CARD+"")
                            .replace("{currentCrystal}",  GameContext.gameContext.player.crystal+"")
                    );
                    activity.popupBox.show();
                    activity.popupBox.rightConfirm.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(GameContext.gameContext.player.crystal > Setting.CRYSTAL_FOR_UNLOCK_CARD ){
                                customCard.locked = 0;
//                                customCard.customName = editText.getText().toString();
                                customCard.customImage = tempImageBase64;
                                customCard.customHead = tempHeadBase64;
                                GameContext.gameContext.player.crystal = GameContext.gameContext.player.crystal - Setting.CRYSTAL_FOR_UNLOCK_CARD;
                                GameContext.gameContext.playerDAO.update(GameContext.gameContext.player);
                                boolean update = GameContext.gameContext.customCardDAO.update(customCard);
                                activity.popupBox.hide();
                                Logger.toast(activity.getString(R.string.cardDetail_saved), activity);
                            }else {
                                activity.popupBox.reset(2);
                                activity.popupBox.title.setText(activity.getString(R.string.cardDetail_notEnoughCrystal));
                                activity.popupBox.content.setText(activity.getString(R.string.cardDetail_doesGoToBuyCrystal));
                                activity.popupBox.show();
                                activity.popupBox.rightConfirm.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        activity.startOtherActivity(BuyCrystalActivity.class);
                                    }
                                });
                            }
                        }
                    });
                }else {
                    customCard.customImage = tempImageBase64;
                    customCard.customHead = tempHeadBase64;
                    GameContext.gameContext.playerDAO.update(GameContext.gameContext.player);
                    boolean update = GameContext.gameContext.customCardDAO.update(customCard);
                    Logger.log(tag, "update:"+update);
                    Logger.log(tag, "customCard.customImage:"+customCard.customImage);
                    Logger.toast(activity.getString(R.string.cardDetail_saved), activity);
                }
            }
        });
//        randomImageBt.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int n = random.nextInt(7)+1;
//                Logger.log(tag, "n:"+n);
//                card.image = "c"+n;
//                card.head = card.image+"_head";
//                card.customImage = null;
//                card.customHead = null;
//                tempImagePath = null;
//                tempHeadPath = null;
//                int resourceId = ImageUtils.convertImageStringToInt(activity, card.image);
//                int headResourceId = ImageUtils.convertImageStringToInt(activity,card.head);
//                bigImage.setImageResource(resourceId);
//                headImage.setImageResource(headResourceId);
//            }
//        });

        importBigImageBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.log(tag, "importBigImage");
                activity.readExternalRes();
            }
        });

        cropCardHeadBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customCard.customImage == null && tempImageBase64 == null) {
                    activity.popupBox.title.setText(activity.getResources().getString(R.string.cardDetail_cannotFindImportImage));
                    activity.popupBox.content.setText(activity.getResources().getString(R.string.cardDetail_pleaseImportImage));
                    activity.popupBox.centerConfirmHideBox();
                    activity.popupBox.show();
                }else {
//                    if(tempImageBase64 != null){
//                        file = new File(tempImageBase64);
//                    }else{
//                        file = new File(customCard.customImage);
//                    }
                    if(customCard.customImage == null) {
                        activity.popupBox.title.setText(activity.getResources().getString(R.string.cardDetail_cannotFindImportImage));
                        activity.popupBox.content.setText(activity.getResources().getString(R.string.cardDetail_pleaseImportImage));
                        activity.popupBox.centerConfirmHideBox();
                        activity.popupBox.show();
                    }else {
                        File file = ImageUtils.saveImage(activity, customCard.customImage, customCard.code);
                        activity.cropFile(concatHeadFileName(),Uri.fromFile(file));
                    }
                }
            }
        });
    }
    public void callBackFromImportFile(Intent data) {
        Logger.log(tag,"callBackFromImportFile");
//        String imagePath = copyFile(data.getData(), card.code+"");
//        CustomCard tempCard = findCard(customCard.code, GameContext.gameContext.player.customCards);
//        if(tempCard == null){
//            tempCard = new Card();
//            tempCard.code = customCard.code;
//            GameContext.gameContext.player.customCards.add(tempCard);
//        }
        tempImageBase64 = ImageUtils.convertImageToBase64(activity,data.getData());
//        GameContext.gameContext.savePlayerData(activity);
        bigImage.setImageURI(data.getData());

    }

    private Card findCard(String cardCode, List<Card> cardList){
        for(Card card : cardList) {
            if(card.code != null && card.code.equals(cardCode)){
                return card;
            }
        }
        return null;
    }

    private void loadCardImageAndHeadImage() {
//        String base64 = ImageUtils.convertImageToBase64(card.customImage);
        int resourceId = ImageUtils.convertImageStringToInt(activity, dbCard.image);
        int headResourceId = ImageUtils.convertImageStringToInt(activity,dbCard.head);
        if(customCard.customImage != null && !customCard.customImage.equals("null")) {
            bigImage.setImageBitmap(ImageUtils.convertBase64ToImage(customCard.customImage));
        }else {
            bigImage.setImageResource(resourceId);
        }
        if(customCard.customHead != null && !customCard.customHead.equals("null")) {
            Logger.log(tag, "set-head:"+customCard.customHead);
            headImage.setImageBitmap(ImageUtils.convertBase64ToImage(customCard.customHead));
        }else {
            headImage.setImageResource(headResourceId);
        }
    }

    private String copyFile(Uri sourceuri, String fileName) {

        Logger.log(tag,"start-savefile");
        String sourceFilename= sourceuri.getPath();
        Logger.log(tag,"sourceFilename:"+sourceFilename);
        String destinationFilename = Environment.getExternalStorageDirectory()+ Setting.GAME_FOLDER+fileName+".png";

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
//            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bis = new BufferedInputStream(activity.getContentResolver().openInputStream(sourceuri));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while(bis.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return destinationFilename;
    }

    public void showDetail(boolean show, boolean withAnim){
        if(show) {
            if(withAnim) {
                AnimationUtil.doFadeIn(activity,cardDetailLayout);
            }else {
                cardDetailLayout.setVisibility(VISIBLE);
            }
        }else {
            if(withAnim) {
                AnimationUtil.doFadeOut(activity,cardDetailLayout);
            }else {
                cardDetailLayout.setVisibility(INVISIBLE);
            }
        }
    }

    public void callBackFromCropFile(Uri data) {
        Logger.log(tag,"callBackFromCropFile");
//        String path = Environment.getExternalStorageDirectory()+Setting.GAME_FOLDER+concatHeadFileName();
//        File file = new File(path);
////        Uri uri = Uri.fromFile(file);
//        Bitmap bmp = BitmapFactory.decodeFile(path);
        headImage.setImageURI(data);
        tempHeadBase64 = ImageUtils.convertImageToBase64(activity, data);
//        BaseActivity.savePlayerData(activity);

    }

    private String concatHeadFileName() {
        return dbCard.code+"head.png";
    }
}
