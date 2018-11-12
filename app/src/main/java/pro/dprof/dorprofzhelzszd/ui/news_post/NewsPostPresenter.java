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

package pro.dprof.dorprofzhelzszd.ui.news_post;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;
import pro.dprof.dorprofzhelzszd.utils.AppData;

public class NewsPostPresenter<V extends NewsPostMvpView> extends BasePresenter<V>
        implements NewsPostMvpPresenter<V> {

    private ExecutorService mExecutorService;
    private AppData appData;

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExecutorService != null && !mExecutorService.isShutdown()) {
            mExecutorService.shutdownNow();
        }
    }

    @Override
    public void onSetPostContent(final String postTitle, final String postLink, final String imageLink) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                if (appData == null) {
                    appData = getDataProvider().getNewsPostText(postLink);
                    appData.setTitle(postTitle);
                    appData.setImageLink(imageLink);
                }
                try {
                    getMvpView().setPostContent(appData);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        });
        mExecutorService.shutdown();
    }
}
