package com.ultra.vision.player.controller;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.*;
import com.ultra.vision.R;

public class BDControlViewTv extends PlayerControlView
{
    private ExoPlayer player; // Instância do ExoPlayer
	private TextView fileNameTextView; // TextView para exibir o nome do arquivo MP4
	
	public BDControlViewTv(Context context) {
		super(context);
		initialize();
	}

	public BDControlViewTv(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public BDControlViewTv(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();
	}

	private void initialize() {
		// Configura o foco para o controle personalizado
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		LayoutInflater.from(getContext()).inflate(R.layout.custom_player_title, this, true);
        fileNameTextView = findViewById(R.id.textview_title);

		setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (event.getAction() == KeyEvent.ACTION_DOWN) {
						switch (keyCode) {
							case KeyEvent.KEYCODE_DPAD_CENTER: // Botão OK
								togglePlayPause();
								return true;
							case KeyEvent.KEYCODE_DPAD_RIGHT: // Botão RIGHT
								fastForward();
								return true;
							case KeyEvent.KEYCODE_DPAD_LEFT: // Botão LEFT
								rewind();
								return true;
						}
					}
					return false;
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
	
	
	public void setFileName(String fileName) {
        if (fileNameTextView != null) {
            fileNameTextView.setText(fileName);
        }
	
}}
