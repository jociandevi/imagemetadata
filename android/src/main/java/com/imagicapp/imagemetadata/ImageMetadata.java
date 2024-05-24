package com.imagicapp.imagemetadata;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class ImageMetadata {
    private Context context;

    public ImageMetadata(Context context) {
        this.context = context;
        FirebaseApp.initializeApp(context);
    }

    public List<String> getImagesBetweenDates(Date validFrom, Date validTo) {
        FirebaseCrashlytics.getInstance().recordException(new Exception("In the ImageMetadata " +
        "class"));
        FirebaseCrashlytics.getInstance().log("Values I got: validFrom=" + validFrom +
                ", validTo=" + validTo);
        FirebaseCrashlytics.getInstance().log("What I will actually use: validFrom=" + validFrom.getTime() + ", validTo=" + validTo.getTime());

        List<String> imagePaths = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN };
        String selection = MediaStore.Images.Media.DATE_TAKEN + " BETWEEN ? AND ?";
        String[] selectionArgs = { String.valueOf(validFrom.getTime()), String.valueOf(validTo.getTime()) };
    
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor == null) {
            FirebaseCrashlytics.getInstance().("Cursor is null");
        } else if (cursor.moveToFirst()) {
            do {
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                long dateTaken = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN));
                FirebaseCrashlytics.getInstance().log("Found image: " + filePath + " with dateTaken=" + dateTaken);
    
                if (dateTaken >= validFrom.getTime() && dateTaken <= validTo.getTime()) {
                    imagePaths.add(filePath);
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            FirebaseCrashlytics.getInstance().log("No images found in the specified range.");
        }
    
        return imagePaths;
    }
    

    public Date getCreationDate(String filePath) {
        Uri uri = Uri.parse(filePath);
        String[] projection = { MediaStore.Images.Media.DATE_TAKEN };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int dateTakenIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
            long dateTaken = cursor.getLong(dateTakenIndex);
            cursor.close();
            return new Date(dateTaken);
        }
        return null;
    }

    public void logCrashlyticsMessage(String message) {
        FirebaseCrashlytics.getInstance().recordException(new Exception("In the logCrashlyticsMessage " +
        "function"));
        FirebaseCrashlytics.getInstance().log(message);
    }
}
