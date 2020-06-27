package com.xiaohan.media.audio;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Time: 2020/6/22 6:56
 * Author: Xiaohan
 * Des: New Class
 */
public class MediaRecordeManager {
    private static final String TAG = "MediaRecordeManager";
    private static MediaRecordeManager mMediaRecordeManager;
    private static String AudioFolderFile; //音频文件路径
    private static final String DIR_NAME = "mp3";
    private File mp3File;
    private MediaRecorder mMediaRecorder;
    private boolean isStartRecord = false;

    private MediaPlayer mMediaPlayer;

    public static MediaRecordeManager NewInstance() {
        if (mMediaRecordeManager == null) {
            synchronized (AudioRecordManager.class) {
                if (mMediaRecordeManager == null) {
                    mMediaRecordeManager = new MediaRecordeManager();
                }
            }
        }
        return mMediaRecordeManager;
    }

    /**
     * 初始化目录
     */
    public void init() {
        //文件目录
        AudioFolderFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                + File.separator + DIR_NAME;
        File WavDir = new File(AudioFolderFile);
        if (!WavDir.exists()) {
            boolean flag = WavDir.mkdirs();
            Log.d(TAG, "文件路径:" + AudioFolderFile + "创建结果:" + flag);
        } else {
            Log.d(TAG, "文件路径:" + AudioFolderFile + "创建结果: 已存在");
        }
    }

    public void startRecord() {
        if (isStartRecord) {
            Log.i(TAG, "startRecord: 正在录制");
            return;
        }
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
            //源aac数据文件
            mp3File = new File(AudioFolderFile + File.separator + sdf.format(new Date()) + ".aac");
            Log.i(TAG, "startRecord: "+mp3File.getPath());
            mMediaRecorder.setOutputFile(mp3File);

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isStartRecord = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
                isStartRecord = false;
            } catch (RuntimeException e) {
                //结束有问题
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
                if (mp3File.exists()) {
                    mp3File.delete();
                }
            }
        }
    }

    public void startMp3Play() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        try {
            Log.i(TAG, "startMp3Play: "+mp3File.getPath());
//            mMediaPlayer.setDataSource("/sdcard/DCIM/mp3/200623_124528.aac");
            mMediaPlayer.setDataSource(mp3File.getPath());
            mMediaPlayer.prepare();
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopMp3Play() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
