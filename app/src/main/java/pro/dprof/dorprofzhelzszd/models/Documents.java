package pro.dprof.dorprofzhelzszd.models;

import java.util.List;

public final class Documents {

    private String sectionTitle;
    private List<String> itemTitles;
    private List<String> activityTitles;
    private List<String> assetsNames;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String mSectionTitle) {
        this.sectionTitle = mSectionTitle;
    }

    public List<String> getItemTitles() {
        return itemTitles;
    }

    public void setItemTitles(List<String> mDocumentsItemTitles) {
        this.itemTitles = mDocumentsItemTitles;
    }

    public List<String> getActivityTitles() {
        return activityTitles;
    }

    public void setActivityTitles(List<String> mDocumentsActivityTitles) {
        this.activityTitles = mDocumentsActivityTitles;
    }

    public List<String> getAssetsNames() {
        return assetsNames;
    }

    public void setAssetsNames(List<String> mAssetsNames) {
        this.assetsNames = mAssetsNames;
    }
}
