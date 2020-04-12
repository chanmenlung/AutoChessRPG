package com.longtail360.autochessrpg.activity;

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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GooglePlayActivity extends BaseActivity {
    String tag = "GooglePlayActivity";
    private View loginBt;
    private View writeBt;
    private View readBt;
    private GoogleSignInClient signInClient;
    private String saveName = "AutoChessRPG_PlayerData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_play);
        loginBt = findViewById(R.id.loginBt);
        writeBt = findViewById(R.id.writeBt);
        readBt = findViewById(R.id.readBt);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInSilently();
            }
        });
        writeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeData("thisIsData");
            }
        });

        readBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSnapshot();
            }
        });
    }

    protected Task<byte[]> loadSnapshot() {
        // Get the SnapshotsClient from the signed in account.
        Logger.log(tag, "loadSnapshot");
        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(this, GoogleSignIn.getLastSignedInAccount(this));

        // In the case of a conflict, the most recently modified version of this snapshot will be used.
        int conflictResolutionPolicy = SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED;

        // Open the saved game using its name.
        return snapshotsClient.open(saveName, true, conflictResolutionPolicy)
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
                            String a = new String(snapshot.getSnapshotContents().readFully(), StandardCharsets.UTF_8);
                            Logger.log(tag, "a:"+a);
                            onGettingData(a);
                            return null;
                        } catch (IOException e) {
                            Log.e(tag, "Error while reading Snapshot.", e);
                        }

                        return null;
                    }
                }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                    @Override
                    public void onComplete(@NonNull Task<byte[]> task) {
                        // Dismiss progress dialog and reflect the changes in the UI when complete.
                        // ...
                    }
                });
    }

    private void signInSilently() {
        GoogleSignInOptions signInOption =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                        // Add the APPFOLDER scope for Snapshot support.
                        .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, signInOption.getScopeArray())) {
            Log.d(tag, "already-has-account");
//            signInClient = GoogleSignIn.getClient(this, signInOptions);
            signInClient = GoogleSignIn.getClient(this, signInOption);
            signInClient
                    .silentSignIn()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<GoogleSignInAccount>() {
                                @Override
                                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {

                                    if (task.isSuccessful()) {
                                        Log.d(tag, "success-create-account");
                                        // The signed in account is stored in the task's result.
                                        GoogleSignInAccount signedInAccount = task.getResult();
                                        Log.d(tag, "success");
                                    } else {
                                        startSignInIntent();
//                                        onConnected(task.getResult());
                                    }
                                }
                            });

        } else {
            Log.i(tag, "try-first-login-in-11111");
            // Haven't been signed-in before. Try the silent sign-in first.
            signInClient = GoogleSignIn.getClient(this, signInOption);
            signInClient
                    .silentSignIn()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<GoogleSignInAccount>() {
                                @Override
                                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {

                                    if (task.isSuccessful()) {
                                        Log.d(tag, "success-create-account");
                                        // The signed in account is stored in the task's result.
                                        GoogleSignInAccount signedInAccount = task.getResult();
                                        Log.d(tag, "success");
                                    } else {
                                        Log.d(tag, "startSignInIntent");
                                        startSignInIntent();
//                                        onConnected(task.getResult());
                                    }
                                }
                            });

        }
    }

    private void startSignInIntent() {
        Log.i(tag, "startSignInIntent");
        GoogleSignInOptions signInOption =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                        // Add the APPFOLDER scope for Snapshot support.
                        .requestScopes(Drive.SCOPE_APPFOLDER)
                        .build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                signInOption);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(tag, "onActivityResult");
        if (requestCode == 1) {
            signInClient.silentSignIn();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
                Log.d(tag, "sign in success");
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
}
