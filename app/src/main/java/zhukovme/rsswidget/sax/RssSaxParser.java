package zhukovme.rsswidget.sax;

import android.support.annotation.Nullable;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import zhukovme.rsswidget.model.RssFeed;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class RssSaxParser {

    private static final String TAG = RssSaxParser.class.getName();

    private RssSaxHandler handler;

    public RssSaxParser(RssSaxHandler handler) {
        this.handler = handler;
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
