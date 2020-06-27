package com.xiaohan.media.utils;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Time: 2019/3/30 10:31
 * Author: Xiaohan
 * Des: New Class
 */
public class DataRandomAccessFileUtils {
    private static String filePath = "/sdcard/test.pcw";
    private static DataRandomAccessFileUtils fileOpseratorUtils;

    public static DataRandomAccessFileUtils getInstance() {
        if (fileOpseratorUtils == null) {
            synchronized (DataRandomAccessFileUtils.class) {
                if (fileOpseratorUtils == null) {
                    fileOpseratorUtils = new DataRandomAccessFileUtils();
                }
            }
        }
        return fileOpseratorUtils;
    }

    /**
     * @param modleDataBean
     * @return
     */
    public long addFile(String modleDataBean) {
        LogUtils.i("add "+modleDataBean);
        long indexPointer = -1;
        RandomAccessFile rFile = null;
        try {
            File file = new File(filePath);
            rFile = new RandomAccessFile(file, "rw");//读取文件
            long point = rFile.length();
            indexPointer = point;
            rFile.seek(point);// 到达文件尾
            rFile.writeBytes(modleDataBean + point + "\r\n");//初始化 使用空行占位
            rFile.close();
        } catch (Exception e) {
            return indexPointer;
        } finally {
        }
        return indexPointer;
    }

    public void deleteModleFile() {
        File file = new File(filePath);
        if (file.exists()) {
            boolean delete = file.delete();
            LogUtils.i("delete " + delete);
        }
    }

}
