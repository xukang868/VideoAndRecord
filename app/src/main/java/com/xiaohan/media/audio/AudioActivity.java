package com.xiaohan.media.audio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaohan.media.R;
import com.xiaohan.media.utils.PcmToWavUtil;

/**
 * Time: 2020/6/16 19:28
 * Author: Xiaohan
 * Des: New Class
 */
public class AudioActivity extends Activity implements View.OnClickListener {
    private boolean isStartRecord = true;
    private boolean isPlayRecord = true;
    private boolean isWavPlay = true;
    private boolean isStartMp3 = true;
    private boolean isPlayMp3 = true;
    private Button PCMRecord, PCMplay, WavPlay, Mp3Start, Mp3Play;
    private PcmToWavUtil mPcmToWavUtil;
    private AudioRecordManager mManager;
    private MediaRecordeManager mMediaRecordeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        PCMRecord = findViewById(R.id.PCMRecord);
        PCMplay = findViewById(R.id.PCMplay);
        WavPlay = findViewById(R.id.WavPlay);
        Mp3Start = findViewById(R.id.Mp3Start);
        Mp3Play = findViewById(R.id.Mp3Play);

        PCMRecord.setOnClickListener(this);
        PCMplay.setOnClickListener(this);
        WavPlay.setOnClickListener(this);
        Mp3Start.setOnClickListener(this);
        Mp3Play.setOnClickListener(this);

        mManager = AudioRecordManager.NewInstance();
        AudioRecordManager.init();
        mManager.setIAudioTrackInterface(new AudioRecordManager.IAudioTrackInterface() {
            @Override
            public void stop(boolean result) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       PCMplay.setText("PCM stop");
                       Toast.makeText(AudioActivity.this, "结束播放", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });

        mMediaRecordeManager = new MediaRecordeManager();
        mMediaRecordeManager.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PCMRecord:
                if (isStartRecord) {
                    PCMRecord.setText("PCM start");
                    //录音
                    mManager.startRecord();
                    Toast.makeText(AudioActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
                } else {
                    PCMRecord.setText("PCM stop");
                    mManager.stopRecord();
                    Toast.makeText(AudioActivity.this, "结束录音", Toast.LENGTH_SHORT).show();
                }
                isStartRecord = !isStartRecord;
                break;
            case R.id.PCMplay:
                if (isPlayRecord) {
                    mManager.playRecord();
                    PCMplay.setText("PCM play");
                    Toast.makeText(AudioActivity.this, "开始播放", Toast.LENGTH_SHORT).show();
                } else {
                    PCMplay.setText("PCM stop");
                    mManager.stopPlayRecord();
                    Toast.makeText(AudioActivity.this, "结束播放", Toast.LENGTH_SHORT).show();
                }
                isPlayRecord = !isPlayRecord;
                break;
            case R.id.WavPlay:
                if (isWavPlay) {
                    mManager.playRecord();
                    PCMplay.setText("Wav play");
                    Toast.makeText(AudioActivity.this, "开始播放", Toast.LENGTH_SHORT).show();
                } else {
                    PCMplay.setText("Wav stop");
                    mManager.stopPlayRecord();
                    Toast.makeText(AudioActivity.this, "结束播放", Toast.LENGTH_SHORT).show();
                }
                isWavPlay = !isWavPlay;
                break;
            case R.id.Mp3Start:
                if (isStartMp3) {
                    Mp3Start.setText("StartMp3");
                    mMediaRecordeManager.startRecord();
                    Toast.makeText(AudioActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
                } else {
                    Mp3Start.setText("StopMp3");
                    mMediaRecordeManager.stopRecord();
                    Toast.makeText(AudioActivity.this, "结束录音", Toast.LENGTH_SHORT).show();
                }
                isStartMp3 = !isStartMp3;
                break;
            case R.id.Mp3Play:
                if (isPlayMp3) {
                    Mp3Play.setText("Mp3Play");
                    mMediaRecordeManager.startMp3Play();
                    Toast.makeText(AudioActivity.this, "开始播放", Toast.LENGTH_SHORT).show();
                } else {
                    Mp3Play.setText("Mp3stop");
                    mMediaRecordeManager.stopMp3Play();
                    Toast.makeText(AudioActivity.this, "结束播放", Toast.LENGTH_SHORT).show();
                }
                isPlayMp3 = !isPlayMp3;
                break;

        }
    }
}
