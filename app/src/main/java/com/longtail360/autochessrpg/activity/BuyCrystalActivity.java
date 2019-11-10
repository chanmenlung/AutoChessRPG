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
    String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAljYSOBMEBdqJR301Htk" +
            "1vA4akRp3Au1s9B1udkmnPkqV35EvPZIVfqXd7gKUeNDceTuSwHOhTnRFgCrV6Rc5U9uyboJTC" +
            "ut3ye7oYS4nYpqULlKVYn1O9y+hNUxteaZUnZWViDv5rwUpO3Dfwb/oE9xpIH15rDR2l6LX1Ic" +
            "+eqHOR4g7J0ArkcQTqmao3J4PilY+d1Dj7NHvsC9FdXfPuob1uConjjsHC0+iPbXKhKxWehBX6" +
            "1NP8iFdGpOgE5IatbtNIPm+7EmxkMWOa0oU6hwQkimt3aK28BSPKdV8ED2YMZzv5PMoWK1u4dj" +
            "JW3L2R9TzgHo08bxnZAwwWQu9owIDAQAB";
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
                startOtherActivity(AllCardActivity.class);
            }
        });

        billingClient = BillingClient.newBuilder(BuyCrystalActivity.this).enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {

            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Logger.log(tag, "success, response code:"+billingResult.getResponseCode());
                    List<String> skuList = new ArrayList<>();
                    skuList.add("crystal10");
                    skuList.add("crystal20");
                    skuList.add("crystal50");
                    skuList.add("crystal100");
                    skuList.add("crystal200");
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
                                            if ("crystal10".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails, BuyCrystalActivity.this, 10,price));
                                            } else if ("crystal20".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails,BuyCrystalActivity.this, 20,price));
                                            }else if ("crystal50".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails,BuyCrystalActivity.this, 50,price));
                                            }else if ("crystal100".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails,BuyCrystalActivity.this, 100,price));
                                            }else if ("crystal200".equals(sku)) {
                                                crystalContainer.addView(new BuyCrystalDescItem(BuyCrystalActivity.this, skuDetails,BuyCrystalActivity.this, 200,price));
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
                                    player.crystal = player.crystal + 10;
                                } else if ("crystal20".equals(pc.getSku())) {
                                    player.crystal = player.crystal + 20;
                                }else if ("crystal50".equals(pc.getSku())) {
                                    player.crystal = player.crystal + 50;
                                }else if ("crystal100".equals(pc.getSku())) {
                                    player.crystal = player.crystal + 100;
                                }else if ("crystal200".equals(pc.getSku())) {
                                    player.crystal = player.crystal + 200;
                                }
                                GameContext.gameContext.savePlayerData(BuyCrystalActivity.this);
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
