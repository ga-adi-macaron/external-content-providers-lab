package generalassembly.yuliyakaleda.calendarprovider;

/**
 * Created by drewmahrt on 11/22/16.
 */

public class CalendarEvent {
    private long mId;
    private String mTitle;
    private long mDate;

    public CalendarEvent(long id, String title, long date) {
        mId = id;
        mTitle = title;
        mDate = date;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
