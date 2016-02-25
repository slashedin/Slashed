package in.slashed.slashed.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.digits.sdk.android.Digits;

import java.net.CookieHandler;
import java.net.CookiePolicy;

import in.slashed.slashed.R;
import okhttp3.OkHttpClient;

public class HomeActivity extends AppCompatActivity {

    Button searchB;
    TextView searchACTV;
    ImageButton button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Digits.getSessionManager().clearActiveSession();

        button1 = (ImageButton) findViewById(R.id.imageButton1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // init cookie manager
                CookieHandler cookieHandler = new CookieManager(
                        new PersistentCookieStore(ctx), CookiePolicy.ACCEPT_ALL);
                // init okhttp 3 logger
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient();
            }
        });

        searchB = (Button) findViewById(R.id.searchB);
        searchACTV = (TextView) findViewById(R.id.searchACTV);
        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Digits.getSessionManager().clearActiveSession();
                Intent intent = new Intent(HomeActivity.this, SearchResultsActivity.class);
                intent.putExtra("searchKey", searchACTV.getText() + "");
                Log.v("Saurabh", searchACTV.getText() + "");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Sign Out");
        menu.add("Share app");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
