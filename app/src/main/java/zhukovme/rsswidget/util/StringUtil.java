package zhukovme.rsswidget.util;

import android.text.Html;

/**
 * Created by Michael Zhukov on 20/11/2016
 */

public class StringUtil {

    public String fromHtml(String source) {
        if (source == null) {
            return "";
        }
        String str = source.replaceAll("[<](/)?img[^>]*[>]", "");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(str).toString();
        }
    }
}
