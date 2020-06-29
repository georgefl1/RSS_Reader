package com.example.rssreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rssreader.Model.RSSObject;
import com.example.rssreader.Interface.ItemClickListener;
import com.example.rssreader.R;
import com.example.rssreader.Read;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener //defines the individual views that the RecyclerView will hold
{

    public TextView txtTitle,txtPubDate,txtContent;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView) {
        super(itemView);

        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtPubDate = itemView.findViewById(R.id.txtPubDate);
        txtContent = itemView.findViewById(R.id.txtContent);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;
    public static String url; //defines the URL of each view in the feed for use in the read activity when (if) it is clicked

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext); //adapts each RSS object into a feed view
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row,parent,false);
        return new FeedViewHolder(itemView); // inflates each view holder on creation
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        //sets the title and the publication date text of each article view to their corresponding title and pubdate in the RSSObject
        String tempContent = ((rssObject.getItems().get(position).getContent().replaceAll("<[^>]+>", "").replaceAll("\\[(.*?)\\]", "")));
        while(tempContent.indexOf("\n") == 0) tempContent = tempContent.substring(2);
        holder.txtContent.setText(tempContent.substring(0, Math.min(tempContent.length(), 200)) + "...");
        // sets the content preview of each article view to not contain XML tags by using regex, and makes sure they are not too long, since they are only supposed to be previews of the article


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    Intent intent = new Intent(view.getContext(), Read.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //creates an intent to call the read activity when the article view is clicked from this fragment, which is why the flag is needed.
                    url = Uri.parse(rssObject.getItems().get(position).getLink()).toString();
                    //saved the url of this article to the fragment in a static context publicly so when the read activity is opened by the intent it can open the article from the web
                    mContext.startActivity(intent);
                    //starts the activity to read this article from the web from the intent defined earlier
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}