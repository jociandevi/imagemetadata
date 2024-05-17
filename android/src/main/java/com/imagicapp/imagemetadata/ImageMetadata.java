package com.imagicapp.imagemetadata;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.Date;

public class ImageMetadata {
    private Context context;

    public ImageMetadata(Context context) {
        this.context = context;
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
