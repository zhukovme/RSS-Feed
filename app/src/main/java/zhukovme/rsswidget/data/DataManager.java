package zhukovme.rsswidget.data;

import android.util.Log;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import zhukovme.rsswidget.data.model.RssFeed;
import zhukovme.rsswidget.data.model.RssItem;
import zhukovme.rsswidget.data.sax.RssSaxParser;

/**
 * Created by Michael Zhukov on 20/11/2016
 */
public class DataManager {

    private static final String TAG = DataManager.class.getName();

    private RssSaxParser parser;
    private PreferenceHelper prefHelper;

    public DataManager(RssSaxParser parser, PreferenceHelper prefHelper) {
        this.parser = parser;
        this.prefHelper = prefHelper;
    }

    public boolean isRssUrlSet() {
        return !prefHelper.getRssUrl().isEmpty();
    }

    public List<RssItem> loadRssItems() {
        RssFeed rssFeed = loadRssFeed();
        return rssFeed == null ? Collections.<RssItem>emptyList() : rssFeed.getRssItems();
    }

    public RssFeed loadRssFeed() {
        RssFeed rssFeed = null;
        try {
            URL url = new URL(prefHelper.getRssUrl());
            rssFeed = parser.parse(url);
        } catch (Exception e) {
            Log.e(TAG, "Error fetching rss from url", e);
        }
        if (rssFeed != null) {
            prefHelper.setRssTitle(rssFeed.getTitle());
            prefHelper.setRssDescription(rssFeed.getDescription());
        }
        return rssFeed;
    }
}
