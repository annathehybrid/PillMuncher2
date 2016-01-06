package anna.richarddawkinsalarmclock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

public class RingtonePlayingService extends Service {

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.e("MyActivity", "In the service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        // randomly pick a .wav file to play
        Random r = new Random();

        int min = 1;
        int max = 2;

        int random_number = r.nextInt(max - min + 1) + min;
        Log.e("Random number is ", String.valueOf(random_number));

        if (random_number == 1) {
            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            mMediaPlayer.isPlaying();
            //mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.start();

        }
        else if (random_number == 2) {
            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_2);
            mMediaPlayer.isPlaying();
            //mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.start();
        }
        else {
            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            mMediaPlayer.isPlaying();
            //mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.start();
        }




        //
        //Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        //player = new MediaPlayer();
        //try {
        //    player.setDataSource(this, alert);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
        //    player.setAudioStreamType(AudioManager.STREAM_ALARM);
        //    player.setLooping(true);
        //    try {
        //        player.prepare();
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }
        //    player.start();
        //}
        //

        Log.e("MyActivity", "In the service");


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        // doesn't work
        MediaPlayer mMediaPlayer = new MediaPlayer();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer.stop();
        }
    }


}