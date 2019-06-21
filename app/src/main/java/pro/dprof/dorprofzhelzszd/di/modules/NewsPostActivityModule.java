package pro.dprof.dorprofzhelzszd.di.modules;

import dagger.Module;
import dagger.Provides;
import pro.dprof.dorprofzhelzszd.di.scopes.NewsPostActivityScope;
import pro.dprof.dorprofzhelzszd.domain.Repository;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpView;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostPresenter;

@Module
public class NewsPostActivityModule {

    @Provides
    @NewsPostActivityScope
    NewsPostMvpPresenter<NewsPostMvpView> provideNewsPostPresenter(Repository repository) {
        final NewsPostPresenter<NewsPostMvpView> newsPostPresenter = new NewsPostPresenter<>();
        newsPostPresenter.setRepository(repository);
        return newsPostPresenter;
    }

}
