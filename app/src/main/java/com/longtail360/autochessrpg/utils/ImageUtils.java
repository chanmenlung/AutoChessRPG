package com.longtail360.autochessrpg.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import com.longtail360.autochessrpg.entity.Setting;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    private static String tag = "ImageUtils";
    static public int convertImageStringToInt(Context context, String s){
        int headResourceId = context.getResources().getIdentifier(s, "drawable",
                context.getPackageName());
        return headResourceId;
    }

    static public Bitmap convertBase64ToImage(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    static public  String convertImageToBase64(Context context, Uri uri) {

        InputStream imageStream = null;
        try {
            imageStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        return encodeImage(selectedImage);
    }

    private static String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static File saveImage(final Context context, final String imageData, String cardCode) {
        final byte[] imgBytesData = android.util.Base64.decode(imageData,
                android.util.Base64.DEFAULT);
//        String path = context.getExternalFilesDir("cardCode")+cardCode;
//        Logger.log(tag, "path:"+path);
        File file = context.getExternalFilesDir("customCards");
        Logger.log(tag, "path:"+file.getPath());
        file = new File(file.getPath()+cardCode);
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
