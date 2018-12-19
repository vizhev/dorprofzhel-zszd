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

package pro.dprof.dorprofzhelzszd.ui.news_feed;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;
import pro.dprof.dorprofzhelzszd.utils.AppContent;

public final class NewsFeedPresenter<V extends NewsFeedMvpView> extends BasePresenter<V> implements NewsFeedMvpPresenter<V> {

    @Override
    public void onCreateAdapter() {
        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter();
        getMvpView().setAdapter(newsFeedAdapter);
    }

    @Override
    public void onSetContent(final boolean isRefresh) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<AppContent> contentList = getDataProvider().getNewsFeedContent(isRefresh);
                int retry = 0;
                do {
                    try {
                        getMvpView().setContent(contentList);
                        retry = 2;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    } finally {
                        retry++;
                    }
                } while (retry < 2);
            }
        });
        executorService.shutdown();
    }
}
