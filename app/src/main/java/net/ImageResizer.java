package net;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;

public class ImageResizer {
    public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight, boolean isLow) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        if (isLow) {
            options.inPreferredConfig = Config.RGB_565;
        }
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
            for (long totalPixels = (long) ((width * height) / inSampleSize); totalPixels > ((long) ((reqWidth * reqHeight) * 2)); totalPixels /= 2) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static int[] getDisplayDimensions(Activity activity) {
        int[] dimensions = new int[2];
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        dimensions[0] = metrics.widthPixels;
        dimensions[1] = metrics.heightPixels;
        return dimensions;
    }
}
