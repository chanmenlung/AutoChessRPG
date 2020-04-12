package com.longtail360.autochessrpg.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GooglePlayHandler {
    private String tag = "GooglePlayHandler";
    private GoogleSignInClient signInClient;
    public static int intentRequestCode = 1;
    private String savedName = "AutoChessRpgPlayerData";
    public void login(final Activity activity, final LoginCallBack loginCallBack) {
        GoogleSignInOptions signInOptions =       new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                // Add the APPFOLDER scope for Snapshot support.
                .requestScopes(Drive.SCOPE_APPFOLDER)
                .build();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            Logger.log(tag, "hasPermissions");
            loginCallBack.doActionAfterLogin(true);
        } else {
            Logger.log(tag, "try-first-login-in");
            // Haven't been signed-in before. Try the silent sign-in first.
            signInClient = GoogleSignIn.getClient(activity, signInOptions);
            signInClient.silentSignIn()
                    .addOnCompleteListener(
                            activity,
                            new OnCompleteListener<GoogleSignInAccount>() {
                                @Override
                                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                    if (task.isSuccessful()) {
                                        loginCallBack.doActionAfterFirstLogin(true);
                                        // The signed in account is stored in the task's result.
                                    } else {
                                        Logger.log(tag, "start-intent");
                                        startSignInIntent(activity);
//                                        loginCallBack.doActionAfterFirstLogin(false);
                                    }
                                }
                            });

        }
    }

    public void write(final Context context,final String data,final WriteBack writeBack) {
        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(context, GoogleSignIn.getLastSignedInAccount(context));
        snapshotsClient.open(savedName, true)
                .continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
            @Override
            public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                Snapshot snapshot = task.getResult().getData();
                writeSnapshot(context, snapshot, data.getBytes(), "playerData").addOnCompleteListener(new OnCompleteListener<SnapshotMetadata>() {
                    @Override
                    public void onComplete(@NonNull Task<SnapshotMetadata> task) {
                        Logger.log(tag, "write-onComplete");
                        if(writeBack != null) {
                            writeBack.doActionAfterWrite();
                        }else {
                            Logger.log(tag, "writeBack_is_null");
                        }
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

    public void read(final Context context,final ReadBack readBack){
        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(context, GoogleSignIn.getLastSignedInAccount(context));

        // In the case of a conflict, the most recently modified version of this snapshot will be used.
        int conflictResolutionPolicy = SnapshotsClient.RESOLUTION_POLICY_MOST_RECENTLY_MODIFIED;
        String data;
        // Open the saved game using its name.
        snapshotsClient.open(savedName, true, conflictResolutionPolicy)
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
                        String data = new String(task.getResult());
                        readBack.doActionAfterRead(data);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void doActionAfterIntent(Context context, int requestCode, int resultCode, Intent data) {
        signInClient.silentSignIn();

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            // The signed in account is stored in the result.

        } else {
            Logger.log(tag, "sign in fail");
            String message = result.getStatus().getStatusMessage();
            if (message == null || message.isEmpty()) {
                message = result.getStatus().getStatusCode()+"";
            }
            new AlertDialog.Builder(context).setMessage(message)
                    .setNeutralButton(android.R.string.ok, null).show();
        }
    }
    private void startSignInIntent(Activity activity) {
        Log.i(tag, "startSignInIntent");
        GoogleSignInOptions signInOptions =       new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                // Add the APPFOLDER scope for Snapshot support.
                .requestScopes(Drive.SCOPE_APPFOLDER)
                .build();

        GoogleSignInClient signInClient = GoogleSignIn.getClient(activity,signInOptions);
        Intent intent = signInClient.getSignInIntent();
        activity.startActivityForResult(intent, intentRequestCode);
    }

    public void logout(Activity activity) {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(activity,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(activity,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // at this point, the user is signed out.
                    }
                });
    }

    public interface LoginCallBack {
        void doActionAfterFirstLogin(boolean success);
        void doActionAfterLogin(boolean success);
    }

    public interface WriteBack {
        void doActionAfterWrite();
    }

    public interface ReadBack {
        void doActionAfterRead(String data);
    }
}
