package com.imagicapp.imagemetadata;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ImageMetadata {
    private Context context;

    public ImageMetadata(Context context) {
        this.context = context;
    }

    public List<String> getImagesBetweenDates(Date validFrom, Date validTo) {
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
}
