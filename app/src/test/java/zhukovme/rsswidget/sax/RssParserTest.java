package zhukovme.rsswidget.sax;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.net.URL;

import zhukovme.rsswidget.TestDataFactory;
import zhukovme.rsswidget.data.model.RssFeed;
import zhukovme.rsswidget.util.TimeUtil;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
@RunWith(MockitoJUnitRunner.class)
public class RssParserTest {

    private RssSaxParser parser;

    @Mock TimeUtil timeUtil;

    @Before
    public void setUp() throws Exception {
        when(timeUtil.parse(anyString())).thenReturn(0L);
        parser = new RssSaxParser(timeUtil);
    }

    @After
    public void tearDown() throws Exception {
        parser = null;
    }

    @Test
    public void testParseOkStream() throws Exception {
        RssFeed expected = TestDataFactory.getTestOkRssFeed();
        String file = "xml/test_ok_rss.xml";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
        RssFeed actual = parser.parse(is);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseOkUrl() throws Exception {
        RssFeed expected = TestDataFactory.getTestOkRssFeed();
        String file = "xml/test_ok_rss.xml";
        URL url = this.getClass().getClassLoader().getResource(file);
        RssFeed actual = parser.parse(url);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseWrongXml() throws Exception {
        String file = "xml/test_wrong_rss.xml";
        URL url = this.getClass().getClassLoader().getResource(file);
        RssFeed actual = parser.parse(url);
        Assert.assertEquals(null, actual);
    }
}