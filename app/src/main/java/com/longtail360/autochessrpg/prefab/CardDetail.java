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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.AllCardActivity;
import com.longtail360.autochessrpg.activity.BaseActivity;
import com.longtail360.autochessrpg.activity.ExternalResActivity;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.utils.AnimationUtil;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class CardDetail extends FrameLayout {
    private String tag = "CardDetail";
    private Card card;
    private ImageView bigImage;
    private ImageView headImage;
    private ViewGroup cardDetailLayout;
    public ExternalResActivity activity;
    public CardDescItem cardDescItem;
    private HeadBackButton backBt;
    private View randomImageBt;
    private View importBigImageBt;
    private View cropCardHeadBt;
    private Random random;
    private String tempImagePath;
    private String tempHeadPath;
    private View saveBt;
    private PopupBox box;
    public CardDetail(Context context) {
        super(context);
    }

    public CardDetail(ExternalResActivity activity,Card card) {
        super(activity);
        this.card = card;
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_detail, this);

        cardDetailLayout = findViewById(R.id.cardDetailLayout);
        backBt =findViewById(R.id.backBt);
        bigImage = findViewById(R.id.bigImage);
        headImage = findViewById(R.id.headImage);
        randomImageBt = findViewById(R.id.randomImageBt);
        importBigImageBt = findViewById(R.id.importBigImageBt);
        cropCardHeadBt = findViewById(R.id.cropCardHeadBt);
        saveBt = findViewById(R.id.saveBt);
        random = new Random();
        tempImagePath = card.customImage;
        tempHeadPath = card.customHead;
        box = new PopupBox(activity, 2);
        cardDetailLayout.addView(box);
        setListener();
        loadCardImageAndHeadImage();
    }

    public void loadCard(Card card) {
        this.card = card;
        loadCardImageAndHeadImage();
    }

    private void setListener() {
        backBt.backBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(cardDescItem != null){
//                    cardDescItem.reload(card);
//                }
//                BaseActivity.savePlayerData(activity);
                showDetail(false, true);
            }
        });

        saveBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(card.locked == 1){
                    box.reset(2);
                    box.title.setText(activity.getString(R.string.cardDetail_cardHadLocked));
                    box.content.setText(activity.getString(R.string.cardDetail_isUnlockCard).replace("{crystal}", Setting.CRYSTAL_FOR_UNLOCK_CARD +""));
                    box.show();
                    box.rightConfirm.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(GameContext.gameContext.getPlayer(activity).crystal > Setting.CRYSTAL_FOR_UNLOCK_CARD ){
                                card.locked = 0;
                                GameContext.gameContext.cardDAO.update(card);
                                GameContext.gameContext.getPlayer(activity).crystal = GameContext.gameContext.getPlayer(activity).crystal - Setting.CRYSTAL_FOR_UNLOCK_CARD;
                                GameContext.gameContext.getPlayer(activity).unlockCards.add(card.code);
                                GameContext.gameContext.savePlayerData(activity);
                                box.hide();
                                Logger.toast(activity.getString(R.string.cardDetail_cardHadUnLock), activity);
                            }else {
                                box.reset(2);
                                box.title.setText(activity.getString(R.string.cardDetail_notEnoughCrystal));
                                box.content.setText(activity.getString(R.string.cardDetail_doesGoToBuyCrystal));
                                box.show();
                                box.rightConfirm.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        activity.startOtherActivity(AllCardActivity.class);
                                    }
                                });
                            }
                        }
                    });
                }else {
                    card.customImage = tempImagePath;
                    card.customHead = tempHeadPath;
                    GameContext.gameContext.cardDAO.update(card);
                    Logger.toast(activity.getString(R.string.cardDetail_saved), activity);
                }
            }
        });
        randomImageBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = random.nextInt(7)+1;
                Logger.log(tag, "n:"+n);
                tempImagePath = "c"+n;
                tempHeadPath = tempImagePath+"_head";
                int resourceId = ImageUtils.convertImageStringToInt(activity, tempImagePath);
                int headResourceId = ImageUtils.convertImageStringToInt(activity,tempHeadPath);
                bigImage.setImageResource(resourceId);
                headImage.setImageResource(headResourceId);
            }
        });

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
                if(card.customImage == null) {
                    box.title.setText(activity.getResources().getString(R.string.cardDetail_cannotFindImportImage));
                    box.content.setText(activity.getResources().getString(R.string.cardDetail_pleaseImportImage));
                    box.centerConfirmHideBox();
                    box.show();
                }else {
                    File file = new File(card.customImage);
                    if(!file.exists()) {
                        box.title.setText(activity.getResources().getString(R.string.cardDetail_cannotFindImportImage));
                        box.content.setText(activity.getResources().getString(R.string.cardDetail_pleaseImportImage));
                        box.centerConfirmHideBox();
                        box.show();
                    }else {
                        activity.cropFile(concatHeadFileName(),Uri.fromFile(file));
                    }
                }
            }
        });
    }
    public void callBackFromImportFile(Intent data) {
        Logger.log(tag,"callBackFromImportFile");
        String imagePath = copyFile(data.getData(), card.code+"");
        bigImage.setImageURI(data.getData());
        this.card.customImage = imagePath;
//        BaseActivity.savePlayerData(activity);

    }

    private void loadCardImageAndHeadImage() {
        Logger.log(tag,"card.imageUri"+card.customImage);
        int resourceId = ImageUtils.convertImageStringToInt(activity, card.image);
        int headResourceId = ImageUtils.convertImageStringToInt(activity,card.head);
        if(card.customImage != null) {
            File file = new File(card.customImage);
            if(file.exists()) {
                bigImage.setImageURI(Uri.parse(card.customImage));
            }else {
                bigImage.setImageResource(resourceId);
            }
        }else {
            bigImage.setImageResource(resourceId);
        }
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
        String path = Environment.getExternalStorageDirectory()+Setting.GAME_FOLDER+concatHeadFileName();
        File file = new File(path);
//        Uri uri = Uri.fromFile(file);
        Bitmap bmp = BitmapFactory.decodeFile(path);
        headImage.setImageBitmap(bmp);
        this.card.head = path;
//        BaseActivity.savePlayerData(activity);

    }

    private String concatHeadFileName() {
        return card.code+"head.png";
    }
}
