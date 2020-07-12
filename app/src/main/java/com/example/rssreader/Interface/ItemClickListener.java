package com.example.rssreader.Interface;

import android.view.View;

/** Interface ItemClickListener creates ItemClickListener used by FeedAdapter in determining whether or not a click is a long click so feed items can be scrolled past without "clicking" them */
public interface ItemClickListener {
    void onClick(View view, int position,boolean isLongClick);
}
