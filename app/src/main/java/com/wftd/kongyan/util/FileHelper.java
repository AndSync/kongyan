package com.wftd.kongyan.util;

import android.os.Environment;
import android.text.TextUtils;
import com.wftd.kongyan.app.App;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 文件缓存管理
 * object 缓存迁移至app内部文件存储路径 不可清除
 * 其他临时缓存文件 保存在外部存储 可以清除
 *
 * @author AndSync
 * @date 2017/11/13
 * Copyright © 2014-2017 北京智阅网络科技有限公司 All rights reserved.
 */
public class FileHelper {

    private static String TAG = "FileHelper";

    public interface Suffix {
        String JPG = ".jpg";
        String PNG = ".png";
        String MP3 = ".mp3";
    }

    /**
     * 目录名称
     */
    private static String FILE_DIR_NAME_OBJECT = "object";

    private static String CACHE_DIR_NAME_IMAGE = "image";
    private static String CACHE_DIR_NAME_AUDIO = "audio";
    private static String CACHE_DIR_NAME_TEMP = "temp";

    /**
     * 文件名称
     */
    private static String FILE_NAME_AUDIO = "audio";
    private static String FILE_NAME_LONG_IMAGE = "long_image";
    private static String FILE_NAME_WATER_MARK = "water_mark";

    /**
     * 获取文件存储根目录
     */
    public static String getFileRootPath() {
        String path = getExternalFileRootPath();
        // path = "/storage/emulated/0/Android/data/" + context.getPackageName() + "/files/";
        if (TextUtils.isEmpty(path)) {
            path = getAppFileRootPath();
            // path = "/data/data/" + context.getPackageName() + "/files/";
        }
        LogUtils.i(TAG, "FileRootPath=" + path);
        return path;
    }

    public static String getExternalFileRootPath() {
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File fileDir = App.getContext().getExternalFilesDir(null);
            if (fileDir != null && isWriteAble(fileDir)) {
                path = fileDir.getPath() + File.separator;
            }
        }
        return path;
    }

    public static String getAppFileRootPath() {
        return App.getContext().getFilesDir().getPath() + File.separator;
    }

    public static String getCacheRootPath() {
        String path = getExternalCacheRootPath();
        // path = "/storage/emulated/0/Android/data/" + context.getPackageName() + "/cache/";
        if (TextUtils.isEmpty(path)) {
            path = getAppCacheRootPath();
            // path = "/data/data/" + context.getPackageName() + "/cache/";
        }
        LogUtils.i(TAG, "CacheRootPath=" + path);
        return path;
    }

    public static String getExternalCacheRootPath() {
        String path = "";
        if (isSdCardMounted()) {
            File fileDir = App.getContext().getExternalCacheDir();
            if (fileDir != null && isWriteAble(fileDir)) {
                path = fileDir.getPath() + File.separator;
            }
        }
        return path;
    }

    public static String getAppCacheRootPath() {
        return App.getContext().getCacheDir().getPath() + File.separator;
    }

    public static boolean isSdCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isWriteAble(File fileDir) {
        boolean flag;
        File file = new File(fileDir, "test.txt");
        try {
            flag = file.createNewFile();
            file.delete();
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取/创建对象文件存储目录
     */
    private static File getObjectFileDir() {
        String fileDirPath = getAppCacheRootPath() + FILE_DIR_NAME_OBJECT + File.separator;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        LogUtils.i(TAG, "ObjectFileDir=" + fileDir.getPath());
        return fileDir;
    }

    /**
     * 获取/创建图片文件存储目录
     */
    private static File getImageCacheDir() {
        String fileDirPath = getCacheRootPath() + CACHE_DIR_NAME_IMAGE + File.separator;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        LogUtils.i(TAG, "ImageCacheDir=" + fileDir.getPath());
        return fileDir;
    }

    /**
     * 获取/创建语音文件存储目录
     */
    private static File getAudioCacheDir() {
        String fileDirPath = getCacheRootPath() + CACHE_DIR_NAME_AUDIO + File.separator;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        LogUtils.i(TAG, "AudioCacheDir=" + fileDir.getPath());
        return fileDir;
    }

    /**
     * 获取/创建临时文件存储目录
     */
    private static File getTempCacheDir() {
        String fileDirPath = getCacheRootPath() + CACHE_DIR_NAME_TEMP + File.separator;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        LogUtils.i(TAG, "TempCacheDir=" + fileDir.getPath());
        return fileDir;
    }

    /**
     * 录制语音临时文件
     */
    public static File getAudioTempFile() {
        File file = new File(getAudioCacheDir(), FILE_NAME_AUDIO + "_" + System.currentTimeMillis() + Suffix.MP3);
        return file;
    }

    /**
     * 分享图片临时文件
     */
    public static File getLongImageFile() {
        File file = new File(getImageCacheDir(), FILE_NAME_LONG_IMAGE + "_" + System.currentTimeMillis() + Suffix.JPG);
        return file;
    }

    /**
     * 水印临时文件
     */
    public static File getLiveWaterMarkFile() {
        File file = new File(getImageCacheDir(), FILE_NAME_WATER_MARK + "_" + System.currentTimeMillis() + ".png");
        return file;
    }

    /**
     * apk下载临时文件
     */
    public static File getDownloadApkFile(String version) {
        File file = new File(getTempCacheDir(), "qctt_" + version + "_" + System.currentTimeMillis() + ".apk");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getFileSuffix(String filePath) {
        if (!TextUtils.isEmpty(filePath) && filePath.contains(".")) {
            String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
            return suffix;
        } else {
            return "";
        }
    }

    public static String getCacheSize() {
        Long externalCacheFileSize = getFolderSize(new File(getExternalCacheRootPath()));
        Long appCacheFileSize = getFolderSize(new File(getAppCacheRootPath()));
        return formatFileSize(externalCacheFileSize + appCacheFileSize);
    }

    public static void clearCache() {
        deleteDir(new File(getExternalCacheRootPath()));
        deleteDir(new File(getAppCacheRootPath()));
    }

    public static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        }
        return size;
    }

    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("###.##");
        float f = ((float) size / (float) (1024 * 1024));

        if (f < 1.0) {
            float f2 = ((float) size / (float) (1024));
            return df.format(Float.valueOf(f2).doubleValue()) + " KB";
        } else {
            return df.format(Float.valueOf(f).doubleValue()) + " MB";
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 保存对象到文件
     */
    public static synchronized void saveObject2File(Serializable serializableObject, String fileName) {
        try {
            File fileDir = FileHelper.getObjectFileDir();
            File file = new File(fileDir, fileName);
            if (null == serializableObject) {
                if (file != null && file.exists()) {
                    file.delete();
                }
                return;
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(serializableObject);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取对象
     */
    public static synchronized Serializable readObjectFromFile(String fileName) {
        Serializable object = null;
        try {
            File fileDir = FileHelper.getObjectFileDir();
            File file = new File(fileDir, fileName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                object = (Serializable) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
