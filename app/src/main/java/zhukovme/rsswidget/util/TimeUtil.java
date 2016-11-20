package zhukovme.rsswidget.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class TimeUtil {

    private static final String TAG = TimeUtil.class.getName();

    public static final String DEFAULT_PATTERN = "EEE, dd MMM yyyy HH:mm:ss";
    private static final java.lang.String DEFAULT_UI_PATTERN = "dd.MM.yyyy HH:mm";

    public TimeZone getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        String offset = String.format(
                Locale.getDefault(),
                "%02d:%02d",
                Math.abs(offsetInMillis / 3600000) - 1, Math.abs((offsetInMillis / 60000) % 60)
        );
        offset = "GMT"+(offsetInMillis >= 0 ? "+" : "-") + offset;
        return TimeZone.getTimeZone(offset);
    }

    public long parse(String date) {
        return parse(date, DEFAULT_PATTERN);
    }

    public long parse(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        sdf.setTimeZone(getTimeZone());
        long time = 0L;
        try {
            time = sdf.parse(date).getTime();
        } catch (Exception e) {
            Log.e(TAG, "Parsing date error", e);
        }
        return time;
    }

    public String format(long dateInMillis) {
        return format(dateInMillis, DEFAULT_UI_PATTERN);
    }

    public String format(long dateInMillis, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date(dateInMillis));
    }
}
