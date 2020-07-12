package com.example.rssreader.Model;

import java.util.List;

/**
 * Class RSSObject creates the RSS object that, along with Enclosure, Feed, and Items, holds all the data parsed the RSS feed
 *
 * @author George Lord
 * @version 7.11.2020
 */

public class RSSObject
{
    public String status;
    public Feed feed;
    public List<Item> items;

    public RSSObject(String status, Feed feed, List<Item> items){
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

//this object, along with all of it's objects, contain all the information from the retrieved RSS feed so it can be fed into rows in the RecycleView
