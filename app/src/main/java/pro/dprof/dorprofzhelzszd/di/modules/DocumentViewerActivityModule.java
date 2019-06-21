package pro.dprof.dorprofzhelzszd.di.modules;

import dagger.Module;
import dagger.Provides;
import pro.dprof.dorprofzhelzszd.di.scopes.DocumentViewerActivityScope;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerMvpView;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerPresenter;

@Module
public class DocumentViewerActivityModule {

    @Provides
    @DocumentViewerActivityScope
    DocumentViewerMvpPresenter<DocumentViewerMvpView> providePdfViewerPresenter() {
        return new DocumentViewerPresenter<>();
    }
}
