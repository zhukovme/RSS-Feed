package zhukovme.rsswidget.data;

import android.content.Context;

import zhukovme.rsswidget.data.sax.RssSaxParser;
import zhukovme.rsswidget.util.IntentUtil;
import zhukovme.rsswidget.util.StringUtil;
import zhukovme.rsswidget.util.TimeUtil;

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

    public static DataManager getDataManager(Context context) {
        return new DataManager(getRssSaxParser(), getPreferenceHelper(context));
    }
}
