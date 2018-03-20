package paras.developers.com.tourmentor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);

        SharedPreferences sp = getSharedPreferences("account",MODE_PRIVATE);
        String uid = sp.getString("uid",null);
        if(uid!=null){
            Intent i = new Intent(SplashActivity.this,NavigationActivity.class);
            i.putExtra("uid",uid);
            startActivity(i);
        }
        else{

            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);

        }

    }
}
