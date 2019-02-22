package pro.dprof.dorprofzhelzszd.di.components;

import dagger.Component;
import pro.dprof.dorprofzhelzszd.di.scopes.DocumentViewerActivityScope;
import pro.dprof.dorprofzhelzszd.di.modules.DocumentViewerActivityModule;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerMvpView;

@DocumentViewerActivityScope
@Component(modules = {DocumentViewerActivityModule.class})
public interface DocumentViewerActivityComponent {

    DocumentViewerMvpPresenter<DocumentViewerMvpView> getPresenter();
}
