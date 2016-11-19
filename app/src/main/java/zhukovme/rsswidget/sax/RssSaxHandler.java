package zhukovme.rsswidget.sax;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

import zhukovme.rsswidget.model.RssFeed;
import zhukovme.rsswidget.model.RssItem;
import zhukovme.rsswidget.util.TimeUtil;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
class RssSaxHandler extends DefaultHandler {

    private static final String TAG = RssSaxHandler.class.getName();

    private TimeUtil timeUtil;

    private StringBuilder value = new StringBuilder();
    private Stack<String> tagsStack = new Stack<>();
    private String skippingElement;
    private boolean skip;

    private RssFeed rssFeed;
    private RssItem rssItem;

    public RssSaxHandler(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }

    @Override
    public void startDocument() throws SAXException {
        tagsStack.push("");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tagsStack.push(qName);
        value.setLength(0);
        if (RssTag.CHANNEL.equals(qName)) {
            rssFeed = new RssFeed();
        } else if (RssTag.ITEM.equals(qName)) {
            rssItem = new RssItem();
        }
        startSkipIfUnknown(qName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (!skip) {
            value.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String tag = tagsStack.pop();
        if (!qName.equals(tag)) {
            Log.d(TAG, "RSS parse error");
            return;
        }
        String parenTag = tagsStack.peek();
        if (!skip) {
            if (rssFeed != null && rssItem == null) {
                mapRssFeedTags(tag, parenTag);
            } else if (rssItem != null) {
                mapRssItemTags(tag);
            }
            if (rssFeed != null && RssTag.ITEM.equals(tag)) {
                rssFeed.addRssItem(rssItem);
            }
        }
        stopSkipUnknown(tag);
    }

    @Override
    public void endDocument() throws SAXException {
        value = null;
        rssItem = null;
    }

    public RssFeed getResult() {
        return rssFeed;
    }

    private void startSkipIfUnknown(String element) {
        if (skippingElement == null && !RssTag.contains(element)) {
            skippingElement = element;
            skip = true;
        }
    }

    private void stopSkipUnknown(String element) {
        if (skippingElement != null && skippingElement.equals(element)) {
            skip = false;
            skippingElement = null;
        }
    }

    private void mapRssFeedTags(String tag, String parentTag) {
        if (RssTag.CHANNEL.equals(parentTag)) {
            if (RssTag.TITLE.equals(tag)) {
                rssFeed.setTitle(value.toString());
            } else if (RssTag.LINK.equals(tag)) {
                rssFeed.setLink(value.toString());
            } else if (RssTag.DESCRIPTION.equals(tag)) {
                rssFeed.setDescription(value.toString());
            }
        }
    }

    private void mapRssItemTags(String tag) {
        if (RssTag.GUID.equals(tag)) {
            rssItem.setGuid(value.toString());
        } else if(RssTag.TITLE.equals(tag)) {
            rssItem.setTitle(value.toString());
        } else if(RssTag.DESCRIPTION.equals(tag)) {
            rssItem.setDescription(value.toString());
        } else if(RssTag.PUB_DATE.equals(tag)) {
            rssItem.setPubDate(parseDate(value.toString()));
        } else if(RssTag.CATEGORY.equals(tag)) {
            rssItem.setCategory(value.toString());
        } else if(RssTag.LINK.equals(tag)) {
            rssItem.setLink(value.toString());
        }
    }

    private long parseDate(String date) {
        return timeUtil.parse(date);
    }

    private enum RssTag {
        RSS("rss"),
        CHANNEL("channel"),
        ITEM("item"),
        GUID("guid"),
        TITLE("title"),
        LINK("link"),
        DESCRIPTION("description"),
        PUB_DATE("pubDate"),
        CATEGORY("category");

        private String value;

        RssTag(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static boolean contains(String tag) {
            for (RssTag rssTag : RssTag.values()) {
                if (rssTag.equals(tag)) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(String tag) {
            return getValue().equals(tag);
        }
    }
}
