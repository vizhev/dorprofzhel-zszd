/*
 * Copyright (C) 2018 Evgenii Vizhev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pro.dprof.dorprofzhelzszd.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pro.dprof.dorprofzhelzszd.data.DataProvider;
import pro.dprof.dorprofzhelzszd.di.ActivityContext;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgMvpView;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgPresenter;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerMvpView;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewerPresenter;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsMvpView;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsPresenter;
import pro.dprof.dorprofzhelzszd.ui.main.MainMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.main.MainMvpView;
import pro.dprof.dorprofzhelzszd.ui.main.MainPresenter;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedMvpView;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedPresenter;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostMvpView;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostPresenter;
import pro.dprof.dorprofzhelzszd.ui.note.NoteMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.note.NoteMvpView;
import pro.dprof.dorprofzhelzszd.ui.note.NotePresenter;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffMvpView;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffPresenter;

@Module
public class ActivityModule {

    private Context mContext;

    public ActivityModule(Context context) {
        mContext = context;
    }

    @Provides
    @ActivityContext
    Context provideActivityContext() {
        return mContext;
    }

    @Provides
    MainMvpPresenter<MainMvpView> provideMainPresenter(DataProvider dataProvider) {
        MainPresenter<MainMvpView> mainPresenter = new MainPresenter<>();
        mainPresenter.setDataProvider(dataProvider);
        return mainPresenter;
    }

    @Provides
    NewsFeedMvpPresenter<NewsFeedMvpView> provideNewsPresenter(DataProvider dataProvider) {
        NewsFeedPresenter<NewsFeedMvpView> newsFeedPresenter = new NewsFeedPresenter<>();
        newsFeedPresenter.setDataProvider(dataProvider);
        return newsFeedPresenter;
    }

    @Provides
    NewsPostMvpPresenter<NewsPostMvpView> provideNewsPostPresenter(DataProvider dataProvider) {
        NewsPostPresenter<NewsPostMvpView> newsPostPresenter = new NewsPostPresenter<>();
        newsPostPresenter.setDataProvider(dataProvider);
        return newsPostPresenter;
    }

    @Provides
    DocumentsMvpPresenter<DocumentsMvpView> provideDocumentsPresenter(DataProvider dataProvider) {
        DocumentsPresenter<DocumentsMvpView> documentsPresenter = new DocumentsPresenter<>();
        documentsPresenter.setDataProvider(dataProvider);
        return documentsPresenter;
    }

    @Provides
    NoteMvpPresenter<NoteMvpView> provideNotePresenter(DataProvider dataProvider) {
        NotePresenter<NoteMvpView> notePresenter = new NotePresenter<>();
        notePresenter.setDataProvider(dataProvider);
        return notePresenter;
    }

    @Provides
    AboutOrgMvpPresenter<AboutOrgMvpView> provideAboutOrgPresenter(DataProvider dataProvider) {
        AboutOrgPresenter<AboutOrgMvpView> aboutOrgPresenter = new AboutOrgPresenter<>();
        aboutOrgPresenter.setDataProvider(dataProvider);
        return aboutOrgPresenter;
    }

    @Provides
    StaffMvpPresenter<StaffMvpView> providePersonsPresenter(DataProvider dataProvider) {
        StaffPresenter<StaffMvpView> staffPresenter = new StaffPresenter<>();
        staffPresenter.setDataProvider(dataProvider);
        return staffPresenter;
    }


    @Provides
    DocumentViewerMvpPresenter<DocumentViewerMvpView> providePdfViewerPresenter() {
        return new DocumentViewerPresenter<>();
    }
}
