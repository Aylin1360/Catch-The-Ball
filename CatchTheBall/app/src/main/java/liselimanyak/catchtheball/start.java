package liselimanyak.catchtheball;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class start extends AppCompatActivity {

    public static enum STATE {
        START
    };

    private InterstitialAd interstitial;
    MediaPlayer mp;
    public static STATE State = STATE.START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        mp = MediaPlayer.create(this, R.raw.background);
        mp.setLooping(true);
        mp.start();


        interstitial = new InterstitialAd(this);

        interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        AdRequest adRequest = new AdRequest.Builder().build();

        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener(){

            public void onAdLoaded(){
                displayInterstitial();
            }
        });
    }

    public void displayInterstitial(){
        if(interstitial.isLoaded()){
            interstitial.show();
        }
    }


    public void startGame(View view){

        if (State == STATE.START) {
            startActivity(new Intent(getApplicationContext(), main.class));
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
