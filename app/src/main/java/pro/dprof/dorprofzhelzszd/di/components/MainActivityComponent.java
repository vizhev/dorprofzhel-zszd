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

package pro.dprof.dorprofzhelzszd.di.components;

import dagger.Component;
import pro.dprof.dorprofzhelzszd.di.modules.MainActivityModule;
import pro.dprof.dorprofzhelzszd.di.scopes.MainActivityScope;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgMvpView;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsMvpView;
import pro.dprof.dorprofzhelzszd.ui.main.MainMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.main.MainMvpView;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedMvpView;
import pro.dprof.dorprofzhelzszd.ui.note.NoteMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.note.NoteMvpView;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffMvpPresenter;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffMvpView;

@MainActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

    MainMvpPresenter<MainMvpView> getMainPresenter();

    NewsFeedMvpPresenter<NewsFeedMvpView> getNewsFeedPresenter();

    DocumentsMvpPresenter<DocumentsMvpView> getDocumentsPresenter();

    NoteMvpPresenter<NoteMvpView> getNotePresenter();

    StaffMvpPresenter<StaffMvpView> getStaffPresenter();

    AboutOrgMvpPresenter<AboutOrgMvpView> getAboutOrgPresenter();

}
