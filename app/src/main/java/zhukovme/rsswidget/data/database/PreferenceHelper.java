package zhukovme.rsswidget.data.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Michael Zhukov on 20/11/2016
 */

public class PreferenceHelper {

    private static final String PREF_FILE_NAME = "rss_widget_pref_file";

    private static final String RSS_URL_PREF = "rss_url_pref";
    private static final String RSS_TITLE_PREF = "rss_title_pref";
    private static final String RSS_DESCRIPTION_PREF = "rss_description_pref";

    private final SharedPreferences prefs;

    public PreferenceHelper(Context context) {
        this.prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        prefs.edit().clear().apply();
    }

    public void setRssUrl(String url) {
        prefs.edit()
                .putString(RSS_URL_PREF, url)
                .apply();
    }

    public String getRssUrl() {
        return prefs.getString(RSS_URL_PREF, "http://news.yahoo.com/rss/");
    }

    public void setRssTitle(String title) {
        prefs.edit()
                .putString(RSS_TITLE_PREF, title)
                .apply();
    }

    public String getRssTitle() {
        return prefs.getString(RSS_TITLE_PREF, "");
    }

    public void setRssDescription(String description) {
        prefs.edit()
                .putString(RSS_DESCRIPTION_PREF, description)
                .apply();
    }

    public String getRssDescription() {
        return prefs.getString(RSS_DESCRIPTION_PREF, "No description");
    }
}
