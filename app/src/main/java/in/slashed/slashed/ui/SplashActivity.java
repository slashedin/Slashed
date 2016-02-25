package in.slashed.slashed.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import in.slashed.slashed.R;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "IOLZBSi835Gij9CZw0QBg7MNQ";
    private static final String TWITTER_SECRET = "5by1p8T0JB1iwEHOdxUhX3ni2fVLmCKr2H1Rw08XxKho8sGVhZ";


    @Bind(R.id.splashTextIV) ImageView splashTextIV;
    @Bind(R.id.splashIconIV) ImageView splashIconIV;
    Animation animation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        splashTextIV.setVisibility(View.INVISIBLE);

        /*Check if user logged in or not and create appropriate intent.
              Logged-in :-
                Home
              New user:-
                Tour+Login/Sign Up
        */

        final Intent intent = new Intent(getApplicationContext(), TourActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_bottom);
        animation1.setDuration(1000);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //Start the activity
                SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String logged_in = mSharedPreferences.getString("Logged_in", "False");
                if (logged_in.equals("True")) {
                    getApplicationContext().startActivity(intent1);
                } else {
                    getApplicationContext().startActivity(intent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashTextIV.setVisibility(View.VISIBLE);
                splashTextIV.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashIconIV.startAnimation(fadeIn);

    }
}
