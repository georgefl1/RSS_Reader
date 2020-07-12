package com.example.rssreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rssreader.Adapter.FeedAdapter;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rssreader.Common.HTTPDataHandler;
import com.example.rssreader.Model.RSSObject;

/**
 * Activity for displaying the recyclerView containing all of the rows of RSS articles made by the FeedAdapter from the RSSObject, and a refresh button to load/reload the RSS page into an RSS object with possibly new or different RSS articles to display.
 *
 * Runs the method to create the RSSObject for the RSS feed and display it on creation of the activity as well.
 *
 * @author George Lord
 * @version 7.11.2020
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RSSObject rssObject;
    ImageButton refreshButton;

    private final String RSS_link="https://www.reddit.com/r/makingmoney/.rss?format=xml";
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";

    @Override

    /** onCreate method runs on creation of MainActivity */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById((R.id.recyclerView));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        addListenerOnButton();
        loadRSS(); // loads the RSS feed initially

    }

    /** addListenerOnButton adds listener for refresh button on MainActivity */
    public void addListenerOnButton () {

        refreshButton = (ImageButton) findViewById(R.id.refresh);

        refreshButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                loadRSS(); //calls the method to reload the rss feed
                Toast.makeText(MainActivity.this,
                        "Feed Updated", Toast.LENGTH_SHORT).show(); // notifies the user when done reloading the RSS feed with a short toast
            }
        });
    }

    /** loadRSS method called to (re)load the RSS feed into rows on the recyclerView in MainActivity
     *
     * @deprecated AsyncTask is depreciated, but it works.
     * */
    private void loadRSS() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() { //makes an AsyncTask to handle loading the RSS feed
            ProgressDialog mDialog = new ProgressDialog(MainActivity.this); // creates a dialogue to show the user progress of loading rss

            @Override
            /** Overridden AsyncTask method onPreExecute lets user know the RSS feed is being loaded */
            protected void onPreExecute() {
                mDialog.setMessage("Loading...");
                mDialog.show(); //tells the user it is loading the RRS feed when it starts doing so
            }

            @Override
            /**
             * Overridden AsyncTask method doInBackground loads the RSS feed from its HTML page and returns parsed data
             *
             * @return The resulting string data of the RSS feed taken from the RSS feed url by HTTPDataHandler for creation of the RSSObject
             */
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return result; //in the background it parses the data from the web API call that takes the Reddit RSS XML data and converts it to JSON, returns this as a string
            }

            @Override
            /** Overridden AsyncTask method onPostExecute creates an RSS object from previously loaded RSS page data and pushes it into MainActivity's recyclerView rows through FeedAdapter */
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s, RSSObject.class); //creates a new RSS object from the JSON returned by the doInbackground method
                FeedAdapter adapter = new FeedAdapter(rssObject,getBaseContext()); //puts the RSS object into the feed adapter to make it possible to put on the recyclerview
                recyclerView.setAdapter(adapter); //hooks the recyclerview to the feed adapter with the RSS feed
                adapter.notifyDataSetChanged(); //calls the adapter's notifiyDataSetChanged() method
            }
        };
        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSS_link);//builds one Url string to call the RRS_to_JSON web api on the reddit RSS feed link
        loadRSSAsync.execute(url_get_data.toString()); //executes the asynctask we defined earlier to load the RSS feed from the API call URL
    }
}