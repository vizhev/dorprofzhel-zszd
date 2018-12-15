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

package pro.dprof.dorprofzhelzszd.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class AppPreferencesHelper implements PreferencesHelper {

    private SharedPreferences mSharedPreferences;

    public AppPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(
                Constants.PREF_APP_STATE, Context.MODE_PRIVATE
        );
    }

    @Override
    public void setNoteState(String noteState) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.PREF_NOTE, noteState);
        editor.apply();
    }

    @Override
    public String getNoteState() {
        return mSharedPreferences.getString(Constants.PREF_NOTE, "");
    }

    @Override
    public void setAboutOrgDocument(String document) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.PREF_ABOUT_ORG, document);
        editor.apply();
    }

    @Override
    public String getAboutOrgDocument() {
        return mSharedPreferences.getString(Constants.PREF_ABOUT_ORG, "");
    }

    @Override
    public void setStaffDocument(String document) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.PREF_STAFF, document);
        editor.apply();
    }

    @Override
    public String getStaffDocument() {
        return mSharedPreferences.getString(Constants.PREF_STAFF, "");
    }
}