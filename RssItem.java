package prabhat.x.spiker.rsstest2;

/**
 * Created by Spiker_x on 2/4/2017.
 */

public class RssItem {

    private final String title;
    private final String link;
    private final String des;

    public RssItem(String title, String link,String des) {
        this.title = title;
        this.link = link;
        this.des=des;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDes() {
        return des;
    }
}
