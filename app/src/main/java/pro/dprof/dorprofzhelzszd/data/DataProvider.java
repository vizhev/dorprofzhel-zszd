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
import pro.dprof.dorprofzhelzszd.utils.AppContent;

public final class DataProvider {

    PreferencesHelper preferences;
    DbHelper dbHelper;
    NetworkClient networkClient;

    public DataProvider(PreferencesHelper preferences, DbHelper dbHelper, NetworkClient networkClient) {
        this.preferences = preferences;
        this.dbHelper = dbHelper;
        this.networkClient = networkClient;

    }

    public List<AppContent> getNewsFeedContent(boolean isRefresh) {
        return networkClient.loadNewsFeed(isRefresh);
    }

    public AppContent getNewsPostText(String postLink) {
        return networkClient.loadNewsPost(postLink);
    }

    public List<AppContent> getDocuments() {
        return dbHelper.getDocuments();
    }

    public void setNoteState(String noteState) {
        preferences.setNoteState(noteState);
    }

    public String getNoteState() {
        return preferences.getNoteState();
    }

    public String getAboutOrganizationText() {
        return networkClient.loadAboutOrganizationText();
    }

    public List<AppContent> getStaffList() {
        return networkClient.loadStaff();
    }

}
