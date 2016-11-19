package zhukovme.rsswidget.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import zhukovme.rsswidget.TestDataFactory;

/**
 * Created by Michael Zhukov on 19/11/2016
 */
public class TimeUtilTest {

    private TimeUtil timeUtil;

    @Before
    public void setUp() throws Exception {
        timeUtil = new TimeUtil(TestDataFactory.getMyTimeZone());
    }

    @After
    public void tearDown() throws Exception {
        timeUtil = null;
    }

    @Test
    public void testParseOk() throws Exception {
        String testDate = "Sat, 19 Nov 2016 01:37:28 -0500";
        long expected = 1479508648000L;
        long actual = timeUtil.parse(testDate);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseWithoutTZ() throws Exception {
        String testDate = "Sat, 19 Nov 2016 01:37:28";
        long expected = 1479508648000L;
        long actual = timeUtil.parse(testDate);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseWrong() throws Exception {
        String testDate = "Sat, 19/11/2016 01:37:28";
        long expected = 0L;
        long actual = timeUtil.parse(testDate);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseNull() throws Exception {
        long expected = 0L;
        long actual = timeUtil.parse(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseEmpty() throws Exception {
        long expected = 0L;
        long actual = timeUtil.parse("");
        Assert.assertEquals(expected, actual);
    }
}