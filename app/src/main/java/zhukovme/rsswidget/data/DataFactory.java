package zhukovme.rsswidget.data;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import zhukovme.rsswidget.R;
import zhukovme.rsswidget.data.database.DatabaseHelperFactory;
import zhukovme.rsswidget.data.database.PreferenceHelper;
import zhukovme.rsswidget.sax.RssSaxParser;
import zhukovme.rsswidget.util.IntentUtil;
import zhukovme.rsswidget.util.StringUtil;
import zhukovme.rsswidget.util.TimeUtil;
import zhukovme.rsswidget.widget.RssFeedWidgetProvider;
import zhukovme.rsswidget.widget.RssWidgetUpdateTask;

/**
 * Created by Michael Zhukov on 20/11/2016
 */
public class DataFactory {

    public static StringUtil getStringUtil() {
        return new StringUtil();
    }

    public static IntentUtil getIntentUtil() {
        return new IntentUtil();
    }

    public static TimeUtil getTimeUtil() {
        return new TimeUtil();
    }

    public static RssSaxParser getRssSaxParser() {
        return new RssSaxParser(getTimeUtil());
    }

    public static PreferenceHelper getPreferenceHelper(Context context) {
        return new PreferenceHelper(context);
    }

    public static RssFeedRepository getRssFeedRepository(Context context) {
        return new RssFeedRepository(getPreferenceHelper(context), DatabaseHelperFactory.getHelper());
    }

    public static RssWidgetUpdateTask getRssWidgetUpdateTask(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_rss_feed);
        ComponentName widget = new ComponentName(context, RssFeedWidgetProvider.class);
        RssSaxParser parser = getRssSaxParser();
        RssFeedRepository rssFeedRepository = getRssFeedRepository(context);
        return new RssWidgetUpdateTask(manager, views, widget, parser, rssFeedRepository);
    }
}
