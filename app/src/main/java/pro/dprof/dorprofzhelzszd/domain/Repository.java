package pro.dprof.dorprofzhelzszd.domain;

import java.util.List;

import pro.dprof.dorprofzhelzszd.domain.models.Documents;
import pro.dprof.dorprofzhelzszd.domain.models.News;
import pro.dprof.dorprofzhelzszd.domain.models.Staff;

public interface Repository {

    List<News> getNewsFeedContent(boolean isRefresh);

    News getNewsPostText(String postLink);

    String getAboutOrganizationText();

    List<Staff> getStaffList();

    List<Documents> getDocuments();

    void setNoteState(String noteState);

    String getNoteState();
}
