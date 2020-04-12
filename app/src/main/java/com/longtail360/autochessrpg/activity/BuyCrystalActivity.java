package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.prefab.BuyCrystalDescItem;
import com.longtail360.autochessrpg.prefab.HeadBackButton;
import com.longtail360.autochessrpg.utils.BillingSecurity;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//https://play.google.com/apps/testing/com.longtail360.autorpg/join?hl=zh-TW
public class BuyCrystalActivity  extends BaseActivity implements BuyCrystalDescItem.CallBack, PurchasesUpdatedListener {
    String tag = "BuyCrystalActivity";
    private HeadBackButton backBt;
    private ViewGroup crystalContainer;
    private BillingClient billingClient;
    String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl8kgg" +
            "zwmXeuX3wWlacsPHQhC6LXbk1OKCe+tU4IifEji2evHrwOOTyP2uZ7Aw4hRQ" +
            "MZWB5wMmaHwf3IJr/BzAW9XhhNc11rH58g9dvsgNpgBLRITf6XBadREftNVY" +
            "vSfN9vSEJbSKuuVWk4rn1GPCHMDUOZBdcI39cS97ByrfG9Noe6xY3WmfH3sx" +
            "niyI9XQd5xMRMfud7e9E2owG8jFykkL75JeWEQjyBVNIz6JHWBR2nv+q7qx1" +
            "jVb6MSPWS+1nIGSdUhtiPHzcjeQDkjCYe6y4LvINYPKp1xTDi2nRFSUH7bII" +
            "p9l04c1Y2aQQsMww7GUrWrD++gKsHpGVYORNwIDAQAB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_crystal);
        thisLayout = findViewById(R.id.thisLayout);

        crystalContainer = findViewById(R.id.crystalContainer);

        backBt = findViewById(R.id.backBt);
        backBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        billingClient = BillingClient.newBuilder(BuyCrystalActivity.this).enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {

            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Logger.log(tag, "success, response code:"+billingResult.getResponseCode());
                    List<String> skuList = new ArrayList<>();
                    skuList.add("crystal100");
                    skuList.add("crystal200");
                    skuList.add("crystal500");
                    skuList.add("crystal1000");
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    billingClient.querySkuDetailsAsync(params.build(),
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(BillingResult billingResult,
                                                                 List<SkuDetails> skuDetailsList) {
                                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                        Collections.sort(skuDetailsList, new Comparator<SkuDetails>() {
                                            @Override
                                            public int compare(SkuDetails t1, SkuDetails t2) {
                                                if(t1.getPriceAmountMicros() > t2.getPriceAmountMicros()) {
                                                    return 1;
                                                }else {
                                                    return -1;
                                                }
                                            }
                                        });
                                        for (SkuDetails skuDetails : skuDetailsList) {
                                            String sku = skuDetails.getSku();
                                            String price = skuDetails.getPrice();
                                            Logger.log(tag,"getPriceAmountMicros:"+skuDetails.getPriceAmountMicros());
                                            if ("crystal100".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails, BuyCrystalActivity.this, 100,price));
                                            } else if ("crystal200".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails,BuyCrystalActivity.this, 200,price));
                                            }else if ("crystal500".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails,BuyCrystalActivity.this, 500,price));
                                            }else if ("crystal1000".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails,BuyCrystalActivity.this, 1000,price));
                                            }
                                        }
                                    }
                                }
                            });
                }else {
                    Logger.log(tag,"error, response code:"+billingResult.getResponseCode());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });

    }

    @Override
    public void doActionAfterClickBt(BuyCrystalDescItem focusItem, SkuDetails skuDetails, int crystal) {
//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Buy "+crystal,
//                Toast.LENGTH_SHORT);
//        toast.show();
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        BillingResult result = billingClient.launchBillingFlow(this, flowParams);
    }


    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for(final Purchase pc : purchases) {
                if (!verifyValidSignature(pc.getOriginalJson(), pc.getSignature())) {
                    // Invalid purchase
                    // show error to user
                    Logger.log(tag, "Got a purchase: " + pc + "; but signature is bad. Skipping...");
                    return;
                } else {
                    Logger.log(tag,"Purchase valid");
                    ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(pc.getPurchaseToken()).build();
                    ConsumeResponseListener listener = new ConsumeResponseListener() {

                        @Override
                        public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                Logger.log(tag, pc.getSku()+" is handled");
                                if ("crystal10".equals(pc.getSku())) {
                                    GameContext.gameContext.player.crystal = GameContext.gameContext.player.crystal + 10;
                                } else if ("crystal20".equals(pc.getSku())) {
                                    GameContext.gameContext.player.crystal = GameContext.gameContext.player.crystal + 20;
                                }else if ("crystal50".equals(pc.getSku())) {
                                    GameContext.gameContext.player.crystal = GameContext.gameContext.player.crystal + 50;
                                }else if ("crystal100".equals(pc.getSku())) {
                                    GameContext.gameContext.player.crystal = GameContext.gameContext.player.crystal + 100;
                                }else if ("crystal200".equals(pc.getSku())) {
                                    GameContext.gameContext.player.crystal = GameContext.gameContext.player.crystal + 200;
                                }
                                GameContext.gameContext.playerDAO.update(GameContext.gameContext.player);
                            }
                        }
                    };
                    billingClient.consumeAsync(consumeParams, listener);
                }
            }
        }
    }


    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            return BillingSecurity.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            Logger.log(tag, "Got an exception trying to validate a purchase: " + e);
            return false;
        }
    }
}
