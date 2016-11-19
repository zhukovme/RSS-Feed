package zhukovme.rsswidget.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class RssFeed {

    private String title;
    private String link;
    private String description;
    private List<RssItem> rssItems;

    public RssFeed() {
    }

    public RssFeed(String title, String link, String description, List<RssItem> rssItems) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.rssItems = rssItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RssItem> getRssItems() {
        return rssItems;
    }

    public void setRssItems(List<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    public void addRssItem(RssItem rssItem) {
        if (rssItems == null) {
            rssItems = new ArrayList<>();
        }
        rssItems.add(rssItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RssFeed rssFeed = (RssFeed) o;

        if (title != null ? !title.equals(rssFeed.title) : rssFeed.title != null) return false;
        if (link != null ? !link.equals(rssFeed.link) : rssFeed.link != null) return false;
        if (description != null ? !description.equals(rssFeed.description) : rssFeed.description != null) return false;
        return rssItems != null ? rssItems.equals(rssFeed.rssItems) : rssFeed.rssItems == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (rssItems != null ? rssItems.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RssFeed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", rssItems=" + rssItems +
                '}';
    }
}
