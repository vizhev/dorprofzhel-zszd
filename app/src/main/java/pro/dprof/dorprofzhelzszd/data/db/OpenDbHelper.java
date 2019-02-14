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

package pro.dprof.dorprofzhelzszd.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pro.dprof.dorprofzhelzszd.dataclasses.Documents;
import pro.dprof.dorprofzhelzszd.utils.Constants;

final class OpenDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database";
    static final String DB_TABLE_NAME_DOCUMENTS = "documents";

    OpenDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DB_TABLE_NAME_DOCUMENTS + "(" +
                "_id integer primary key autoincrement, " +
                "section text not null, " +
                "item_title text not null, " +
                "activity_title text not null, " +
                "asset_name text not null" + ");"
        );
        for (Documents documents : getInsertList()) {
            final List<String> itemTitles = documents.getItemTitles();
            final List<String> activityTitles = documents.getActivityTitles();
            final List<String> assetsNames = documents.getAssetsNames();
            final ContentValues contentValues = new ContentValues();
            for (int i = 0; i < itemTitles.size(); i++) {
                contentValues.put("section", documents.getSectionTitle());
                contentValues.put("item_title", itemTitles.get(i));
                contentValues.put("activity_title", activityTitles.get(i));
                contentValues.put("asset_name", assetsNames.get(i));
                db.insert(DB_TABLE_NAME_DOCUMENTS, null, contentValues);
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private List<Documents> getInsertList() {
        final List<Documents> insertList = new ArrayList<>();
        for (int i = 0; i < Constants.SECTIONS.length; i++) {
            final String sectionTitle = Constants.SECTIONS[i];
            final List<String> itemTitles = new ArrayList<>();
            final List<String> activityTitles = new ArrayList<>();
            final List<String> assetsNames = new ArrayList<>();
            switch (sectionTitle) {
                case Constants.SECTION_PLENARY:
                    Collections.addAll(itemTitles, Constants.DOC_SECTION_PLENARY_ITEM_TITLES);
                    Collections.addAll(activityTitles, Constants.DOC_SECTION_PLENARY_ACTIVITY_TITLES);
                    Collections.addAll(assetsNames, Constants.DOC_SECTION_PLENARY_ASSETS_NAMES);
                    break;
                case Constants.SECTION_ORGANIZATIONAL:
                    Collections.addAll(itemTitles, Constants.DOC_SECTION_ORGANIZATIONAL_ITEM_TITLES);
                    Collections.addAll(activityTitles, Constants.DOC_SECTION_ORGANIZATIONAL_ACTIVITY_TITLES);
                    Collections.addAll(assetsNames, Constants.DOC_SECTION_ORGANIZATIONAL_ASSETS_NAMES);
                    break;
                case Constants.SECTION_ECONOMIC:
                    Collections.addAll(itemTitles, Constants.DOC_SECTION_ECONOMIC_ITEM_TITLES);
                    Collections.addAll(activityTitles, Constants.DOC_SECTION_ECONOMIC_ACTIVITY_TITLES);
                    Collections.addAll(assetsNames, Constants.DOC_SECTION_ECONOMIC_ASSETS_NAMES);
                    break;
                case Constants.SECTION_SOCIAL:
                    Collections.addAll(itemTitles, Constants.DOC_SECTION_SOCIAL_ITEM_TITLES);
                    Collections.addAll(activityTitles, Constants.DOC_SECTION_SOCIAL_ACTIVITY_TITLES);
                    Collections.addAll(assetsNames, Constants.DOC_SECTION_SOCIAL_ASSETS_NAMES);
                    break;
                case Constants.SECTION_LEGAL:
                    Collections.addAll(itemTitles, Constants.DOC_SECTION_LEGAL_ITEM_TITLES);
                    Collections.addAll(activityTitles, Constants.DOC_SECTION_LEGAL_ACTIVITY_TITLES);
                    Collections.addAll(assetsNames, Constants.DOC_SECTION_LEGAL_ASSETS_NAMES);
                    break;
                case Constants.SECTION_TECHNICAL:
                    Collections.addAll(itemTitles, Constants.DOC_SECTION_TECHNICAL_ITEM_TITLES);
                    Collections.addAll(activityTitles, Constants.DOC_SECTION_TECHNICAL_ACTIVITY_TITLES);
                    Collections.addAll(assetsNames, Constants.DOC_SECTION_TECHNICAL_ASSETS_NAMES);
                    break;

            }
            final Documents documents = new Documents();
            documents.setSectionTitle(sectionTitle);
            documents.setItemTitles(itemTitles);
            documents.setActivityTitles(activityTitles);
            documents.setAssetsNames(assetsNames);
            insertList.add(documents);
        }
        return insertList;
    }
}
