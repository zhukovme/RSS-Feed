package zhukovme.rsswidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import zhukovme.rsswidget.R;
import zhukovme.rsswidget.data.DataFactory;
import zhukovme.rsswidget.data.RssFeedRepository;
import zhukovme.rsswidget.data.database.DatabaseHelperFactory;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class RssFeedWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RssFeedWidgetProvider.class.getName();

    private static final String ACTION_SETTINGS = "zhukovme.rsswidget.widget.action_settings";
    private static final String ACTION_SYNC = "zhukovme.rsswidget.widget.action_sync";
    private static final String ACTION_APPLY = "zhukovme.rsswidget.widget.action_apply";
    public static final String ACTION_OPEN_URL = "zhukovme.rsswidget.widget.action_open_url";

    public static final String EXTRA_URL = "zhukovme.rsswidget.widget.extra_url";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: " + intent.getAction());
        if (ACTION_OPEN_URL.equals(intent.getAction())) {
            String url = intent.getStringExtra(EXTRA_URL);
            DataFactory.getIntentUtil().openUrl(context, url);
        } else if (ACTION_SYNC.equals(intent.getAction())) {
            DataFactory.getRssWidgetUpdateTask(context).execute();
        } else if (ACTION_SETTINGS.equals(intent.getAction())) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_rss_feed);
            showSettings(views);
            update(context, views);
        } else if (ACTION_APPLY.equals(intent.getAction())) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_rss_feed);
            showRssItems(views);
            update(context, views);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.e(TAG, "onEnabled");
        DatabaseHelperFactory.setHelper(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate");
        RssFeedRepository rssFeedRepository = DataFactory.getRssFeedRepository(context);
        for (int widgetId : appWidgetIds) {
            RemoteViews views = initRemoteViews(context, widgetId);

            views.setPendingIntentTemplate(R.id.lv_rss_feed, getPendingSelfIntent(context, ACTION_OPEN_URL));
            views.setOnClickPendingIntent(R.id.iv_settings, getPendingSelfIntent(context, ACTION_SETTINGS));
            views.setOnClickPendingIntent(R.id.iv_sync, getPendingSelfIntent(context, ACTION_SYNC));
            views.setOnClickPendingIntent(R.id.iv_apply, getPendingSelfIntent(context, ACTION_APPLY));

            views.setEmptyView(R.id.lv_rss_feed, R.id.tv_empty_here);
            views.setTextViewText(R.id.tv_rss_title, rssFeedRepository.getRssTitle());
            views.setTextViewText(R.id.tv_rss_url, rssFeedRepository.getRssUrl());
            views.setTextViewText(R.id.tv_rss_description, rssFeedRepository.getRssDescription());

            appWidgetManager.updateAppWidget(widgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.e(TAG, "onDeleted");
        DatabaseHelperFactory.releaseHelper();
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private RemoteViews initRemoteViews(Context context, int widgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_rss_feed);
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

    private void update(Context context, RemoteViews views) {
        ComponentName myWidget = new ComponentName(context, RssFeedWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, views);
    }

    private void showSettings(RemoteViews views) {
        views.setViewVisibility(R.id.iv_settings, View.GONE);
        views.setViewVisibility(R.id.iv_sync, View.GONE);
        views.setViewVisibility(R.id.iv_apply, View.VISIBLE);

        views.setViewVisibility(R.id.rl_rss_items, View.GONE);
        views.setViewVisibility(R.id.ll_settings, View.VISIBLE);
    }

    private void showRssItems(RemoteViews views) {
        views.setViewVisibility(R.id.iv_settings, View.VISIBLE);
        views.setViewVisibility(R.id.iv_sync, View.VISIBLE);
        views.setViewVisibility(R.id.iv_apply, View.GONE);

        views.setViewVisibility(R.id.rl_rss_items, View.VISIBLE);
        views.setViewVisibility(R.id.ll_settings, View.GONE);
    }
}
