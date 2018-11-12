package pro.dprof.dorprofzhelzszd.utils;

import java.util.List;

public class AppData {

    private String mTitle;
    private String mText;
    private String mDate;
    private String mPostLink;
    private String mImageLink;
    private List<String> mDocItemTitles;
    private List<String> mDocActivityTitles;
    private List<String> mAssetNames;
    private boolean isExpanded = false;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getPostLink() {
        return mPostLink;
    }

    public void setPostLink(String mFullText) {
        this.mPostLink = mFullText;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public void setImageLink(String mUrlImage) {
        this.mImageLink = mUrlImage;
    }


    public List<String> getItemsTitles() {
        return mDocItemTitles;
    }

    public void setItemTitles(List<String> mDocumentsList) {
        this.mDocItemTitles = mDocumentsList;
    }

    public List<String> getActivitysTitles() {
        return mDocActivityTitles;
    }

    public void setActivityTitles(List<String> mDocActivityTitles) {
        this.mDocActivityTitles = mDocActivityTitles;
    }

    public List<String> getAssetsNames() {
        return mAssetNames;
    }

    public void setAssetsNames(List<String> mDocumentsLinksList) {
        this.mAssetNames = mDocumentsLinksList;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
