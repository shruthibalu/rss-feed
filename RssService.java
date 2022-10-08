package prabhat.x.spiker.rsstest2;

/**
 * Created by Spiker_x on 2/4/2017.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class RssService extends IntentService {

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    private static final String RSS_LINK = "";

    public RssService() {
        super("RssService");
    }

    protected void onHandleIntent(Intent intent) {
        Log.d(Constants.TAG, "Service started");
        List<RssItem> rssItems = null;
        try {
            rssItems = new PcWorldRssParser().parse(getInputStream(RSS_LINK));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e2) {
            Log.w(e2.getMessage(), e2);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ((ResultReceiver) intent.getParcelableExtra(RECEIVER)).send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            return new URL(link).openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(Constants.TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}


