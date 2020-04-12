package com.longtail360.autochessrpg.activity;

import android.app.Activity;

import android.app.Activity;

import android.content.ClipData;
import android.content.ClipDescription;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.utils.GooglePlayHandler;
import com.longtail360.autochessrpg.utils.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HelloActivity extends BaseActivity {
    String tag = "HelloActivity";
    private View loginBt;
    private View writeBt;
    private View readBt;
    private GooglePlayHandler googlePlayHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        loginBt = findViewById(R.id.loginBt);
        writeBt = findViewById(R.id.writeBt);
        readBt = findViewById(R.id.readBt);
        googlePlayHandler = new GooglePlayHandler();
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlePlayHandler.login(HelloActivity.this, new GooglePlayHandler.LoginCallBack() {
                    @Override
                    public void doActionAfterFirstLogin(boolean success) {
                        Logger.log(tag, "success-doActionAfterFirstLogin:"+success);
                    }

                    @Override
                    public void doActionAfterLogin(boolean success) {
                        if(success){
                            Logger.log(tag, "success-login");
                        }
                    }
                });
            }
        });
        writeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlePlayHandler.write(HelloActivity.this, "thisIsData", new GooglePlayHandler.WriteBack() {
                    @Override
                    public void doActionAfterWrite() {
                        Logger.log(tag, "doActionAfterWrite");
                    }
                });
            }
        });

        readBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlePlayHandler.read(HelloActivity.this, new GooglePlayHandler.ReadBack() {
                    @Override
                    public void doActionAfterRead(String data) {
                        Logger.log(tag, "data:"+data);
                    }
                });
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(tag, "onActivityResult");
        if (requestCode == GooglePlayHandler.intentRequestCode) {
            googlePlayHandler.doActionAfterIntent(this,requestCode,requestCode, data);

        }
    }
}
