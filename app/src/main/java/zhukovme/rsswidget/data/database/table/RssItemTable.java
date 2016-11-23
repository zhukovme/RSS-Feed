package zhukovme.rsswidget.data.database.table;

/**
 * Created by Michael Zhukov on 23/11/2016
 */
public class RssItemTable {

    public static final String TABLE_NAME = "rss_feed_item";

    public interface Column {
        String GUID = "guid";
        String TITLE = "title";
        String DESCRIPTION = "description";
        String PUB_DATE = "pub_date";
        String CATEGORY = "category";
        String LINK = "link";
    }

    public static final String CREATION_REQUEST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    Column.GUID + " TEXT PRIMARY KEY, " +
                    Column.TITLE + " TEXT, " +
                    Column.DESCRIPTION + " TEXT, " +
                    Column.PUB_DATE + " LONG, " +
                    Column.CATEGORY + " TEXT, " +
                    Column.LINK + " TEXT" +
                    ");";

    public static final String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
