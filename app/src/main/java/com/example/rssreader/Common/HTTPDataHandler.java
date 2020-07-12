package com.example.rssreader.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class HTTPDataHandler takes the raw data from the RSS feed URL and builds a string from it containing all the RSS page's raw data in string format to be parsed and converted into an RSSObject
 *
 * @author George Lord
 * @version 7.11.2020
 */
public class HTTPDataHandler {
    static String stream = null;

    public HTTPDataHandler() {
    }

    /**
     * Method that takes the url where the RSS data can be found in JSON format and builds and returns a string from it containing all the RSS feed information for use in creating the RSSObject.
     *
     * @param urlString The url containing the RSS page data in RSS2JSON format as a string.
     * @return The string built from the RSS page at urlString containing the RSS data for creation of an RSSObject.
     */

    public String GetHTTPData(String urlString)
    {
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //tries to establish a connection to the web API RSS to JSON converter converting the reddit RSS feed

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                urlConnection.disconnect();
                //if the connection works it parses all of the json data into a string as the stream and then closes the connection (it's good manners)
            }

        } catch (IOException e) {
            e.printStackTrace();
        } //this shouldn't happen, but just in case. if it does, the stream will be null and no RSS feed will appear, anyways, which is out of our control since it likely means the RSS feed URL is down

        return stream;
    }
}
