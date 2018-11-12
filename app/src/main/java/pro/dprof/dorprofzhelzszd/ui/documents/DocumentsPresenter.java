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

package pro.dprof.dorprofzhelzszd.ui.documents;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pro.dprof.dorprofzhelzszd.ui.adapters.DocumentsAdapter;
import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;
import pro.dprof.dorprofzhelzszd.utils.AppData;

public class DocumentsPresenter<V extends DocumentsMvpView> extends BasePresenter<V>
        implements DocumentsMvpPresenter<V> {

    private ExecutorService mExecutorService;

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExecutorService != null && !mExecutorService.isShutdown()) {
            mExecutorService.shutdownNow();
        }
    }

    @Override
    public void onSetAdapter() {
        mExecutorService = Executors.newSingleThreadExecutor();
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                List<AppData> documents = getDataProvider().getDocuments();
                DocumentsAdapter documentsAdapter = new DocumentsAdapter(documents);
                getMvpView().setAdapter(documentsAdapter);
            }
        });
        mExecutorService.shutdown();
    }
}
