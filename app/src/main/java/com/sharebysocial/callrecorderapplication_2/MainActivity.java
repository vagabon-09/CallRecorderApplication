package com.sharebysocial.callrecorderapplication_2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startRecordingButton = findViewById(R.id.startRecordingId);
        Button stopRecordingButton = findViewById(R.id.endRecordingId);

        startRecordingButton.setOnClickListener(v -> {
            startRecording();
        });

        stopRecordingButton.setOnClickListener(v -> {
            stopRecording();
        });
    }

    private void startRecording() {
        // Create a MediaRecorder instance
        mediaRecorder = new MediaRecorder();

        // Set the audio source to capture the call
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        // Set the output format and encoder
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        // Set the output file path
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CallRecordings/";
        String fileName = "Recording_" + System.currentTimeMillis() + ".mp3";
        String completeFilePath = filePath + fileName;

        // Create the directory if it doesn't exist
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        mediaRecorder.setOutputFile(completeFilePath);

        try {
            // Prepare and start recording
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            Toast.makeText(getApplicationContext(), "Recording started.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle any errors
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null && isRecording) {
            // Stop recording and release resources
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            Toast.makeText(getApplicationContext(), "Recording stopped.", Toast.LENGTH_SHORT).show();
        }
    }

}