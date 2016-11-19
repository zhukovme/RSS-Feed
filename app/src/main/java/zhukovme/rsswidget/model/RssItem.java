package zhukovme.rsswidget.model;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class RssItem {

    private String guid;
    private String title;
    private String description;
    private long pubDate;
    private String category;
    private String link;

    public RssItem() {
    }

    public RssItem(String guid, String title, String description, long pubDate, String category, String link) {
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.category = category;
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPubDate() {
        return pubDate;
    }

    public void setPubDate(long pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RssItem rssItem = (RssItem) o;

        if (guid != null ? !guid.equals(rssItem.guid) : rssItem.guid != null) return false;
        if (title != null ? !title.equals(rssItem.title) : rssItem.title != null) return false;
        if (description != null ? !description.equals(rssItem.description) : rssItem.description != null) return false;
        if (pubDate != rssItem.pubDate) return false;
        if (category != null ? !category.equals(rssItem.category) : rssItem.category != null) return false;
        return link != null ? link.equals(rssItem.link) : rssItem.link == null;

    }

    @Override
    public int hashCode() {
        int result = guid != null ? guid.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (pubDate ^ (pubDate >>> 32));
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "guid='" + guid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                ", category='" + category + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}