package com.longtail360.autochessrpg.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONObject;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.prefab.PopupBox;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by chanmenlung on 7/10/2018.
 */

public class BaseActivity extends AppCompatActivity {
    private static String tag = "BaseActivity";
//public static String PLAYER_FILE_NAME; test git
    public PopupBox popupBox;
    protected ViewGroup thisLayout;
    private String saveName = "AutoChessRPG_PlayerData";

    public void initPopupBox() {
        popupBox = new PopupBox(this, 0);
        thisLayout.addView(popupBox);
    }
    public void startOtherActivity(java.lang.Class<?> cls) {
        Logger.log(tag, "startOtherActivity:");
        Intent logIntent = new Intent(getBaseContext(), cls);
        startActivity(logIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    protected void writeData(final String data) {
        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(this, GoogleSignIn.getLastSignedInAccount(this));
        snapshotsClient.open(saveName, true).continueWith(new Continuation<SnapshotsClient.DataOrConflict<Snapshot>, byte[]>() {
            @Override
            public byte[] then(@NonNull Task<SnapshotsClient.DataOrConflict<Snapshot>> task) throws Exception {
                Snapshot snapshot = task.getResult().getData();
                writeSnapshot(snapshot, data.getBytes(), "playerData");

                return null;
            }
        });
    }

    protected Task<SnapshotMetadata> writeSnapshot(Snapshot snapshot,
                                                 byte[] data,String desc) {

        // Set the data payload for the snapshot
        snapshot.getSnapshotContents().writeBytes(data);

        // Create the change operation
        SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
                .setDescription(desc)
                .build();

        SnapshotsClient snapshotsClient =
                Games.getSnapshotsClient(this, GoogleSignIn.getLastSignedInAccount(this));

        // Commit the operation
        return snapshotsClient.commitAndClose(snapshot, metadataChange);
    }


    protected void onGettingData(String data) {

    }

    protected void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // at this point, the user is signed out.
                        Logger.log(tag, "user is sign out");

                    }
                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
//
//
//    public void startOtherActivityAndFinish(Intent logIntent) {
//        logIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(logIntent);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        finish();
//    }
//
//    public void startOtherActivityAndFinish(java.lang.Class<?> cls) {
//        Intent logIntent = new Intent(getBaseContext(), cls);
//        startOtherActivityAndFinish(logIntent);
//    }
//
//    public static void writeToFile(String fileName, String data, Context context) {
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
//            outputStreamWriter.write(data);
//            outputStreamWriter.close();
//        }
//        catch (IOException e) {
//            Logger.log("File write failed: " + e.toString());
//        }
//    }
//
//    public static String readFromFile(String fileName, Context context) {
//
//        String ret = "";
//
//        try {
//            InputStream inputStream = context.openFileInput(fileName);
//
//            if ( inputStream != null ) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ( (receiveString = bufferedReader.readLine()) != null ) {
//                    stringBuilder.append(receiveString);
//                }
//
//                inputStream.close();
//                ret = stringBuilder.toString();
//            }
//        }
//        catch (FileNotFoundException e) {
//            Logger.log("File not found: " + e.toString());
//        } catch (IOException e) {
//            Logger.log("Can not read file: " + e.toString());
//        }
//
//        return ret;
//    }
//
//    public static boolean localFileExists(String fileName, Context context) {
//        File file = context.getFileStreamPath(fileName);
//        return file.exists();
//    }
//
//    public static void deleteFile(String fileName, Context context) {
//        File file = context.getFileStreamPath(fileName);
//        file.deleteOnExit();
//    }
//
//    public static void savePlayerData(Context context) {
//        String str = gson.toJson(player);
//        Logger.log("player:"+str);
//        writeToFile(PLAYER_FILE_NAME,str, context);
//    }
//
//    public static void readPlayerData(Context context) throws JSONException {
//        String playerStr = readFromFile(PLAYER_FILE_NAME, context);
//        player =  gson.fromJson(playerStr, Player.class);
//    }

}
