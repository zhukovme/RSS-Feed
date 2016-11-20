package zhukovme.rsswidget.data.sax;

import android.support.annotation.Nullable;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import zhukovme.rsswidget.data.model.RssFeed;
import zhukovme.rsswidget.util.TimeUtil;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class RssSaxParser {

    private static final String TAG = RssSaxParser.class.getName();

    private RssSaxHandler handler;

    public RssSaxParser(TimeUtil timeUtil) {
        this.handler = new RssSaxHandler(timeUtil);
    }

    @Nullable
    public RssFeed parse(URL url) throws SAXException, IOException {
        return parse(url.openStream());
    }

    @Nullable
    public RssFeed parse(InputStream stream) throws SAXException, IOException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            parser.parse(stream, handler);

            return handler.getResult();
        } catch (Exception e) {
            Log.e(TAG, "Error parsing rss", e);
            return null;
        }
    }
}
