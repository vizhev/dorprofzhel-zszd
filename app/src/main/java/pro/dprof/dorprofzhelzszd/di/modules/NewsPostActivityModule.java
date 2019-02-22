package pro.dprof.dorprofzhelzszd.di.modules;

import dagger.Module;
import dagger.Provides;
import pro.dprof.dorprofzhelzszd.data.DataProvider;
import pro.dprof.dorprofzhelzszd.di.scopes.NewsPostActivityScope;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpView;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostPresenter;

@Module
public class NewsPostActivityModule {

    @Provides
    @NewsPostActivityScope
    NewsPostMvpPresenter<NewsPostMvpView> provideNewsPostPresenter(DataProvider dataProvider) {
        final NewsPostPresenter<NewsPostMvpView> newsPostPresenter = new NewsPostPresenter<>();
        newsPostPresenter.setDataProvider(dataProvider);
        return newsPostPresenter;
    }

}
