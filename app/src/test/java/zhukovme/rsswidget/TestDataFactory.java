package zhukovme.rsswidget;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import zhukovme.rsswidget.model.RssFeed;
import zhukovme.rsswidget.model.RssItem;

/**
 * Created by Michael Zhukov on 19/11/2016
 */

public class TestDataFactory {

    public static TimeZone getMyTimeZone() {
        return TimeZone.getTimeZone("GMT+3:00");
    }

    public static RssFeed getTestOkRssFeed() {
        RssItem rssItem1 = new RssItem(
                "item_001",
                "Item 1 title",
                "Item 1 description",
                0L,
                "Item 1 All",
                "http://item_1.link/"
        );
        RssItem rssItem2 = new RssItem(
                "item_002",
                "Item 2 title",
                "Item 2 description",
                0L,
                "Item 2 All",
                "http://item_2.link/"
        );
        List<RssItem> items = new ArrayList<>();
        items.add(rssItem1);
        items.add(rssItem2);
        return new RssFeed(
                "RSS Title",
                "RSS Link",
                "RSS description",
                items
        );
    }
}
