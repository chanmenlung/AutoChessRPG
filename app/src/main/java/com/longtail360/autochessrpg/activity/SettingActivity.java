package com.longtail360.autochessrpg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.CloudPlayerData;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.prefab.HeadBackButton;
import com.longtail360.autochessrpg.utils.GooglePlayHandler;
import com.longtail360.autochessrpg.utils.Logger;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingActivity extends BaseActivity{
    private String tag = "SettingActivity";
    private int googlePlayRequestCode = 1;
    private View uploadBt;
    private View downloadBt;
    private GoogleSignInClient signInClient;
    public Gson gson;
    private HeadBackButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        thisLayout = findViewById(R.id.thisLayout);
        uploadBt = findViewById(R.id.uploadBt);
        downloadBt = findViewById(R.id.downloadBt);
        initPopupBox();
        gson  = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        final GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                // Add the APPFOLDER scope for Snapshot support.
                .requestScopes(Drive.SCOPE_APPFOLDER)
                .build();
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        backButton = findViewById(R.id.backBt);
        backButton.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        uploadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.title.setText(getString(R.string.ui_setting_isUploadData));
                popupBox.content.setText(getString(R.string.ui_setting_uploadDataRemark));
                popupBox.reset(2);
                popupBox.rightConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupBox.title.setText(getString(R.string.ui_setting_uploading));
                        popupBox.content.setText(getString(R.string.ui_setting_pleaseWait));
                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                            writePlayerDataToGoogle();
                        } else {
                            // Haven't been signed-in before. Try the silent sign-in first.
                            signInClient = GoogleSignIn.getClient(SettingActivity.this, signInOptions);
                            signInClient.silentSignIn()
                                    .addOnCompleteListener(
                                            SettingActivity.this,
                                            new OnCompleteListener<GoogleSignInAccount>() {
                                                @Override
                                                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                                    if (task.isSuccessful()) {
                                                        writePlayerDataToGoogle();
                                                    } else {
                                                        Logger.log(tag, "start-intent");
                                                        GoogleSignInOptions signInOptions =       new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                                                                // Add the APPFOLDER scope for Snapshot support.
                                                                .requestScopes(Drive.SCOPE_APPFOLDER)
                                                                .build();

                                                        GoogleSignInClient signInClient = GoogleSignIn.getClient(SettingActivity.this,signInOptions);
                                                        Intent intent = signInClient.getSignInIntent();
                                                        startActivityForResult(intent, googlePlayRequestCode);
                                                    }
                                                }
                                            });

                        }
                    }
                });
                popupBox.show();
            }
        });
        downloadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.title.setText(getString(R.string.ui_setting_isUploadData));
                popupBox.content.setText(getString(R.string.ui_setting_uploadDataRemark));
                popupBox.reset(2);
                popupBox.rightConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupBox.title.setText(getString(R.string.ui_setting_downloading));
                        popupBox.content.setText(getString(R.string.ui_setting_pleaseWait));
                        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
                            readPlayerDataFromGoogle();
                        } else {
                            // Haven't been signed-in before. Try the silent sign-in first.
                            signInClient = GoogleSignIn.getClient(SettingActivity.this, signInOptions);
                            signInClient.silentSignIn()
                                    .addOnCompleteListener(
                                            SettingActivity.this,
                                            new OnCompleteListener<GoogleSignInAccount>() {
                                                @Override
                                                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                                    if (task.isSuccessful()) { //success login in
                                                        readPlayerDataFromGoogle();
                                                        // The signed in account is stored in the task's result.
                                                    } else {
                                                        Logger.log(tag, "start-intent");
                                                        Logger.toast(getString(R.string.ui_setting_cannotFindData), getBaseContext());
                                                    }
                                                }
                                            });

                        }
                    }
                });
                popupBox.show();
            }
        });
    }

    public void write(final Context context, final String fileName, final byte[] data) {
        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(context, GoogleSignIn.getLastSignedInAccount(context));
        snapshotsClient.open(fileName, true)
                .continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
                    @Override
                    public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                        Snapshot snapshot = task.getResult().getData();
                        writeSnapshot(context, snapshot, data, fileName).addOnCompleteListener(new OnCompleteListener<SnapshotMetadata>() {
                            @Override
                            public void onComplete(@NonNull Task<SnapshotMetadata> task) {
                                popupBox.hide();
                                Logger.toast(getString(R.string.ui_setting_successWrite), getBaseContext());
                            }
                        });

                        return null;
                    }
                });
    }

    protected Task<SnapshotMetadata> writeSnapshot(Context context, Snapshot snapshot,
                                                   byte[] data, String desc) {

        // Set the data payload for the snapshot
        snapshot.getSnapshotContents().writeBytes(data);

        // Create the change operation
        SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                .setDescription(desc)
                .build();

        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(context, GoogleSignIn.getLastSignedInAccount(context));

        // Commit the operation
        return snapshotsClient.commitAndClose(snapshot, metadataChange);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(tag, "onActivityResult");
        if (requestCode == googlePlayRequestCode) {
            signInClient.silentSignIn();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                writePlayerDataToGoogle();
            } else {
                Log.d(tag, "sign in fail");
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = result.getStatus().getStatusCode()+"";
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }

    public void read(final Context context, String fileName, final ReadBack readBack){
        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(context, GoogleSignIn.getLastSignedInAccount(context));

        // In the case of a conflict, the most recently modified version of this snapshot will be used.
        int conflictResolutionPolicy = SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED;
        // Open the saved game using its name.
        snapshotsClient.open(fileName, true, conflictResolutionPolicy)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(tag, "Error while opening Snapshot.", e);
                    }
                }).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
            @Override
            public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                Snapshot snapshot = task.getResult().getData();

                // Opening the snapshot was a success and any conflicts have been resolved.
                try {
                    // Extract the raw data from the snapshot.
//                            String data = new String(snapshot.getSnapshotContents().readFully(), StandardCharsets.UTF_8);
//                            Logger.log(tag, "data:"+data);
                    return snapshot.getSnapshotContents().readFully();
                } catch (IOException e) {
                    Log.e(tag, "Error while reading Snapshot.", e);
                }

                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                popupBox.hide();
                byte[] data = task.getResult();
                if(data != null){
                    readBack.doActionAfterRead(task.getResult());
                    Logger.toast(getString(R.string.ui_setting_successRead), SettingActivity.this);
                }else {
                    Logger.toast(getString(R.string.ui_setting_cannotFindData), getBaseContext());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void writePlayerDataToGoogle() {
        CloudPlayerData data = new CloudPlayerData(GameContext.gameContext.player);
        data.customCards = GameContext.gameContext.customCardDAO.getAll();
        Logger.log(tag, "customCard-size:"+data.customCards.size());
        write(SettingActivity.this, "playerData", SerializationUtils.serialize(data));
    }

    private void readPlayerDataFromGoogle() {
        GameContext.gameContext.playerDAO.deleteAll();
        read(this, "playerData", new ReadBack() {
            @Override
            public void doActionAfterRead(byte[] data) {
                GameContext.gameContext.playerDAO.deleteAll();
                CloudPlayerData cloudPlayerData = SerializationUtils.deserialize(data);
                GameContext.gameContext.player.crystal = cloudPlayerData.crystal;
                GameContext.gameContext.player.tacticsJson = cloudPlayerData.tacticsJson;
                Logger.log(tag, "crystal:"+GameContext.gameContext.player.crystal);
                GameContext.gameContext.player.tacticsList = new ArrayList<>(Arrays.asList(GameContext.gameContext.gson.fromJson(GameContext.gameContext.player.tacticsJson, Tactics[].class)));
                GameContext.gameContext.playerDAO.insert(GameContext.gameContext.player);
                GameContext.gameContext.player.concreteConds(SettingActivity.this);
                GameContext.gameContext.player.concreteAction(SettingActivity.this);
                GameContext.gameContext.customCardDAO.deleteAll();
                for(CustomCard customCard : cloudPlayerData.customCards){
                    GameContext.gameContext.customCardDAO.insert(customCard);
                }
            }
        });
    }

    public interface ReadBack {
        void doActionAfterRead(byte[] data);
    }
}
