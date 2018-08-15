package com.lingxi.preciousmetal.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhangwei on 2018/3/26.
 */

public class ImageUtils {
    private final static String TAG = ImageUtils.class.getSimpleName();

    /**
     * 根据原图,取原图长宽最短一边绘制圆形图片
     *
     * @param source
     * @return
     */
    public static Bitmap getCircleImage(Bitmap source) {
        // TODO: 2016/6/18 leak memory
        if (source != null) {
            int rad = (source.getWidth() > source.getHeight()) ?
                    source.getHeight() : source.getWidth();
            return getCircleImage(source, rad);
        } else {
            return null;
        }
    }

    /**
     * 根据原图和变长绘制圆形图片
     * 其实主要靠：paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
     * 这行代码，为什么呢，我给大家解释下，SRC_IN这种模式，两个绘制的效果叠加后取交集展现后图，
     * 怎么说呢，咱们第一个绘制的是个圆形，第二个绘制的是个Bitmap，于是交集为圆形，展现的是BItmap，
     * 就实现了圆形图片效果。
     *
     * @param source
     * @param min
     * @return
     */
    public static Bitmap getCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 圆角图片
     *
     * @param source 原始图片
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source) {
        int min = Math.min(source.getWidth(), source.getHeight());
        return getFilletImage(source, min, min);
    }

    /**
     * 圆角图片
     *
     * @param source 原始图片
     * @param width  宽
     * @param height 高
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source, int width, int height) {
        return getFilletImage(source, width, height, 10f, 10f);
    }

    /**
     * 圆角图片
     *
     * @param source 原始图片
     * @param rx     x半径
     * @param ry     y半径
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source, float rx, float ry) {
        int min = Math.min(source.getWidth(), source.getHeight());
        return getFilletImage(source, min, min, rx, ry);
    }

    /**
     * 圆角图片
     *
     * @param source 原始图片
     * @param width  宽
     * @param height 高
     * @param rx     x半径
     * @param ry     y半径
     * @return
     */
    public static Bitmap getFilletImage(Bitmap source, int width, int height, float rx, float ry) {
        Paint paint = new Paint();
        Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF();
        rect.bottom = height;
        rect.top = 0;
        rect.left = 0;
        rect.right = width;
        canvas.drawRoundRect(rect, rx, ry, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    public static Bitmap rotate(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
        return bitmap;
    }

    private static ExifInterface getExif(String path) {
        try {
            return new ExifInterface(path);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * 缩放图片
     *
     * @param bitmap    源位图
     * @param maxWidth  最大宽
     * @param maxHeight 最大高
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float sw = maxWidth * 1f / width;
        float sh = maxHeight * 1f / height;
        int outW, outH;
        if (sw < sh) {
            outW = (int) (sw * width);
            outH = (int) (sw * height);
        } else {
            outW = (int) (sh * width);
            outH = (int) (sh * height);
        }
        return Bitmap.createScaledBitmap(bitmap, outW, outH, false);
    }

    /**
     * 压缩图片，默认格式为jpg
     *
     * @param srcPath    源图片路径
     * @param quality    图片质量（0-100）,100为原图质量
     * @param targetPath 输出压缩后图片的文件路径
     * @throws java.io.IOException
     */
    public static void compress(String srcPath, int quality, String targetPath, int compressQualityKb) throws IOException {
        compressEx(srcPath, quality, Integer.MAX_VALUE, Integer.MAX_VALUE, targetPath, compressQualityKb);
    }

    /**
     * 压缩图片，默认格式为jpg，若图片大于最大宽或高，将按比例缩小
     *
     * @param srcPath    源图片路径
     * @param quality    图片质量（0-100）,100为原图质量
     * @param maxWidth   最大宽（将自动识别，数值大的对应长边）
     * @param maxHeight  最大高（将自动识别，数值大的对应长边）
     * @param targetPath 输出压缩后图片的文件路径
     * @throws java.io.IOException
     */
    public static void compress(String srcPath, int quality, int maxWidth, int maxHeight, String targetPath) throws IOException {
        //大小压缩-------------------------
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        int scaleW, scaleH;
        if (options.outWidth > options.outHeight) {
            int maxW = Math.max(maxWidth, maxHeight);
            int maxH = Math.min(maxWidth, maxHeight);
            scaleW = options.outWidth > maxW ? (options.outWidth / maxW) + 1 : 1;
            scaleH = options.outHeight > maxH ? (options.outHeight / maxH) + 1 : 1;
        } else {
            int maxW = Math.min(maxWidth, maxHeight);
            int maxH = Math.max(maxWidth, maxHeight);
            scaleW = options.outWidth > maxW ? (options.outWidth / maxW) + 1 : 1;
            scaleH = options.outHeight > maxH ? (options.outHeight / maxH) + 1 : 1;
        }
        //int scaleW=options.outWidth > maxWidth ? (options.outWidth / maxWidth)+1 : 1;
        //int scaleH=options.outHeight > maxHeight ? (options.outHeight / maxHeight)+1 : 1;
        int scale = Math.max(scaleW, scaleH);
        options.inSampleSize = scale;
        options.inDither = false;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);
        //-----------------------

        ExifInterface exif = getExif(srcPath);
        if (exif != null) {
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(exifOrientation);
            bitmap = rotate(bitmap, rotationInDegrees);
        }


        File targetFile = new File(targetPath);
        FileOutputStream out = new FileOutputStream(targetFile);
        if (out != null && bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        out.close();

        Log.i(TAG, "compress " + srcPath + " --> " + targetPath);
        Log.i(TAG, "compress quality=" + quality + ",outWidth=" + options.outWidth + ",outHeight=" + options.outHeight);
    }

    /**
     * 压缩图片，默认格式为jpg，若图片大于最大宽或高，将按比例缩小
     *
     * @param srcPath           源图片路径
     * @param quality           图片质量（0-100）,100为原图质量
     * @param maxWidth          最大宽（将自动识别，数值大的对应长边）
     * @param maxHeight         最大高（将自动识别，数值大的对应长边）
     * @param targetPath        输出压缩后图片的文件路径
     * @param compressQualityKb 压缩至多少KB  <=0 不压缩 大于0 压缩
     * @throws java.io.IOException
     */
    public static void compressEx(String srcPath, int quality, int maxWidth, int maxHeight, String targetPath, int compressQualityKb) throws IOException {
        //大小压缩-------------------------
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        int scaleW, scaleH;
        if (options.outWidth > options.outHeight) {
            int maxW = Math.max(maxWidth, maxHeight);
            int maxH = Math.min(maxWidth, maxHeight);
            scaleW = options.outWidth > maxW ? (options.outWidth / maxW) + 1 : 1;
            scaleH = options.outHeight > maxH ? (options.outHeight / maxH) + 1 : 1;
        } else {
            int maxW = Math.min(maxWidth, maxHeight);
            int maxH = Math.max(maxWidth, maxHeight);
            scaleW = options.outWidth > maxW ? (options.outWidth / maxW) + 1 : 1;
            scaleH = options.outHeight > maxH ? (options.outHeight / maxH) + 1 : 1;
        }
        //int scaleW=options.outWidth > maxWidth ? (options.outWidth / maxWidth)+1 : 1;
        //int scaleH=options.outHeight > maxHeight ? (options.outHeight / maxHeight)+1 : 1;
        int scale = Math.max(scaleW, scaleH);
        options.inSampleSize = scale;
        options.inDither = false;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);
        //-----------------------

        ExifInterface exif = getExif(srcPath);
        if (exif != null) {
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(exifOrientation);
            bitmap = rotate(bitmap, rotationInDegrees);
        }


        byteToFile(compressImageReturnByte(bitmap, quality, compressQualityKb), targetPath);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }

        Log.i(TAG, "compress " + srcPath + " --> " + targetPath);
        Log.i(TAG, "compress quality=" + quality + ",outWidth=" + options.outWidth + ",outHeight=" + options.outHeight);
    }

    /**
     * 质量压缩
     *
     * @param compressQualityKb 压缩至多少KB  <=0 不压缩 大于0 压缩
     * @param image
     * @return
     */
    public static byte[] compressImageReturnByte(Bitmap image, int quality, int compressQualityKb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = quality;
        if (compressQualityKb > 0) {
            while (baos.toByteArray().length / 1024 > compressQualityKb) {  //循环判断如果压缩后图片是否大于300kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
        }
        byte[] returnbytes = baos.toByteArray();

        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnbytes;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void byteToFile(byte[] bfile, String targetFile) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(targetFile);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static boolean isDamage(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, options); //filePath代表图片路径
        return options.mCancel || options.outWidth == -1
                || options.outHeight == -1;
    }

    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        return baos.toByteArray();
    }
}
