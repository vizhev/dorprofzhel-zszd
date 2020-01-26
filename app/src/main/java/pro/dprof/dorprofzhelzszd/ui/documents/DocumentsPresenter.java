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
import java.util.concurrent.TimeUnit;

import pro.dprof.dorprofzhelzszd.domain.models.Documents;
import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;
import pro.dprof.dorprofzhelzszd.domain.TaskExecutor;

public final class DocumentsPresenter<V extends DocumentsMvpView> extends BasePresenter<V>
        implements DocumentsMvpPresenter<V> {

    private final DocumentsAdapter mAdapter = new DocumentsAdapter(this::onShowDocument);

    @Override
    public void onSetAdapter() {
        getMvpView().setAdapter(mAdapter);
    }

    @Override
    public void onSetContent() {
        TaskExecutor.submitRunnable(() -> {
            final List<Documents> contentList = getRepository().getDocuments();
            synchronized (mAdapter) {
                mAdapter.setContentList(contentList);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TaskExecutor.handleCallback(() -> getMvpView().showContent());
        });
    }

    public boolean isNeedLoadingContent() {
        return mAdapter.getItemCount() == 0;
    }

    private void onShowDocument(final String assetName, final String title) {
        getMvpView().showDocument(assetName, title);
    }
}
