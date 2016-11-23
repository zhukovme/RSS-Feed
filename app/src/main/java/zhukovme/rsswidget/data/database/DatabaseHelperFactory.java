package zhukovme.rsswidget.data.database;

import android.content.Context;

/**
 * Created by Michael Zhukov on 23/11/2016
 */

public class DatabaseHelperFactory {

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void setHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static void releaseHelper() {
        databaseHelper.close();
        databaseHelper = null;
    }
}
