package com.ultra.vision.player;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.ultra.vision.database.DatabaseMemory;
import com.ultra.vision.database.MyDbHelper;
import com.ultra.vision.database.SQLiteHashMap;
import com.ultra.vision.databinding.ActivityBdProPlayerBinding;
import com.ultra.vision.movies.object.Movie;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.ExoPlaybackException;
import android.content.Intent;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ExoPlayerFactory;
import android.net.Uri;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import android.util.Log;
import com.google.android.exoplayer2.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ultra.vision.R;

public class BDProPlayer extends AppCompatActivity {

    ActivityBdProPlayerBinding binding;

    ExoPlayer exoPlayer;
    boolean isPlaying;

    // Movie passado por intent
    Movie movie;

    

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow()
                .setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int FLAG_SCREEN_ON = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        getWindow().setFlags(FLAG_SCREEN_ON, FLAG_SCREEN_ON);

        binding = ActivityBdProPlayerBinding.inflate(getLayoutInflater());

        getMovieFromIntent();

        setContentView(binding.getRoot());
        
        exoPlayerConfig();

        // TODO: Implement this method
    }
    
    private void getMovieFromIntent() {
        Intent intent = getIntent();

        movie = (Movie) intent.getSerializableExtra("db_player");
    }

    public void exoPlayerConfig() {
        try {

            // bandwisthmeter is used for
            // getting default bandwidth
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            // track selector is used to navigate between
            // video using a default seekbar.
            TrackSelector trackSelector =
                    new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            // we are adding our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // we are parsing a video url
            // and parsing its video uri.
            // Toast.makeText(BDMediaPlayer.this,
            // BDFilms.Film.FILM.getNome()+"\n"+BDFilms.Film.FILM.getLink(),
            // Toast.LENGTH_LONG).show();
            Uri videouri = Uri.parse(movie.getLink());

            // we are creating a variable for datasource factory
            // and setting its user agent as 'exoplayer_view'
            DefaultHttpDataSourceFactory dataSourceFactory =
                    new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor factory
            // and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and/ passing our event handler as null,
            MediaSource mediaSource =
                    new ExtractorMediaSource(
                            videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            binding.playerView.setPlayer(exoPlayer);

            // we are preparing our exoplayer
            // with media source.
            exoPlayer.prepare(mediaSource);

            // we are setting our exoplayer
            // when it is ready.
            exoPlayer.setPlayWhenReady(true);

            // Em seu código, registre o ouvinte de eventos no exoPlayer
            MyPlayerEventListener playerEventListener = new MyPlayerEventListener();
            exoPlayer.addListener(playerEventListener);
            
            TextView title = binding.playerView.findViewById(R.id.title);

            title.setText(movie.getNome());
        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }

    public class MyPlayerEventListener implements Player.EventListener {

        @Override
        public void onTimelineChanged(Timeline p1, Object p2, int p3) {
            // TODO: Implement this method
        }

        @Override
        public void onTracksChanged(TrackGroupArray p1, TrackSelectionArray p2) {
            // TODO: Implement this method
        }

        @Override
        public void onLoadingChanged(boolean p1) {
            // TODO: Implement this method
        }

        @Override
        public void onRepeatModeChanged(int p1) {
            // TODO: Implement this method
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean p1) {
            // TODO: Implement this method
        }

        @Override
        public void onPositionDiscontinuity(int p1) {
            // TODO: Implement this method
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters p1) {
            // TODO: Implement this method
        }

        @Override
        public void onSeekProcessed() {
            // TODO: Implement this method
        }

        // Implemente os métodos da interface Player.EventListener conforme necessário
        // ...

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_READY && playWhenReady) {
                // O vídeo está reproduzindo
                isPlaying = true;

            } else if (playbackState == Player.STATE_READY) {
                // O vídeo está pausado
                isPlaying = false;
                // stopTimer();
            } else if (playbackState == Player.STATE_ENDED) {
                // O vídeo terminou de ser reproduzido
                // stopTimer();
                isPlaying = false;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            // Trate o erro de reprodução do vídeo aqui
        }
    }

    public boolean isFinishingVideo() {
        boolean b = false;

        long videoDuration = exoPlayer.getDuration(); // Duração total do vídeo em milissegundos
        long currentPosition =
                exoPlayer.getCurrentPosition(); // Posição atual de reprodução em milissegundos

        long threshold =
                1000 * 60
                        * 5; // Defina um limite para considerar se o vídeo está perto do final
                             // (aqui é 5 minutos)

        // Verifique se o vídeo está perto do final com base no limite definido
        if (videoDuration - currentPosition < threshold) {
            b = true;
            // String currentTime = getCurrentTime();
            //  putFilm(BDFilms.Film.FILM.getNome(), currentTime, BDFilms.Film.FILM.getLogo());
        }

        return b;
    }
}
