package zhukovme.rsswidget.data;

import java.util.List;

import zhukovme.rsswidget.data.database.DatabaseHelper;
import zhukovme.rsswidget.data.database.PreferenceHelper;
import zhukovme.rsswidget.data.database.dao.RssItemDao;
import zhukovme.rsswidget.data.model.RssFeed;
import zhukovme.rsswidget.data.model.RssItem;

/**
 * Created by Michael Zhukov on 23/11/2016
 */
public class RssFeedRepository {

    private PreferenceHelper prefHelper;
    private RssItemDao rssItemDao;

    public RssFeedRepository(PreferenceHelper prefHelper, DatabaseHelper database) {
        this.prefHelper = prefHelper;
        this.rssItemDao = database.getRssItemDao();
    }

    public List<RssItem> loadRssItems() {
        return rssItemDao.getAll();
    }

    public void saveRssItems(RssItem rssItem) {
        rssItemDao.save(rssItem);
    }

    public void saveRssItems(List<RssItem> rssItems) {
        rssItemDao.save(rssItems);
    }

    public void saveRssUrl(String rssUrl) {
        prefHelper.setRssUrl(rssUrl);
    }

    public String getRssUrl() {
        return prefHelper.getRssUrl();
    }

    public String getRssTitle() {
        return prefHelper.getRssTitle();
    }

    public String getRssDescription() {
        return prefHelper.getRssDescription();
    }

    public void saveRssFeed(RssFeed rssFeed) {
        prefHelper.setRssTitle(rssFeed.getTitle());
        prefHelper.setRssDescription(rssFeed.getDescription());
        rssItemDao.save(rssFeed.getRssItems());
    }
}
