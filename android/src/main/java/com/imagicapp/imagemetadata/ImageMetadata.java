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
        FirebaseCrashlytics.recordException({
            message: "Data from the ImageMetadata class"
          });
        FirebaseCrashlytics.getInstance().log("Fetching images between dates: validFrom=" + validFrom.getTime() + ", validTo=" + validTo.getTime());

        List<String> imagePaths = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN };
        String selection = MediaStore.Images.Media.DATE_TAKEN + " BETWEEN ? AND ?";
        String[] selectionArgs = { String.valueOf(validFrom.getTime()), String.valueOf(validTo.getTime()) };
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);

        if (cursor != null) {
            int dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int dateTakenIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
            while (cursor.moveToNext()) {
                String filePath = cursor.getString(dataIndex);
                long dateTaken = cursor.getLong(dateTakenIndex);
                FirebaseCrashlytics.getInstance().log("Checking image: " + filePath + " with dateTaken=" + dateTaken);

                if (dateTaken >= validFrom.getTime() && dateTaken <= validTo.getTime()) {
                    imagePaths.add(filePath);
                }
            }
            cursor.close();
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
        FirebaseCrashlytics.recordException({
            message: "Calling the logCrashlyticsMessage function to actually see it"
          });
        FirebaseCrashlytics.getInstance().log(message);
    }
}
