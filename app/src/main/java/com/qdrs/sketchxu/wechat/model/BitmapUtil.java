package com.qdrs.sketchxu.wechat.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtil {

    private static final String TAG = "BitmapUtil";

    /**
     * 质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度，来达到压缩图片的目的，
     * 图片的长，宽，像素都不会改变，那么bitmap所占内存大小是不会变的。
     * <p>
     * 我们可以看到有个参数：quality，可以调节你压缩的比例，但是还要注意一点就是，质量压缩堆png格式
     * 这种图片没有作用，因为png是无损压缩。
     */
    public static Bitmap compressQuality(Bitmap bitmap) {
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        String mSrcSize = bitmap.getByteCount() + "byte";

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytes = bos.toByteArray();
        Bitmap result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        String mRetSize = result.getByteCount() + "byte";
        Log.d(TAG, "compressQuality: from " + mSrcSize + " to " + mRetSize);
        return result;
    }

    /**
     * 采样率压缩其原理其实也是缩放bitamp的尺寸，通过调节其inSampleSize参数，比如调节为2，宽高会
     * 为原来的1/2，内存变回原来的1/4.
     */
    public static Bitmap compressSampling(Resources resource, int resId, int reqWidth, int reqHeight) {
        Bitmap bitmap = BitmapFactory.decodeResource(resource, resId);
        String mSrcSize = bitmap.getByteCount() + "byte";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        Bitmap result = BitmapFactory.decodeResource(resource, resId, options);

        String mRetSize = result.getByteCount() + "byte";
        Log.d(TAG, "compressSampling: from " + mSrcSize + " to " + mRetSize);
        return result;
    }



    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        Log.d(TAG, "originalWidth:" + originalWidth + " originalHeight:" + originalHeight);

        Log.d(TAG, "reqWidth:" + reqWidth + " reqHeight:" + reqHeight);
        int inSampleSize = 1;

        if (reqWidth == 0 && reqHeight != 0) {
            if (originalHeight > reqHeight){
                int halfHeight = originalHeight / 2;
                //压缩后的尺寸与所需的尺寸进行比较
                while ((halfHeight /inSampleSize)>=reqHeight){
                    inSampleSize *= 2;
                }

            }
        } else if (reqWidth != 0 && reqHeight == 0) {
            if (originalWidth > reqWidth){
                int halfWidth = originalWidth / 2;
                //压缩后的尺寸与所需的尺寸进行比较
                while ((halfWidth / inSampleSize) >= reqWidth){
                    inSampleSize *= 2;
                }
            }
        } else {
            if (originalHeight > reqHeight || originalWidth > reqHeight){
                int halfHeight = originalHeight / 2;
                int halfWidth = originalWidth / 2;
                //压缩后的尺寸与所需的尺寸进行比较
                while ((halfWidth / inSampleSize) >= reqHeight && (halfHeight /inSampleSize)>=reqWidth){
                    inSampleSize *= 2;
                }

            }
        }

        Log.d(TAG, "calculateInSampleSize: inSampleSize = " + inSampleSize);
        return inSampleSize;
    }

    /**
     * 放缩法压缩使用的是通过矩阵对图片进行裁剪，也是通过缩放图片尺寸，来达到压缩图片的效果，和采样率的原理一样。
     */
    public static Bitmap compressMatrix(Bitmap bitmap, float scaleX, float scaleY) {
        Log.d(TAG, "compressMatrix: bitmap.width = " + bitmap.getWidth() + " bitmap.height = " + bitmap.getHeight());
        String mSrcSize = bitmap.getByteCount() + "byte";

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        String mRetSize = result.getByteCount() + "byte";
        Log.d(TAG, "compressMatrix: from " + mSrcSize + " to " + mRetSize);
        return result;
    }

    /**
     * RGB_565压缩
     * 这是通过压缩像素占用的内存来达到压缩的效果，一般不建议使用ARGB_4444，因为画质实在是辣鸡，如果对
     * 透明度没有要求，建议可以改成RGB_565，相比ARGB_8888将节省一半的内存开销。
     */
    public static Bitmap compressRGB565(Resources resource, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(resource, resId);
        String mSrcSize = bitmap.getByteCount() + "byte";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap result = BitmapFactory.decodeResource(resource, resId, options);

        String mRetSize = result.getByteCount() + "byte";
        Log.d(TAG, "compressRGB565: from " + mSrcSize + " to " + mRetSize);
        return result;
    }

    /**
     * 将图片的大小压缩成用户的期望大小，来减少占用内存。
     */
    public static Bitmap compressScaleBitmap(Bitmap bitmap, int width, int height) {
        String mSrcSize = bitmap.getByteCount() + "byte";

        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        Bitmap result = Bitmap.createScaledBitmap(bitmap, width, height, true);

        String mRetSize = result.getByteCount() + "byte";
        Log.d(TAG, "compressScaleBitmap: from " + mSrcSize + " to " + mRetSize);
        return result;
    }

    public static byte[] toByteArray(InputStream input) throws IOException
    {
        byte[] byt = new byte[input.available()];
        input.read(byt);

        return byt;
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return baos.toByteArray();
    }

    public static Bitmap getBitmapFromUrl(String urlpath) {
        Bitmap map = null;
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlpath);
            conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    conn.disconnect();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }


}
