package com.longtail360.autochessrpg.activity;

import android.content.Intent;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONObject;

import com.google.gson.Gson;
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.prefab.PopupBox;
import com.longtail360.autochessrpg.utils.Logger;

/**
 * Created by chanmenlung on 7/10/2018.
 */

public class BaseActivity extends AppCompatActivity {
    private static String tag = "BaseActivity";
//public static String PLAYER_FILE_NAME; test git
    public static JSONObject configJson;
    public static Player player;
    public static Gson gson;
    public PopupBox popupBox;
    protected ViewGroup thisLayout;

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
