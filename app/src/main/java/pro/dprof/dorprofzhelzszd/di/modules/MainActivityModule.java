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

import dagger.Module;
import dagger.Provides;
import pro.dprof.dorprofzhelzszd.data.DataProvider;
import pro.dprof.dorprofzhelzszd.di.scopes.MainActivityScope;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgMvpView;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgPresenter;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsMvpView;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsPresenter;
import pro.dprof.dorprofzhelzszd.ui.main.MainMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.main.MainMvpView;
import pro.dprof.dorprofzhelzszd.ui.main.MainPresenter;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedMvpView;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedPresenter;
import pro.dprof.dorprofzhelzszd.ui.note.NoteMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.note.NoteMvpView;
import pro.dprof.dorprofzhelzszd.ui.note.NotePresenter;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffMvpView;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffPresenter;
import pro.dprof.dorprofzhelzszd.ui.usefullinks.UsefulLinksMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.usefullinks.UsefulLinksMvpView;
import pro.dprof.dorprofzhelzszd.ui.usefullinks.UsefulLinksPresenter;

@Module
public class MainActivityModule {

    @Provides
    @MainActivityScope
    MainMvpPresenter<MainMvpView> provideMainPresenter(DataProvider dataProvider) {
        final MainPresenter<MainMvpView> mainPresenter = new MainPresenter<>();
        mainPresenter.setDataProvider(dataProvider);
        return mainPresenter;
    }

    @Provides
    @MainActivityScope
    NewsFeedMvpPresenter<NewsFeedMvpView> provideNewsPresenter(DataProvider dataProvider) {
        final NewsFeedPresenter<NewsFeedMvpView> newsFeedPresenter = new NewsFeedPresenter<>();
        newsFeedPresenter.setDataProvider(dataProvider);
        return newsFeedPresenter;
    }

    @Provides
    @MainActivityScope
    DocumentsMvpPresenter<DocumentsMvpView> provideDocumentsPresenter(DataProvider dataProvider) {
        final DocumentsPresenter<DocumentsMvpView> documentsPresenter = new DocumentsPresenter<>();
        documentsPresenter.setDataProvider(dataProvider);
        return documentsPresenter;
    }

    @Provides
    @MainActivityScope
    NoteMvpPresenter<NoteMvpView> provideNotePresenter(DataProvider dataProvider) {
        final NotePresenter<NoteMvpView> notePresenter = new NotePresenter<>();
        notePresenter.setDataProvider(dataProvider);
        return notePresenter;
    }

    @Provides
    @MainActivityScope
    AboutOrgMvpPresenter<AboutOrgMvpView> provideAboutOrgPresenter(DataProvider dataProvider) {
        final AboutOrgPresenter<AboutOrgMvpView> aboutOrgPresenter = new AboutOrgPresenter<>();
        aboutOrgPresenter.setDataProvider(dataProvider);
        return aboutOrgPresenter;
    }

    @Provides
    @MainActivityScope
    StaffMvpPresenter<StaffMvpView> providePersonsPresenter(DataProvider dataProvider) {
        final StaffPresenter<StaffMvpView> staffPresenter = new StaffPresenter<>();
        staffPresenter.setDataProvider(dataProvider);
        return staffPresenter;
    }

    @Provides
    @MainActivityScope
    UsefulLinksMvpPresenter<UsefulLinksMvpView> provideUsefulLinksPresenter(DataProvider dataProvider) {
        final UsefulLinksPresenter<UsefulLinksMvpView> usefulLinksPresenter = new UsefulLinksPresenter<>();
        usefulLinksPresenter.setDataProvider(dataProvider);
        return usefulLinksPresenter;
    }
}
