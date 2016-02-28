package in.slashed.slashed.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsOAuthSigning;
import com.digits.sdk.android.DigitsSession;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.slashed.slashed.R;
import in.slashed.slashed.adapter.TourPagerAdapter;

public class TourActivity extends Activity {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    @Bind(R.id.pager) ViewPager mPager;
    @Bind(R.id.indicator) CirclePageIndicator mIndicator;
    @Bind(R.id.tourB) Button mTourB;

    PagerAdapter mAdapter;
    int[] image;
    String[] text;
    DigitsAuthButton digitsButton;
    //String twitterAuth = "";
    String[] twitterAuthArr = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        ButterKnife.bind(this);

        //Digits OTP Auth
        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setAuthTheme(R.style.AppTheme);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, final String phoneNumber) {
                // TODO: associate the session userID with your user model
                /*Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();*/

                TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
                TwitterAuthToken authToken = (TwitterAuthToken) session.getAuthToken();
                DigitsOAuthSigning oauthSigning = new DigitsOAuthSigning(authConfig, authToken);
                Map<String, String> authHeaders = oauthSigning.getOAuthEchoHeadersForVerifyCredentials();
                int i = 0;

                for (Map.Entry<String, String> entry : authHeaders.entrySet()) {
                    //twitterAuth = twitterAuth + String.format("\"%s\" : \"%s\"", entry.getKey().replaceAll("-","_"), entry.getValue().replaceAll("\"","\\\\\"")) + ",";
                    //Log.i("digits", String.format("%s : %s", entry.getKey(), entry.getValue()));
                    twitterAuthArr[i] = entry.getKey().toString();
                    twitterAuthArr[i+1] = entry.getValue();
                    i = i+2;
                }

                //Post data to server
                new Thread() {

                    OkHttpClient client = new OkHttpClient();
                    Response response = null;
                    //String postBody = "{" + twitterAuth + "\"phone_number\": \"" + phoneNumber + "\"}";
                    RequestBody formBody;

                    @Override
                    public void run() {
                        formBody = new FormBody.Builder()
                                .add("phone_number",phoneNumber)
                                .add(twitterAuthArr[0], twitterAuthArr[1])
                                .add(twitterAuthArr[2], twitterAuthArr[3])
                                .build();

                        Log.v("Saurabh-twitter1",twitterAuthArr[0]);
                        Log.v("Saurabh-twitter2",twitterAuthArr[1]);
                        Log.v("Saurabh-twitter1",twitterAuthArr[2]);
                        Log.v("Saurabh-twitter2",twitterAuthArr[3]);

                        Request request = new Request.Builder()
                                .url("http://www.slashed.in/api/post")
                                .post(formBody)
                                //.post(RequestBody.create(MEDIA_TYPE_JSON, postBody))
                                .build();
                        //Log.v("Saurabh-post", postBody);

                        try {
                            response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                Log.v("Saurabh-response", response.body().toString());
                                //Put login data in local file-store
                                SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                mSharedPreferences.edit().putString("Logged_in", "True")
                                        .putString("auth_token", "123456789")
                                        .apply();
                            } else {
                                throw new IOException("Unexpected code " + response);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                //Move user to home activity
                Intent intent = new Intent(TourActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });

        text = new String[] { "Search for your desired product",
                "Compare prices across various m-commerce platforms",
                "Buy the product :)" };
        image = new int[] { R.mipmap.tour1, R.mipmap.tour2, R.mipmap.tour3};

        mAdapter = new TourPagerAdapter(TourActivity.this, image, text);
        mPager.setAdapter(mAdapter);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setRadius((float) 20);
        mIndicator.setCentered(true);
        mIndicator.setViewPager(mPager);
    }

    @OnClick (R.id.tourB)
    public void onClick(View view) {

        digitsButton.performClick();

        /*Dialog for mobile number
        DialogFragment dialogFragment = new MobileDialogFragment();
        dialogFragment.show(getFragmentManager(), "Mobile Number");

        //Second for password(include forgot password) if registered / OTP and passwords if not
        // Extra details like e-mail id, name etc.

        //set login in local system
        //move to home screen*/
    }

}

