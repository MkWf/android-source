package io.bloc.android.blocly.api.model.database.table;

/**
 * Created by Mark on 1/19/2015.
 */
public class RssItemTable extends Table {

    private static final String COLUMN_LINK = "link";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_GUID = "guid";
    private static final String COLUMN_PUB_DATE = "pub_date";
    private static final String COLUMN_ENCLOSURE = "enclosure";
    private static final String COLUMN_MIME_TYPE = "mime_type";
    private static final String COLUMN_RSS_FEED = "rss_feed";
    private static final String COLUMN_FAVORITE = "is_favorite";
    private static final String COLUMN_ARCHIVED = "is_archived";

    @Override
    public String getName() {
        return "rss_items";
    }

    @Override
    public String getCreateStatement() {
        return "CREATE TABLE " + getName() + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LINK + " TEXT UNIQUE,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_GUID + " TEXT,"
                + COLUMN_PUB_DATE + " INTEGER,"
                + COLUMN_ENCLOSURE + " TEXT,"
                + COLUMN_MIME_TYPE + " TEXT,"
                + COLUMN_RSS_FEED + " INTEGER,"
                + COLUMN_FAVORITE + " INTEGER DEFAULT 0,"
                + COLUMN_ARCHIVED + " INTEGER DEFAULT 0)";
    }

    public String getColumnArchived() {
        return COLUMN_ARCHIVED;
    }

    public String getColumnLink() {
        return COLUMN_LINK;
    }

    public String getColumnTitle() {
        return COLUMN_TITLE;
    }

    public String getColumnDescription() {
        return COLUMN_DESCRIPTION;
    }

    public String getColumnGuid() {
        return COLUMN_GUID;
    }

    public String getColumnPubDate() {
        return COLUMN_PUB_DATE;
    }

    public String getColumnEnclosure() {
        return COLUMN_ENCLOSURE;
    }

    public String getColumnMimeType() {
        return COLUMN_MIME_TYPE;
    }

    public String getColumnRssFeed() {
        return COLUMN_RSS_FEED;
    }

    public String getColumnFavorite() {
        return COLUMN_FAVORITE;
    }
}
