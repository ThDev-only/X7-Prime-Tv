package com.ultra.vision.player.controller;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.*;
import com.ultra.vision.*;

import com.ultra.vision.R;

public class BDControlView extends PlayerControlView {
    private ImageButton pauseButton;
    private ImageButton rewindButton;
    private ImageButton fastForwardButton;

    private ExoPlayer player; // Instância do ExoPlayer

    public BDControlView(Context context) {
        super(context);
        initialize();
    }

    public BDControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BDControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        pauseButton = findViewById(R.id.button_pause);
        rewindButton = findViewById(R.id.button_rewind);
        fastForwardButton = findViewById(R.id.button_fast_forward);

        pauseButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					togglePlayPause();
				}
			});

        rewindButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					rewind();
				}
			});

        fastForwardButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					fastForward();
				}
			});
    }

    public void setPlayer(ExoPlayer player) {
        this.player = player;
    }

    private void togglePlayPause() {
        if (player != null) {
            if (player.getPlayWhenReady()) {
                player.setPlayWhenReady(false); // Pausa a reprodução
            } else {
                player.setPlayWhenReady(true); // Retoma a reprodução
            }
        }
    }

    private void rewind() {
        if (player != null) {
            long currentPosition = player.getCurrentPosition();
            long seekPosition = currentPosition - 10000; // Retrocede em 10 segundos (10.000 milissegundos)

            // Verifica se o seekPosition é maior ou igual a 0
            if (seekPosition < 0) {
                seekPosition = 0;
            }

            player.seekTo(seekPosition);
        }
    }

    private void fastForward() {
        if (player != null) {
            long currentPosition = player.getCurrentPosition();
            long duration = player.getDuration();
            long seekPosition = currentPosition + 10000; // Avança em 10 segundos (10.000 milissegundos)

            // Verifica se o seekPosition está dentro dos limites da duração do vídeo
            if (seekPosition > duration) {
                seekPosition = duration;
            }

            player.seekTo(seekPosition);
        }
    }
}

