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

    private final PreferencesHelper mPreferences;
    private final DbHelper mDbHelper;
    private final NetworkClient mNetworkClient;

    public DataProvider(PreferencesHelper preferences, DbHelper dbHelper, NetworkClient networkClient) {
        this.mPreferences = preferences;
        this.mDbHelper = dbHelper;
        this.mNetworkClient = networkClient;
    }

    public List<News> getNewsFeedContent(boolean isRefresh) {
        synchronized (mNetworkClient) {
            return mNetworkClient.loadNewsFeed(isRefresh);
        }
    }

    public News getNewsPostText(String postLink) {
        synchronized (mNetworkClient) {
            return mNetworkClient.loadNewsPost(postLink);
        }
    }

    public String getAboutOrganizationText() {
        synchronized (mNetworkClient) {
            return mNetworkClient.loadAboutOrganizationText();
        }
    }

    public List<Staff> getStaffList() {
        synchronized (mNetworkClient) {
            return mNetworkClient.loadStaff();
        }
    }

    public List<Documents> getDocuments() {
        synchronized (mDbHelper) {
            return mDbHelper.getDocuments();
        }
    }

    public void setNoteState(String noteState) {
        mPreferences.setNoteState(noteState);
    }

    public String getNoteState() {
        return mPreferences.getNoteState();
    }
}
