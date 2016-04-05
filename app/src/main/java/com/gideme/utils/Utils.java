package com.gideme.utils;

import com.gideme.entities.utils.CoupleParams;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oscargallon on 4/4/16.
 */
public class Utils {

    public static String organizePostServicesParametres(List<CoupleParams> couplePostParams) {

        if (couplePostParams == null)
            return null;

        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            for (CoupleParams coupleParams : couplePostParams) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(coupleParams.getParam(), "UTF-8"));
                result.append("=");

                result.append(URLEncoder.encode(coupleParams.getKey(), "UTF-8"));

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    public static List<String> getImagesFromArray(String s) {
        List<String> images = null;
        s = s.replace("[", "");
        s = s.replace("]", "");
        String[] imagesString = s.split(",");
        if (imagesString != null) {
            images = Arrays.asList(imagesString);

        }


        return images;
    }

    public static String encodeBitMapToBase64(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            return base64;
        } catch (Exception e) {
            return null;
        }

    }

    public static Bitmap getBitmapFromBase64(String base) {

        try {
            base = base.replace("\\n", "");
            byte[] decodedString = Base64.decode(base, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        } catch (Exception e) {
            return null;
        }


    }

    public static void sendEmailIntent(Activity activity, String email, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        activity.startActivity(Intent.createChooser(intent, "Send Email"));

    }

    public static List<CoupleParams> getSendOneObjectCouplePostParamList(Serializable object
            , String key) {
        List<CoupleParams> couplePostParamList = new ArrayList<>();
        CoupleParams couplePostParam = new CoupleParams();
        couplePostParam.setKey(key);
        couplePostParam.setObject(object);
        couplePostParamList.add(couplePostParam);
        return couplePostParamList;

    }

    public static String getDateFromPicker(int year, int month, int day) {
        String date = Integer.toString(year);
        month += 1;
        if (month < 10) {
            date += "/" + "0" + Integer.toString(month);
        } else {
            date += "/" + Integer.toString(month);
        }
        if (day < 10) {
            date += "/" + "0" + Integer.toString(day);
        } else {
            date += "/" + Integer.toString(day);
        }
        return date;
    }

    public static String getHourFromPicker(int currentHour, int currentMinutes) {
        String returningHour = "";
        if (Integer.toString(currentMinutes).length() == 1) {
            returningHour = "0" + Integer.toString(currentHour);
        } else {
            returningHour = Integer.toString(currentHour);
        }
        returningHour += ":";
        if (Integer.toString(currentMinutes).length() == 1) {
            returningHour += "0" + Integer.toString(currentMinutes);
        } else {
            returningHour += Integer.toString(currentMinutes);
        }
        return returningHour;
    }

    public static ContentValues getContentValuesFromCouplePostParam(List<CoupleParams> couplePostParamsList) {
        ContentValues contentValues = new ContentValues();

        for (CoupleParams couplePostParam : couplePostParamsList) {
            contentValues.put(couplePostParam.getKey(), couplePostParam.getParam());
        }
        return contentValues;
    }

    public static void performFileSearch(View view, Activity activity, int READ_REQUEST_CODE) {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's
        // file
        // browser.
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        activity.startActivityForResult(i, READ_REQUEST_CODE);
    }

    public static Bitmap decodeUri(Uri selectedImage, Activity activity) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                activity.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                activity.getContentResolver().openInputStream(selectedImage), null, o2);
    }

    public static void CallIntent(Activity activity, String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        activity.startActivity(callIntent);
    }


    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isInternetAvailable() {
        try {

            InetAddress ipAddr = InetAddress.getByName("https://www.google.com");

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }
}
