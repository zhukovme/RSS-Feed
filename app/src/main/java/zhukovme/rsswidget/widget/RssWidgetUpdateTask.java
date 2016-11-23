package zhukovme.rsswidget.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import java.net.URL;

import zhukovme.rsswidget.R;
import zhukovme.rsswidget.data.RssFeedRepository;
import zhukovme.rsswidget.data.model.RssFeed;
import zhukovme.rsswidget.sax.RssSaxParser;

/**
 * Created by Michael Zhukov on 22/11/2016
 */
public class RssWidgetUpdateTask extends AsyncTask<Void, Void, RssFeed> {

    private static final String TAG = RssWidgetUpdateTask.class.getName();

    private AppWidgetManager manager;
    private RemoteViews views;
    private ComponentName widget;
    private RssSaxParser parser;
    private RssFeedRepository rssFeedRepository;

    public RssWidgetUpdateTask(AppWidgetManager manager, RemoteViews views, ComponentName widget,
                               RssSaxParser parser, RssFeedRepository rssFeedRepository) {
        this.manager = manager;
        this.views = views;
        this.widget = widget;
        this.parser = parser;
        this.rssFeedRepository = rssFeedRepository;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // TODO: 11/22/2016 on start loading
    }

    @Override
    protected RssFeed doInBackground(Void... urls) {
        RssFeed rssFeed;
        try {
            String rssUrl = rssFeedRepository.getRssUrl();
            if (rssUrl.isEmpty()) {
                views.setTextViewText(R.id.tv_rss_title, "RSS URL is not yet set");
                return null;
            }
            URL url = new URL(rssUrl);
            rssFeed = parser.parse(url);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing rss url", e);
            views.setTextViewText(R.id.tv_rss_title, "Invalid RSS URL");
            return null;
        }
        if (rssFeed == null) {
            views.setTextViewText(R.id.tv_rss_title, "Invalid RSS URL");
            return null;
        }
        rssFeedRepository.saveRssFeed(rssFeed);
        return rssFeed;
    }

    @Override
    protected void onPostExecute(RssFeed rssFeed) {
        super.onPostExecute(rssFeed);
        if (rssFeed != null) {
            views.setTextViewText(R.id.tv_rss_title, rssFeed.getTitle());
        }
        manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(widget), R.id.lv_rss_feed);
        manager.updateAppWidget(widget, views);
    }
}
