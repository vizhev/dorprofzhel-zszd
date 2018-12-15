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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pro.dprof.dorprofzhelzszd.di.ApplicationContext;
import pro.dprof.dorprofzhelzszd.utils.AppContent;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class AppDbHelper implements DbHelper {

    private OpenDbHelper mOpenDbHelper;
    private SQLiteDatabase mDatabase;

    public AppDbHelper(@ApplicationContext Context context) {
        mOpenDbHelper = new OpenDbHelper(context);
    }

    private void openDb() {
        mDatabase = mOpenDbHelper.getWritableDatabase();
    }

    private void closeDb() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    @Override
    public List<AppContent> getDocuments() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(queryTask());
        Future future = executorService.submit(queryTask());
        List<AppContent> data = new ArrayList<>();
        try {
            data = (List<AppContent>) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (!executorService.isShutdown()) {
                try {
                    executorService.awaitTermination(3, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executorService.shutdown();
            }
        }
        return data;
    }

    private Callable queryTask() {
        return new Callable() {
            @Override
            public List<AppContent> call() throws Exception {
                ArrayList<AppContent> data = new ArrayList<>();
                openDb();
                String[] columns = {
                        "item_title",
                        "activity_title",
                        "asset_name"
                };
                for (int i = 0; i < Constants.SECTIONS.length; i++) {
                    String section = Constants.SECTIONS[i];
                    //set title of DocumentsAdapter item
                    AppContent appContent = new AppContent();
                    appContent.setTitle(section);
                    //load DocumentsAdapter item content from database
                    List<String> itemTitles = new ArrayList<>();
                    List<String> activityTitles = new ArrayList<>();
                    List<String> assetsNames = new ArrayList<>();
                    String selection = "section = ?";
                    Cursor cursor = mDatabase.query(
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
                    appContent.setItemTitles(itemTitles);
                    appContent.setActivityTitles(activityTitles);
                    appContent.setAssetsNames(assetsNames);
                    data.add(appContent);
                    cursor.close();
                }
                closeDb();
                return data;
            }
        };
    }


}
