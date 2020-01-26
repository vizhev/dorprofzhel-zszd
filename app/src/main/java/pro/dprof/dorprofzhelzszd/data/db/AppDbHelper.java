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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pro.dprof.dorprofzhelzszd.domain.models.Documents;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class AppDbHelper implements DbHelper {

    private final OpenDbHelper mOpenDbHelper;
    private SQLiteDatabase mDatabase;

    public AppDbHelper(final Context context) {
        mOpenDbHelper = new OpenDbHelper(context);
    }

    private void openDb() {
        mDatabase = mOpenDbHelper.getReadableDatabase();
    }

    private void closeDb() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    @Override
    public List<Documents> getDocuments() {
        final ArrayList<Documents> documentsList = new ArrayList<>();
        final String[] columns = {
                "item_title",
                "activity_title",
                "asset_name"
        };
        openDb();
        for (int i = 0; i < Constants.SECTIONS.length; i++) {
            final String section = Constants.SECTIONS[i];
            //set title of DocumentsAdapter item
            final Documents documents = new Documents();
            documents.setSectionTitle(section);
            //set content of DocumentsAdapter item
            final List<String> itemTitles = new ArrayList<>();
            final List<String> activityTitles = new ArrayList<>();
            final List<String> assetsNames = new ArrayList<>();
            final String selection = "section = ?";
            final Cursor cursor = mDatabase.query(
                    OpenDbHelper.DB_TABLE_NAME_DOCUMENTS,
                    columns,
                    selection,
                    new String[]{section},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                do {
                    itemTitles.add(cursor.getString(cursor.getColumnIndex("item_title")));
                    activityTitles.add(cursor.getString(cursor.getColumnIndex("activity_title")));
                    assetsNames.add(cursor.getString(cursor.getColumnIndex("asset_name")));
                } while (cursor.moveToNext());
            }
            documents.setItemTitles(itemTitles);
            documents.setActivityTitles(activityTitles);
            documents.setAssetsNames(assetsNames);
            documentsList.add(documents);
            cursor.close();
        }
        closeDb();
        return documentsList;
    }
}
