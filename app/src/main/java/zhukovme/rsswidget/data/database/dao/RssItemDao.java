package zhukovme.rsswidget.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import zhukovme.rsswidget.data.database.table.RssItemTable;
import zhukovme.rsswidget.data.model.RssItem;

/**
 * Created by Michael Zhukov on 23/11/2016
 */
public class RssItemDao {

    private SQLiteDatabase database;

    public RssItemDao(SQLiteDatabase database) {
        this.database = database;
    }

    public List<RssItem> getAll() {
        Cursor cursor = database.query(
                RssItemTable.TABLE_NAME, null, null, null, null, null, RssItemTable.Column.PUB_DATE + " DESC");

        List<RssItem> rssItems = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return rssItems;
        }
        try {
            do {
                rssItems.add(fromCursor(cursor));
            } while (cursor.moveToNext());
        } finally {
            cursor.close();
        }
        return rssItems;
    }

    public long save(RssItem rssItem) {
        long row = -1;
        if (rssItem == null) {
            return row;
        }
        row = database.insertWithOnConflict(
                RssItemTable.TABLE_NAME, null, toContentValues(rssItem), SQLiteDatabase.CONFLICT_REPLACE);
        return row;
    }

    public void save(List<RssItem> rssItems) {
        if (rssItems != null) {
            for (RssItem rssItem : rssItems) {
                save(rssItem);
            }
        }
    }

    public void deleteAll() {
        database.delete(RssItemTable.TABLE_NAME, null, null);
    }

    @NonNull
    private ContentValues toContentValues(RssItem item) {
        ContentValues values = new ContentValues();
        values.put(RssItemTable.Column.GUID, item.getGuid());
        values.put(RssItemTable.Column.TITLE, item.getTitle());
        values.put(RssItemTable.Column.DESCRIPTION, item.getDescription());
        values.put(RssItemTable.Column.PUB_DATE, item.getPubDate());
        values.put(RssItemTable.Column.CATEGORY, item.getCategory());
        values.put(RssItemTable.Column.LINK, item.getLink());
        return values;
    }

    @NonNull
    private RssItem fromCursor(Cursor cursor) {
        String guid = cursor.getString(cursor.getColumnIndex(RssItemTable.Column.GUID));
        String title = cursor.getString(cursor.getColumnIndex(RssItemTable.Column.TITLE));
        String description = cursor.getString(cursor.getColumnIndex(RssItemTable.Column.DESCRIPTION));
        long pubDate = cursor.getLong(cursor.getColumnIndex(RssItemTable.Column.PUB_DATE));
        String category = cursor.getString(cursor.getColumnIndex(RssItemTable.Column.CATEGORY));
        String link = cursor.getString(cursor.getColumnIndex(RssItemTable.Column.LINK));
        return new RssItem(guid, title, description, pubDate, category, link);
    }
}
