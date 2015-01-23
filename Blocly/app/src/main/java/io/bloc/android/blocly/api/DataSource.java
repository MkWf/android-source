package io.bloc.android.blocly.api;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.bloc.android.blocly.BloclyApplication;
import io.bloc.android.blocly.BuildConfig;
import io.bloc.android.blocly.R;
import io.bloc.android.blocly.api.model.RssFeed;
import io.bloc.android.blocly.api.model.RssItem;
import io.bloc.android.blocly.api.model.database.DatabaseOpenHelper;
import io.bloc.android.blocly.api.model.database.table.RssFeedTable;
import io.bloc.android.blocly.api.model.database.table.RssItemTable;
import io.bloc.android.blocly.api.network.GetFeedsNetworkRequest;

/**
 * Created by Mark on 1/13/2015.
 */
public class DataSource {

    private DatabaseOpenHelper databaseOpenHelper;
    private RssFeedTable rssFeedTable;
    private RssItemTable rssItemTable;
    private List<RssFeed> feeds;
    private List<RssItem> items;

    public DataSource() {
        rssFeedTable = new RssFeedTable();
        rssItemTable = new RssItemTable();
        databaseOpenHelper = new DatabaseOpenHelper(BloclyApplication.getSharedInstance(),
                rssFeedTable, rssItemTable);
        feeds = new ArrayList<RssFeed>();
        items = new ArrayList<RssItem>();
        createFakeData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (BuildConfig.DEBUG && false) {
                    BloclyApplication.getSharedInstance().deleteDatabase("blocly_db");
                }
                SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();

                if(writableDatabase.isOpen()){
                    List<GetFeedsNetworkRequest.FeedResponse> feedData = new GetFeedsNetworkRequest("http://feeds.feedburner.com/androidcentral?format=xml").performRequest();
                    for (int feed = 0; feed < feedData.size(); feed++) {
                        ContentValues feedValues = new ContentValues();
                        feedValues.put(rssFeedTable.getLink(), feedData.get(feed).channelURL);
                        feedValues.put(rssFeedTable.getTitle(), feedData.get(feed).channelTitle);
                        feedValues.put(rssFeedTable.getDescription(), feedData.get(feed).channelDescription);
                        feedValues.put(rssFeedTable.getFeedUrl(), feedData.get(feed).channelFeedURL);

                        writableDatabase.insert(rssFeedTable.getName(), null, feedValues);

                        for(int item = 0; item < feedData.get(feed).channelItems.size(); item++){
                            ContentValues itemValues = new ContentValues();
                            itemValues.put(rssItemTable.getColumnLink(), feedData.get(feed).channelItems.get(item).itemURL);
                            itemValues.put(rssItemTable.getColumnTitle(), feedData.get(feed).channelItems.get(item).itemTitle);
                            itemValues.put(rssItemTable.getColumnDescription(), feedData.get(feed).channelItems.get(item).itemDescription);
                            itemValues.put(rssItemTable.getColumnGuid(), feedData.get(feed).channelItems.get(item).itemGUID);
                            itemValues.put(rssItemTable.getColumnPubDate(), feedData.get(feed).channelItems.get(item).itemPubDate);
                            itemValues.put(rssItemTable.getColumnEnclosure(), feedData.get(feed).channelItems.get(item).itemEnclosureURL);
                            itemValues.put(rssItemTable.getColumnMimeType(), feedData.get(feed).channelItems.get(item).itemEnclosureMIMEType);
                            itemValues.put(rssItemTable.getColumnRssFeed(), feed);

                            writableDatabase.insert(rssItemTable.getName(), null, itemValues);
                        }
                    }
                    writableDatabase.close();
                }
            }
        }).start();
    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }

    public List<RssItem> getItems() {
        return items;
    }

    void createFakeData() {
        feeds.add(new RssFeed("My Favorite Feed",
                "This feed is just incredible, I can't even begin to tell you…",
                "http://favoritefeed.net", "http://feeds.feedburner.com/favorite_feed?format=xml"));
        for (int i = 0; i < 10; i++) {
            items.add(new RssItem(String.valueOf(i),
                    BloclyApplication.getSharedInstance().getString(R.string.placeholder_headline) + " " + i,
                    BloclyApplication.getSharedInstance().getString(R.string.placeholder_content),
                    "http://favoritefeed.net?story_id=an-incredible-news-story",
                    "http://rs1img.memecdn.com/silly-dog_o_511213.jpg",
                    0, System.currentTimeMillis(), false, false));
        }
    }
}
