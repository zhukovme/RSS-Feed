package zhukovme.rsswidget.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import zhukovme.rsswidget.R;
import zhukovme.rsswidget.data.DataFactory;
import zhukovme.rsswidget.data.PreferenceHelper;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class RssFeedWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RssFeedWidgetProvider.class.getName();

    public static final String ACTION_OPEN_URL = "zhukovme.rsswidget.ui.widget.action_open_url";
    private static final String ACTION_SETTINGS = "zhukovme.rsswidget.ui.widget.action_settings";
    private static final String ACTION_SYNC = "zhukovme.rsswidget.ui.widget.action_sync";
    public static final String EXTRA_URL = "zhukovme.rsswidget.ui.widget.extra_url";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " + intent.getAction());
        if (ACTION_OPEN_URL.equals(intent.getAction())) {
            String url = intent.getStringExtra(EXTRA_URL);
            DataFactory.getIntentUtil().openUrl(context, url);
        } else if (ACTION_SYNC.equals(intent.getAction())) {
            // sync
        } else if (ACTION_SETTINGS.equals(intent.getAction())) {
            // settings
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate");
        PreferenceHelper prefHelper = DataFactory.getPreferenceHelper(context);
        String rssTitle = prefHelper.getRssTitle();
        for (int widgetId : appWidgetIds) {
            RemoteViews views = initRemoteViews(context, widgetId);

            views.setPendingIntentTemplate(R.id.lv_rss_feed, getPendingSelfIntent(context, ACTION_OPEN_URL));
            views.setOnClickPendingIntent(R.id.iv_settings, getPendingSelfIntent(context, ACTION_SETTINGS));
            views.setOnClickPendingIntent(R.id.iv_sync, getPendingSelfIntent(context, ACTION_SYNC));

            views.setTextViewText(R.id.tv_rss_title, rssTitle);

            appWidgetManager.updateAppWidget(widgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private RemoteViews initRemoteViews(Context context, int widgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_rss_feed_layout);
        Intent intent = new Intent(context, RssFeedWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            views.setRemoteAdapter(R.id.lv_rss_feed, intent);
        } else {
            views.setRemoteAdapter(widgetId, R.id.lv_rss_feed, intent);
        }
        return views;
    }
}
