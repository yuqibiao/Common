package com.smartdevice.aidltestdemo.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

/**
 * FileName    : FileUtil.java
 * Description : 文件和IO的操作帮助类
 **/
public class FileUtil {
    /**
     * 是否有sdcard
     */
    public static boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 获取SD卡跟路径。SD卡不可用时，返回null
     */
    public static String getSDcardRoot() {
        if (hasSdcard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return null;
    }


    /**
     * 获取应用目录的根目录
     */
    public static String getContextPath(Context context) {
        String path = "";
        try {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android" + File.separator + "data" + File.separator + context.getPackageName()
                    + File.separator;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
        }
        return path;
    }

    /**
     * 获取应用目录的相对路径
     */
    public static String getContextPath(Context context, String path) {
        return getContextPath(context) + path;
    }

    /**
     * 文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        return new File(path).exists();
    }

    /**
     * 文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean checkExists(String path) {
        return new File(path).mkdir();
    }

    public static File createDirIfNotExist(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.isDirectory()) {
            dirFile.delete();
        }
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    public static File createFileIfNotExist(String filePath) {
        File file = new File(filePath);
        createFileIfNotExist(file);
        return file;
    }

    public static File createFileIfNotExist(File file) {
        if (!file.isFile()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    private static String getNextPath(String path) {
        Pattern pattern = Pattern.compile("\\(\\d{1,}\\)\\.");
        Matcher matcher = pattern.matcher(path);
        String str = null;
        while (matcher.find()) {
            str = matcher.group(matcher.groupCount());
        }
        if (str == null) {
            int index = path.lastIndexOf(".");
            if (index != -1) {
                path = path.substring(0, index) + "(1)" + path.substring(index);
            } else {
                path = path + "(1)";
            }
        } else {
            int index = Integer.parseInt(str.replaceAll("[^\\d]*(\\d)[^\\d]*", "$1")) + 1;
            path = path.replace(str, "(" + index + ").");
        }
        return path;
    }

    public static void createDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean ret = dir.mkdirs();
        }
    }

    public static boolean deleteFile(File file) {
        try {
            if ((file != null) && (file.exists()) && (file.isFile())) {
                try {
                    return file.delete();
                } catch (Exception e) {
                    file.deleteOnExit();
                }
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }


    public static void deleteAllFile(String folderFullPath) {
        File file = new File(folderFullPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    String filePath = fileList[i].getPath();
                    deleteAllFile(filePath);
                }
            } else if (file.isFile()) {
                try {
                    file.delete();
                } catch (Exception e) {
                    file.deleteOnExit();
                }
            }
        }
    }


    private static boolean isExistSdcard() {
        if (!isEmulator()) {
            return Environment.getExternalStorageState().equals("mounted");
        }
        return true;
    }

    private static boolean isEmulator() {
        return Build.MODEL.equals("sdk");
    }

    public static boolean closeStream(Closeable beCloseStream) {
        if (beCloseStream != null) {
            try {
                beCloseStream.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public static long getFileLength(String filePath) {
        if ((filePath == null) || (filePath.trim().length() < 1)) {
            return 0L;
        }
        try {
            File file = new File(filePath);
            return file.length();
        } catch (Exception e) {
        }
        return 0L;
    }

    public static String getFileNameWithSuffix(String filePath) {
        if ((filePath == null) || (filePath.trim().length() < 1)) {
            return null;
        }
        String fileName;
        try {
            File file = new File(filePath);
            fileName = file.getName();
        } catch (Exception e) {
            fileName = null;
        }
        return fileName;
    }

    public static String getFileNameWithoutSuffix(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int dotIdx = fileName.lastIndexOf(".");
        if (dotIdx != -1) {
            return fileName.substring(0, dotIdx);
        }
        if (fileName.indexOf(".") == -1) {
            return fileName;
        }
        return "";
    }

    public static String getFileTypeString(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String type = fileName.substring(index + 1).toLowerCase(Locale.US);
            return type;
        }
        return "";
    }

    public static String getFileSizeFromPath(String filePath) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        double mBSize = 1048576.0D;
        int kBSize = 1024;
        int bSize = 1;
        long fileLength = getFileLength(filePath);
        if (fileLength >= mBSize) {
            return decimalFormat.format(fileLength / mBSize) + "MB";
        }
        if (fileLength >= kBSize) {
            return Long.valueOf(fileLength / kBSize) + "KB";
        }
        if (fileLength >= bSize) {
            return Long.valueOf(fileLength / bSize) + "B";
        }
        return "0B";
    }

    public static String getFileSizeFromIntSize(int size) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        double mBSize = 1048576.0D;
        int kBSize = 1024;
        int bSize = 1;
        if (size >= mBSize) {
            return decimalFormat.format(size / mBSize) + "MB";
        }
        if (size >= kBSize) {
            return Integer.valueOf(size / kBSize) + "KB";
        }
        if (size >= bSize) {
            return Integer.valueOf(size / bSize) + "B";
        }
        return "0B";
    }

    public static String getFilePath(String absolutePath) {
        int idx = absolutePath.lastIndexOf(File.separator);
        String path = null;
        if (idx != -1) {
            path = absolutePath.substring(0, idx);
        }
        return path;
    }

    public static String getFileName(String absolutePath) {
        int idx = absolutePath.lastIndexOf(File.separator);
        String name = null;
        if (idx != -1)
            name = absolutePath.substring(idx + 1);
        else {
            name = absolutePath;
        }
        return name;
    }

    public static String appendPath(String path, String name) {
        if (TextUtils.isEmpty(path)) {
            return name;
        }
        return path + File.separator + name;
    }

    public static boolean setNoMediaFlag(File directory) {
        File noMediaFile = new File(directory, ".nomedia");
        if (!noMediaFile.exists()) {
            try {
                return noMediaFile.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public static String readSDFile(File fileName) {
        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String convertCodeAndGetText(File file) {// 转码
        BufferedReader reader;
        String text = "";
        try {
            // FileReader f_reader = new FileReader(file);
            // BufferedReader reader = new BufferedReader(f_reader);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fis);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
            in.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                    && first3bytes[2] == (byte) 0xBF) {// utf-8

                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFE) {

                reader = new BufferedReader(
                        new InputStreamReader(in, "unicode"));
            } else if (first3bytes[0] == (byte) 0xFE
                    && first3bytes[1] == (byte) 0xFF) {

                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16be"));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFF) {

                reader = new BufferedReader(new InputStreamReader(in,
                        "utf-16le"));
            } else {

                reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            }
            String str = reader.readLine();

            while (str != null) {
                text = text + str + "\n";
                str = reader.readLine();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    
    /** 判断字符串为空，集合为空，数组为空(后续可以拓展hashSet,hashMap ...) */
    public static boolean isEmpty(Object obj) {
        boolean isEmpty = true;
        if (obj != null) {
            if (obj instanceof String) {
                // 字符串
                String tmp = obj.toString();
                isEmpty = tmp.trim().equals("");

            } else if (obj instanceof Collection<?>) {
                // 集合
                Collection<?> collections = (Collection<?>) obj;
                isEmpty = collections.size() == 0;

            } else if (obj instanceof Object[]) {
                // 数组
                Object[] objarray = (Object[]) obj;
                isEmpty = objarray.length == 0;
            }
        }
        return isEmpty;
    }
    
}
