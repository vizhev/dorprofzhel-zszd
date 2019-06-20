package pro.dprof.dorprofzhelzszd.domain.models;

public final class News {

    private String title;
    private String text;
    private String date;
    private String postLink;
    private String imageLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String mText) {
        this.text = mText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String mDate) {
        this.date = mDate;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String mFullText) {
        this.postLink = mFullText;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String mUrlImage) {
        this.imageLink = mUrlImage;
    }
}
