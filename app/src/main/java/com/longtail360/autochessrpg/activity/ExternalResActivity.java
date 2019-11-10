package com.longtail360.autochessrpg.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.prefab.CardDetail;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;

public class ExternalResActivity extends BaseActivity {
    private String tag = "ExternalResActivity";
    public static final int READ_WRITE_EXTERNAL_FILE = 1;
    public static int IMPORT_FILE_REQUEST_CODE = 2;
    public static int CROP_FILE_REQUEST_CODE = 3;
    private Uri cropedFile;
    protected CardDetail cardDetail;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_WRITE_EXTERNAL_FILE: {
                //if success grant open file browser
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doActionAfterGrantReadExternalRes();
                } else {// If request is cancelled, the result arrays are empty.
//                    doActionAfterCancelGrantReadExternalRes();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.log(tag, "requestCode:" + requestCode);
        Logger.log(tag, "resultCode:" + resultCode);
        if (requestCode == IMPORT_FILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                doActionAfterImportFile(data);
            }
        } else if (requestCode == CROP_FILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                Log.i("teamLog", "crop-success");
                doActionAfterCropFile(cropedFile);

            } else {

            }
        }

    }

    public void readExternalRes() {  //call by card detail
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {  //if no permission, ask for grant permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    ExternalResActivity.READ_WRITE_EXTERNAL_FILE);
        }else {  //if has permession, open file Browser
            openFileBrowser();
        }
    }

    public void cropFile(String fileName, Uri data) { //call by card detail
        cropedFile = Uri.fromFile(getCroppedTempFile(fileName));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra("output", cropedFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_FILE_REQUEST_CODE);
    }
    private File getCroppedTempFile(String fileName) {
        if (isSdcardMounted()) {
            return new File(Environment.getExternalStorageDirectory()+ Setting.GAME_FOLDER+fileName);
        }
        return null;
    }
    protected boolean isSdcardMounted() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    protected void openFileBrowser() {
//        String[] mimeTypes = {"image/jpeg", "image/png"};
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, ExternalResActivity.IMPORT_FILE_REQUEST_CODE);
    }
//    protected void doActionAfterImportFile(Intent data) {}
//    protected void doActionAfterCropFile(Uri data) {}
//    protected void doActionAfterGrantReadExternalRes() {}

    protected void doActionAfterImportFile(Intent data) {
        cardDetail.callBackFromImportFile(data);
    }

    protected void doActionAfterGrantReadExternalRes() {
        openFileBrowser();
    }

    protected void doActionAfterCropFile(Uri data) {
        cardDetail.callBackFromCropFile(data);
    }

    @Override
    protected void onDestroy (){
        Logger.log(tag,"destroy activity");
        if(cardDetail != null){
            cardDetail.activity = null;
        }
        cardDetail = null;
        System.gc();
        super.onDestroy();
    }
}