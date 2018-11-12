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

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pro.dprof.dorprofzhelzszd.data.DataProvider;
import pro.dprof.dorprofzhelzszd.data.db.AppDbHelper;
import pro.dprof.dorprofzhelzszd.data.db.DbHelper;
import pro.dprof.dorprofzhelzszd.data.network.AppNetworkClient;
import pro.dprof.dorprofzhelzszd.data.network.NetworkClient;
import pro.dprof.dorprofzhelzszd.data.prefs.AppPreferencesHelper;
import pro.dprof.dorprofzhelzszd.data.prefs.PreferencesHelper;
import pro.dprof.dorprofzhelzszd.di.ApplicationContext;

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Application application) {
        mContext = application.getApplicationContext();
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mContext;
    }

    @Provides
    PreferencesHelper providePreferencesHelper() {
        return new AppPreferencesHelper(mContext);
    }

    @Provides
    DbHelper provideDbHelper() {
        return new AppDbHelper(mContext);
    }

    @Provides
    NetworkClient provideConnect(PreferencesHelper preferencesHelper) {
        return new AppNetworkClient(preferencesHelper);
    }

    @Provides
    @Singleton
    DataProvider provideDataProvider(PreferencesHelper preferences, DbHelper dbHelper, NetworkClient networkClient) {
        return new DataProvider(preferences, dbHelper, networkClient);
    }
}
