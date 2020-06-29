package com.example.rssreader.Model;

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