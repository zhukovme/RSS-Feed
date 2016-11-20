package zhukovme.rsswidget.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import zhukovme.rsswidget.data.DataFactory;
import zhukovme.rsswidget.data.DataManager;
import zhukovme.rsswidget.util.StringUtil;
import zhukovme.rsswidget.util.TimeUtil;

/**
 * Created by Michael Zhukov on 20/11/2016
 */
public class RssFeedWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        DataManager dataManager = DataFactory.getDataManager(getApplicationContext());
        TimeUtil timeUtil = DataFactory.getTimeUtil();
        StringUtil stringUtil = DataFactory.getStringUtil();
        return new RssFeedWidgetDataProvider(getApplicationContext(), intent, dataManager, timeUtil, stringUtil);
    }
}
