package prabhat.x.spiker.rsstest2;

/**
 * Created by Spiker_x on 2/4/2017.
 */

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PcWorldRssParser {

    private static final String TAG_TITLE = "title";
    private static final String TAG_LINK = "link";
    private static final String TAG_RSS = "rss";
    private static final String TAG_DES = "des";

    // We don't use namespaces
    private final String ns = null;

    public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }

    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, TAG_RSS);
        String title = null;
        String link = null;
        String des=null;
        List<RssItem> items = new ArrayList<RssItem>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_TITLE)) {
                title = readTitle(parser);
            } else if (name.equals(TAG_LINK)) {
                link = readLink(parser);

            }
            else if (name.equals(TAG_DES))
            {
                des=readDes(parser);
            }
            if (title != null && link != null  && des !=null) {
                RssItem item = new RssItem(title, link,des);
                items.add(item);
                title = null;
                link = null;
                des=null;
            }
        }
        return items;
    }



    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_LINK);
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_LINK);
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_TITLE);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_TITLE);
        return title;
    }
    private String readDes(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_DES);
        String des = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_DES);
        return des;
    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}