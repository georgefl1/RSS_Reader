package com.example.rssreader.Model;

/**
 * Class Enclosure holds thumbnail data for RSS feed articles (not used as of right now but could be implemented later)
 *
 * @author George Lord
 * @version 7.11.2020
 */
public class Enclosure
{
    public String thumbnail;

    public Enclosure(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}