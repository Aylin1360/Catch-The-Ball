package liselimanyak.catchtheball;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;
    private ImageView red;

    //Size

    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    //Position
    private int boxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;
    private int redX;
    private int redY;

    //Speed
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;
    private int redSpeed;

    //Score
    private int score = 0;
    //Health
    private ProgressBar health;


    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    //Status check
    private boolean action_flg = false;
    private boolean start_flg = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = new SoundPlayer(this);

        health = (ProgressBar)findViewById(R.id.health);
        scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        startLabel = (TextView)findViewById(R.id.startLabel);
        box = (ImageView)findViewById(R.id.box);
        orange = (ImageView)findViewById(R.id.orange);
        black =(ImageView)findViewById(R.id.black);
        pink = (ImageView)findViewById(R.id.pink);
        red = (ImageView)findViewById(R.id.red);

        int maxValue = health.getMax();

        //Get Screen Size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        boxSpeed = Math.round(screenHeight / 60F);
        orangeSpeed = Math.round(screenWidth / 60F);
        pinkSpeed = Math.round(screenWidth / 36F);
        blackSpeed = Math.round(screenWidth / 45F);
        redSpeed = Math.round(screenWidth / 45F);

        Log.v("SPEED_BOX", boxSpeed + "");
        Log.v("SPEED_ORANGE", orangeSpeed + "");
        Log.v("SPEED_PINK", pinkSpeed + "");
        Log.v("SPEED_BLACK", blackSpeed + "");
        Log.v("SPEED_RED", redSpeed + "");

        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);
        red.setX(-80);
        red.setY(-80);

        scoreLabel.setText("Score : 0" );

    }

    public void changePos(){

        hitCheck();
        //Orange
        orangeX -= orangeSpeed;
        if(orangeX < 0){
            orangeX = screenWidth + 20;
            orangeY = (int)Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //Black
        blackX -= blackSpeed;
        if(blackX < 0){
            blackX = screenWidth + 10;
            blackY = (int)Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        //pink
        pinkX -= pinkSpeed;
        if(pinkX < 0){
            pinkX = screenWidth + 5000;
            pinkY = (int)Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        //red
        redX -= redSpeed;
        if(redX < 0){
            redX = screenWidth + 5000;
            redY = (int)Math.floor(Math.random() * (frameHeight - red.getHeight()));
        }
        red.setX(redX);
        red.setY(redY);


        if(action_flg == true){
            boxY -= boxSpeed;
        }else{
            boxY += boxSpeed;
        }

        if(boxY < 0)boxY = 0;
        if(boxY > frameHeight - boxSize)boxY = frameHeight - boxSize;

        box.setY(boxY);

        scoreLabel.setText("Score : " + score);
    }

    public void hitCheck(){

        //orange
        int orangeCenterX = orangeX + orange.getWidth() / 2;
        int orangeCenterY = orangeY + orange.getHeight() / 2;

        if (0 <= orangeCenterX && orangeCenterX <= boxSize &&
        boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {

            score += 10;
            orangeX = -10;
            sound.playVocalSound();
        }

        //pink
        int pinkCenterX = pinkX + pink.getWidth() / 2;
        int pinkCenterY = pinkY + pink.getHeight() / 2;

        if (0 <= pinkCenterX && pinkCenterX <= boxSize &&
                boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {

            score += 30;
            pinkX = -10;
            sound.playGuitarSound();
        }

        //red
        int redCenterX = redX + red.getWidth() / 2;
        int redCenterY = redY + red.getHeight() / 2;

        if (0 <= redCenterX && redCenterX <= boxSize &&
                boxY <= redCenterY && redCenterY <= boxY + boxSize) {

            score += 20;
            redX = -10;
            sound.playPianoSound();
        }

        //Black
        int blackCenterX = blackX + black.getWidth() / 2;
        int blackCenterY = blackY + black.getHeight() / 2;

        if (0 <= blackCenterX && blackCenterX <= boxSize &&
                boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {

            timer.cancel();
            timer = null;

            health.setProgress(0);
            sound.playOverSound();
            //Show result
            Intent intent = new Intent(getApplicationContext(),result.class);
            intent.putExtra("SCORE" ,score);
            startActivity(intent);
        }

    }

    public boolean onTouchEvent(MotionEvent me){

        FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
        frameHeight = frame.getHeight();

        boxY = (int)box.getY();
        boxSize = box.getHeight();

        if(start_flg == false){

            start_flg = true;
            startLabel.setVisibility(View.GONE);


            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        }else{

            if(me.getAction()==MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else if(me.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }

        }

        return true;
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
