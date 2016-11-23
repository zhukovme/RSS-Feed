package zhukovme.rsswidget.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import zhukovme.rsswidget.data.DataFactory;
import zhukovme.rsswidget.data.RssFeedRepository;
import zhukovme.rsswidget.util.StringUtil;
import zhukovme.rsswidget.util.TimeUtil;

/**
 * Created by Michael Zhukov on 20/11/2016
 */
public class RssFeedWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        TimeUtil timeUtil = DataFactory.getTimeUtil();
        StringUtil stringUtil = DataFactory.getStringUtil();
        RssFeedRepository rssFeedRepository = DataFactory.getRssFeedRepository(getApplicationContext());
        return new RssFeedWidgetDataProvider(getApplicationContext(), intent, rssFeedRepository, timeUtil, stringUtil);
    }
}
