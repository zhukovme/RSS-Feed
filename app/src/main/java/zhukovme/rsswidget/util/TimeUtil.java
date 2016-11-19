package zhukovme.rsswidget.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Michael Zhukov on 19/11/2016
 */

public class TimeUtil {

    private static final String TAG = TimeUtil.class.getName();

    public static final String DEFAULT_PATTERN = "EEE, dd MMM yyyy HH:mm:ss";

    private TimeZone timeZone;

    public TimeUtil(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public long parse(String date) {
        return parse(date, DEFAULT_PATTERN);
    }

    public long parse(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        sdf.setTimeZone(timeZone);
        long time = 0L;
        try {
            time = sdf.parse(date).getTime();
        } catch (Exception e) {
            Log.e(TAG, "Parsing date error", e);
        }
        return time;
    }
}
