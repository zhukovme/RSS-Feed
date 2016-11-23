package zhukovme.rsswidget.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zhukovme.rsswidget.R;
import zhukovme.rsswidget.data.RssFeedRepository;
import zhukovme.rsswidget.data.model.RssItem;
import zhukovme.rsswidget.util.StringUtil;
import zhukovme.rsswidget.util.TimeUtil;

/**
 * Created by Michael Zhukov on 20/11/2016
 */
public class RssFeedWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = RssFeedWidgetDataProvider.class.getName();

    private Context context;
    private RssFeedRepository rssFeedRepository;
    private TimeUtil timeUtil;
    private StringUtil stringUtil;

    private List<RssItem> rssItems;

    public RssFeedWidgetDataProvider(Context context, Intent intent, RssFeedRepository rssFeedRepository,
                                     TimeUtil timeUtil, StringUtil stringUtil) {
        this.context = context;
        this.rssFeedRepository = rssFeedRepository;
        this.timeUtil = timeUtil;
        this.stringUtil = stringUtil;

        rssItems = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        rssItems = rssFeedRepository.loadRssItems();
        Collections.sort(rssItems);
    }

    @Override
    public void onDataSetChanged() {
        Log.e(TAG, "onDataSetChanged");
        rssItems = rssFeedRepository.loadRssItems();
        Collections.sort(rssItems);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        rssItems.clear();
    }

    @Override
    public int getCount() {
        return rssItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews itemView = new RemoteViews(context.getPackageName(), R.layout.item_rss_feed);
        RssItem rssItem = rssItems.get(position);

        String title = stringUtil.fromHtml(rssItem.getTitle());
        String description = stringUtil.fromHtml(rssItem.getDescription());
        String pubDate = timeUtil.format(rssItem.getPubDate());

        itemView.setTextViewText(R.id.tv_title, title);
        itemView.setTextViewText(R.id.tv_description, description);
        itemView.setTextViewText(R.id.tv_pub_date, pubDate);

        final Intent intent = new Intent();
        intent.setAction(RssFeedWidgetProvider.ACTION_OPEN_URL);
        intent.putExtra(RssFeedWidgetProvider.EXTRA_URL, rssItem.getLink());
        itemView.setOnClickFillInIntent(R.id.rl_item_root, intent);

        return itemView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
