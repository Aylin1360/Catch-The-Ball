package liselimanyak.catchtheball;

import android.media.AudioManager;
import android.media.SoundPool;
import android.content.Context;

public class SoundPlayer  {

    private static SoundPool soundPool;
    private static int overSound;
    private static int BackgroundSound;
    private  static int guitarSound;
    private static int pianoSound;
    private static int vocalSound;

    public SoundPlayer(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        guitarSound = soundPool.load(context, R.raw.guitar, 1);
        overSound = soundPool.load(context, R.raw.over, 1);
        pianoSound = soundPool.load(context, R.raw.piano, 1);
        vocalSound = soundPool.load(context, R.raw.vocal, 1);

    }

    public void playGuitarSound(){

        soundPool.play(guitarSound, 1.0f, 1.0f, 1 , 0, 1.0f);
    }

    public void playOverSound(){

        soundPool.play(overSound, 1.0f, 1.0f, 1 , 0, 1.0f);
    }
    public void playPianoSound(){

        soundPool.play(pianoSound, 1.0f, 1.0f, 1 , 0, 1.0f);
    }

    public void playVocalSound(){

        soundPool.play(vocalSound, 1.0f, 1.0f, 1 , 0, 1.0f);
    }


}
