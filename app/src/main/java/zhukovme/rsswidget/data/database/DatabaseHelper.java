package zhukovme.rsswidget.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zhukovme.rsswidget.data.database.dao.RssItemDao;
import zhukovme.rsswidget.data.database.table.RssItemTable;

/**
 * Created by Michael Zhukov on 23/11/2016
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "zhukovme.rsswidget.database";
    private static final int DATABASE_VERSION = 1;

    private RssItemDao rssItemDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(RssItemTable.CREATION_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL(RssItemTable.DROP_REQUEST);
        onCreate(database);
    }

    public RssItemDao getRssItemDao() {
        if (rssItemDao == null) {
            rssItemDao = new RssItemDao(getWritableDatabase());
        }
        return rssItemDao;
    }

    public void clear() {
        rssItemDao.deleteAll();
    }
}
