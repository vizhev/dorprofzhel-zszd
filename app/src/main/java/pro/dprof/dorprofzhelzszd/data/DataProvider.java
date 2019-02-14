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

package pro.dprof.dorprofzhelzszd.data;

import java.util.List;

import pro.dprof.dorprofzhelzszd.data.db.DbHelper;
import pro.dprof.dorprofzhelzszd.data.network.NetworkClient;
import pro.dprof.dorprofzhelzszd.data.prefs.PreferencesHelper;
import pro.dprof.dorprofzhelzszd.dataclasses.Documents;
import pro.dprof.dorprofzhelzszd.dataclasses.News;
import pro.dprof.dorprofzhelzszd.dataclasses.Staff;

public final class DataProvider {

    private PreferencesHelper mPreferences;
    private DbHelper mDbHelper;
    private NetworkClient mNetworkClient;

    public DataProvider(PreferencesHelper preferences, DbHelper dbHelper, NetworkClient networkClient) {
        this.mPreferences = preferences;
        this.mDbHelper = dbHelper;
        this.mNetworkClient = networkClient;
    }

    public synchronized List<News> getNewsFeedContent(boolean isRefresh) {
        return mNetworkClient.loadNewsFeed(isRefresh);
    }

    public synchronized News getNewsPostText(String postLink) {
        return mNetworkClient.loadNewsPost(postLink);
    }

    public synchronized List<Documents> getDocuments() {
        return mDbHelper.getDocuments();
    }

    public synchronized void setNoteState(String noteState) {
        mPreferences.setNoteState(noteState);
    }

    public synchronized String getNoteState() {
        return mPreferences.getNoteState();
    }

    public synchronized String getAboutOrganizationText() {
        return mNetworkClient.loadAboutOrganizationText();
    }

    public synchronized List<Staff> getStaffList() {
        return mNetworkClient.loadStaff();
    }

}
