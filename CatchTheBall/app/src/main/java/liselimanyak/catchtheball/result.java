package liselimanyak.catchtheball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class result extends AppCompatActivity {

    public static enum STATE {
        START
    };

    MediaPlayer mp;
    public static start.STATE State = start.STATE.START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mp = MediaPlayer.create(this, R.raw.background);
        mp.setLooping(true);
        mp.start();

        TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView)findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE" , 0);
        scoreLabel.setText(score + "");

        SharedPreferences settings = getSharedPreferences("GAME DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH SCORE" , 0);

        if(score > highScore){
            highScoreLabel.setText("Hıgh Score : " + score);

            //save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH SCORE",score);
            editor.commit();
        }else{
            highScoreLabel.setText("High Score : " + highScore );
        }
    }

    public void tryAgain(View view){

        if (State == start.STATE.START) {
            startActivity(new Intent(getApplicationContext(), start.class));
            mp.stop();
        }
    }

    //Disable Return Button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){

        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }
}
