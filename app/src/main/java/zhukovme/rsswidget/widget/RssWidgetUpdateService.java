package zhukovme.rsswidget.widget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import zhukovme.rsswidget.data.DataFactory;

/**
 * Created by Michael Zhukov on 21/11/2016
 */
public class RssWidgetUpdateService extends Service {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, RssWidgetUpdateService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DataFactory.getRssWidgetUpdateTask(this).execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
