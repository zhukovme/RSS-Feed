package zhukovme.rsswidget.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.net.URL;

import zhukovme.rsswidget.data.model.RssFeed;
import zhukovme.rsswidget.data.sax.RssSaxParser;

import static android.content.ContentValues.TAG;

/**
 * Created by Michael Zhukov on 20/11/2016
 */

public class RssFeedLoader extends AsyncTaskLoader<RssFeed> {

    private String rssUrl;
    private RssSaxParser parser;

    public RssFeedLoader(Context context, String rssUrl, RssSaxParser parser) {
        super(context);
        this.rssUrl = rssUrl;
        this.parser = parser;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public RssFeed loadInBackground() {
        RssFeed rssFeed = null;
        try {
            URL url = new URL(rssUrl);
            rssFeed = parser.parse(url);
        } catch (Exception e) {
            Log.e(TAG, "Error fetching rss from url", e);
        }
        return rssFeed;
    }
}
