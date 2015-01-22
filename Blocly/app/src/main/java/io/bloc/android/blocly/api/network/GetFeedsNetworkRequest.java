package io.bloc.android.blocly.api.network;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.bloc.android.blocly.BloclyApplication;
import io.bloc.android.blocly.api.model.RssFeed;
import io.bloc.android.blocly.api.model.RssItem;

/**
 * Created by Mark on 1/19/2015.
 */
public class GetFeedsNetworkRequest extends NetworkRequest<List<RssFeed>> {

    public static final int ERROR_PARSING = 3;

    private static final String XML_TAG_TITLE = "title";
    private static final String XML_TAG_DESCRIPTION = "description";
    private static final String XML_TAG_LINK = "link";
    private static final String XML_TAG_ITEM = "item";
    private static final String XML_TAG_PUB_DATE = "pubDate";
    private static final String XML_TAG_GUID = "guid";
    private static final String XML_TAG_ENCLOSURE = "enclosure";
    private static final String XML_ATTRIBUTE_URL = "url";
    private static final String XML_ATTRIBUTE_TYPE = "type";

    String [] feedUrls;

    public GetFeedsNetworkRequest(String... feedUrls) {
        this.feedUrls = feedUrls;
    }

    @Override
    public List<RssFeed> performRequest() {
        List<RssFeed> responseFeeds = new ArrayList<RssFeed>(feedUrls.length);
        for (String feedUrlString : feedUrls) {
            InputStream inputStream = openStream(feedUrlString);
            if (inputStream == null) {
                return null;
            }
            try {
                String channelTitle = BloclyApplication.getSharedDataSource().getFeeds().get(0).getTitle();
                String channelDescription = BloclyApplication.getSharedDataSource().getFeeds().get(0).getDescription();
                String channelURL = BloclyApplication.getSharedDataSource().getFeeds().get(0).getFeedUrl();

                int dataItemSize = BloclyApplication.getSharedDataSource().getItems().size();
                List<RssItem> responseItems = new ArrayList<RssItem>(dataItemSize);
                for (int itemIndex = 0; itemIndex < dataItemSize; itemIndex++) {
                    String itemGUID = BloclyApplication.getSharedDataSource().getItems().get(itemIndex).getGuid();
                    String itemTitle = BloclyApplication.getSharedDataSource().getItems().get(itemIndex).getTitle();
                    String itemDescription = BloclyApplication.getSharedDataSource().getItems().get(itemIndex).getDescription();
                    String itemURL = BloclyApplication.getSharedDataSource().getItems().get(itemIndex).getUrl();
                    String imageURL = BloclyApplication.getSharedDataSource().getItems().get(itemIndex).getImageUrl();
                    Long itemPubDate = BloclyApplication.getSharedDataSource().getItems().get(itemIndex).getDatePublished();
                    Long rssFeedId = BloclyApplication.getSharedDataSource().getItems().get(itemIndex).getRssFeedId();

                    responseItems.add(new RssItem(itemGUID, itemTitle, itemDescription,
                            itemURL, imageURL, rssFeedId, itemPubDate, false, false));
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return responseFeeds;
    }

    private String optFirstTagFromDocument(Document document, String tagName) {
        NodeList elementsByTagName = document.getElementsByTagName(tagName);
        if (elementsByTagName.getLength() > 0) {
            return elementsByTagName.item(0).getTextContent();
        }
        return null;
    }

    public static class FeedResponse {
        public final String channelFeedURL;
        public final String channelTitle;
        public final String channelURL;
        public final String channelDescription;
        public final List<ItemResponse> channelItems;

        FeedResponse(String channelFeedURL, String channelTitle, String channelURL,
                     String channelDescription, List<ItemResponse> channelItems) {
            this.channelFeedURL = channelFeedURL;
            this.channelTitle = channelTitle;
            this.channelURL = channelURL;
            this.channelDescription = channelDescription;
            this.channelItems = channelItems;
        }
    }

    public static class ItemResponse {
        public final String itemURL;
        public final String itemTitle;
        public final String itemDescription;
        public final String itemGUID;
        public final String itemPubDate;
        public final String itemEnclosureURL;
        public final String itemEnclosureMIMEType;

        ItemResponse(String itemURL, String itemTitle, String itemDescription,
                     String itemGUID, String itemPubDate, String itemEnclosureURL,
                     String itemEnclosureMIMEType) {
            this.itemURL = itemURL;
            this.itemTitle = itemTitle;
            this.itemDescription = itemDescription;
            this.itemGUID = itemGUID;
            this.itemPubDate = itemPubDate;
            this.itemEnclosureURL = itemEnclosureURL;
            this.itemEnclosureMIMEType = itemEnclosureMIMEType;
        }
    }

}
