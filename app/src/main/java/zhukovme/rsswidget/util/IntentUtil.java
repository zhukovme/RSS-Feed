package zhukovme.rsswidget.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Michael Zhukov on 20/11/2016
 */
public class IntentUtil {

    public void openUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
