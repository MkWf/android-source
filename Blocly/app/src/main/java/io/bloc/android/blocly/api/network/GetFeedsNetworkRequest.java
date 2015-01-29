package io.bloc.android.blocly.api.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mark on 1/19/2015.
 */
public class GetFeedsNetworkRequest extends NetworkRequest {

    String [] feedUrls;

    public GetFeedsNetworkRequest(String... feedUrls) {
        this.feedUrls = feedUrls;
    }

    @Override
    public Object performRequest() {
        for (String feedUrlString : feedUrls) {
            InputStream inputStream = openStream(feedUrlString);
            if (inputStream == null) {
                return null;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = bufferedReader.readLine();
                int itemCount = 0;
                int titleTag = 7;
                int linkTag = 6;
                while (line != null) {
                    Log.v(getClass().getSimpleName(), "Line: " + line);
                    int index = 0;
                    while ((index = line.indexOf("<item>", index)) != -1) {
                        index++;

                        int startTitle = line.indexOf("<title>", index) + titleTag;
                        int endTitle = line.indexOf("</title>", index);
                        char [] title = new char[endTitle - startTitle];
                        line.getChars(startTitle, endTitle, title, 0);
                        String itemTitle = new String(title);
                        Log.i(getClass().getSimpleName(), itemTitle);

                        int startLink = line.indexOf("<link>", index) + linkTag;
                        int endLink = line.indexOf("</link>", index);
                        char [] link = new char[endLink - startLink];
                        line.getChars(startLink, endLink, link, 0);
                        String itemLink = new String(link);
                        Log.i(getClass().getSimpleName(), itemLink);

                        itemCount++;
                    }
                    line = bufferedReader.readLine();
                }
                Log.i(getClass().getSimpleName(), "Item Count: " + itemCount);
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                setErrorCode(ERROR_IO);
                return null;
            }
        }
        return null;
    }
}
