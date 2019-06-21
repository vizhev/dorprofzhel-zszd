package pro.dprof.dorprofzhelzszd.di.components;

import dagger.Component;
import pro.dprof.dorprofzhelzszd.di.modules.NewsPostActivityModule;
import pro.dprof.dorprofzhelzszd.di.scopes.NewsPostActivityScope;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpView;

@NewsPostActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {NewsPostActivityModule.class})
public interface NewsPostActivityComponent {

    NewsPostMvpPresenter<NewsPostMvpView> getPresenter();
}
