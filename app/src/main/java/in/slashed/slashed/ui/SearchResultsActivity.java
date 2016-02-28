package in.slashed.slashed.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import in.slashed.slashed.R;
import in.slashed.slashed.adapter.EndlessScrollListener;
import in.slashed.slashed.adapter.SearchResultAdapter;
import in.slashed.slashed.data.SearchResult;

public class SearchResultsActivity extends AppCompatActivity {

    ArrayList<SearchResult> mAllSearchResults = new ArrayList<SearchResult>();
    ArrayList<SearchResult> mSearchResults;
    String searchResultURL;
    final String token = "&sToken=8caad61f-3643-47d2-b0e3-fe146ad281f2";
    ListView lv;
    final SearchResultAdapter adapter = new SearchResultAdapter(SearchResultsActivity.this, mAllSearchResults);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        lv = (ListView) findViewById(android.R.id.list);
        TextView mEmptyTextView = (TextView) findViewById(android.R.id.empty);
        lv.setEmptyView(mEmptyTextView);
        String searchKey;

        Intent intent = getIntent();
        searchKey = intent.getStringExtra("searchKey");

        searchResultURL = "http://slashed.in/api/search?keyword=" + searchKey;

        lv.setAdapter(adapter);

        httpCall();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(mAllSearchResults.get(i).getBuyUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        lv.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (!searchResultURL.isEmpty()) {
                    Log.v("Saurabh-if", searchResultURL);
                    customLoadMoreDataFromApi(page);
                    return true;
                } else {
                    Log.v("Saurabh-else", searchResultURL);
                    return false;
                }
            }
        });
    }

    private void customLoadMoreDataFromApi(int page) {
        Log.v("Saurabh-page", String.valueOf(page));
        httpCall();
    }

    private void httpCall() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(searchResultURL + token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                try {
                    mSearchResults = parseSearchResults(jsonData);
                    mAllSearchResults.addAll(mSearchResults);
                    mSearchResults.clear();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList<SearchResult> parseSearchResults(String jsonData) throws JSONException {
        JSONObject jsonResult = new JSONObject(jsonData);
        searchResultURL = jsonResult.getString("next_url");
        JSONArray data = jsonResult.getJSONArray("search_results");

        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

        for (int i=0; i< data.length(); i++) {
            JSONObject jsonSearchResult = data.getJSONObject(i);
            SearchResult searchResult = new SearchResult();

            searchResult.setName(jsonSearchResult.getString("name"));
            searchResult.setDetail1(jsonSearchResult.getString("detail1"));
            searchResult.setDetail2(jsonSearchResult.getString("detail2"));
            searchResult.setImage(jsonSearchResult.getString("image"));
            searchResult.setMinPrice(jsonSearchResult.getInt("min_price"));
            searchResult.setSeller(jsonSearchResult.getString("seller"));
            searchResult.setBuyUrl(jsonSearchResult.getString("buy_url"));

            searchResults.add(i, searchResult);
            Log.v("Saurabh-image",jsonSearchResult.getString("image"));
        }
        return searchResults;
    }


}
