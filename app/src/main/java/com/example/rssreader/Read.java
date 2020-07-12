package com.example.rssreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.rssreader.Adapter.FeedAdapter;
/** Read Activity Class */
public class Read extends AppCompatActivity {

    @Override
    /** method onCreate displays the web page url of the RSS feed row that was clicked to intent this activity so the user can read it from the activity on the creation of activity Read*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        String postURL = FeedAdapter.url; // gets the specific URL for the RSS feed article that was clicked to trigger this activity from th FeedAdapter

        WebView myWebView = (WebView) findViewById(R.id.postWebView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(postURL); //loads the URL for the RSS article in the activity's web view with javascript in case the user wants to interact with their reddit account from there to upvote, for example
    }
}