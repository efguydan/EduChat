package com.vggbudge.educhat.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtils {
    public static String TAG = ImageUtils.class.getSimpleName();

    public static int getDownsampleScaleForFile(File file) {

        long kbSize = file.length() / 1024;
        if (kbSize > (6 * 1024)) {
            return 16; // greater than 6mb
        } else if (kbSize > (4 * 1024)) {
            return 8; // between 6mb and 4mb
        } else if (kbSize > (1024)) {
            return 4; // between 4mb and 1mb
        } else if (kbSize > (0.5 * 1024)) {
            return 2;
        } else {
            return 1; // less than 1mb
        }
    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static byte[] getScaledBitmapAsByteArray(int scaleFactor, File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap getScaledBitmap(int scaleFactor, File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
    }

    public static byte[] convertUriToBinary(Uri uri, Context context) {
        byte[] imageBinary;
        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteArrayOutputStream.size();
        imageBinary = byteArrayOutputStream.toByteArray();
        return imageBinary;
    }

    public static byte[] bitmapToBinary(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Uri bitmapToUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Image", "");
        return Uri.parse(path);
    }

    /**
     * Credit = https://stackoverflow.com/q/34445254/2690696
     *
     * @param uri
     * @param contentResolver
     * @return
     */
    public String getImagePath(Uri uri, ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        cursor.moveToFirst();
        String documentID = cursor.getString(0);
        documentID = documentID.substring(documentID.lastIndexOf(":") + 1);
        cursor.close();
        cursor = contentResolver.query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{documentID}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    /**
     * @param imagePath
     * @param limit
     * @return
     */
    public boolean isSizeGreaterThanLimit(String imagePath, int limit) {
        File file = new File(imagePath);
        long length = file.length();
        return length >= limit;
    }
}
