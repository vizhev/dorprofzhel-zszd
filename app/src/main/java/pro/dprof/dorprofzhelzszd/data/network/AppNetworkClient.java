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


package pro.dprof.dorprofzhelzszd.data.network;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pro.dprof.dorprofzhelzszd.data.prefs.PreferencesHelper;
import pro.dprof.dorprofzhelzszd.utils.AppContent;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class AppNetworkClient implements NetworkClient {

    private static final String URL_NEWS = "http://dprof.pro/news";
    private static final String URL_ABOUT_ORGANIZATION = "http://dprof.pro/razdel/ob-organizatsii/";
    private static final String URL_PERSONS = "http://dprof.pro/razdel/apparat-dorprofzhela/";
    private static Set<String> pageSet;
    private static int page = 0;

    private PreferencesHelper mPreferences;

    public AppNetworkClient(PreferencesHelper preferencesHelper) {
        if (pageSet == null) {
            pageSet = new LinkedHashSet<>();
            pageSet.add(URL_NEWS);
        }
        mPreferences = preferencesHelper;
    }

    @Override
    public List<AppContent> loadNewsFeed(boolean isRefresh) {
        List<AppContent> contentList = new ArrayList<>();
        if (isRefresh) {
            page = 0;
        }
        if (page < pageSet.size()) {
            String pageUrl = (String) pageSet.toArray()[page];
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future future = executorService.submit(newsFeedTask(pageUrl));
            try {
                contentList = (List<AppContent>) future.get();
                executorService.shutdown();
                page++;
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
                    executorService.shutdownNow();
                }
            }
        }
        return contentList;
    }

    private Callable newsFeedTask(final String pageUrl) {
        return new Callable() {
            @Override
            public Object call() throws Exception {
                List<AppContent> contentList = new ArrayList<>();
                Log.d("Feed", "Start load feed task. Page = " + page + " pageSet = " + pageSet.size());
                try {
                    Document document = Jsoup.connect(pageUrl).get();
                    Element feed = document.getElementsByClass("newsslider3").first();
                    Elements titles = feed.getElementsByClass("newsslider_title");
                    Elements texts = feed.getElementsByClass("newsslider_anons");
                    Elements contentLinks = feed.getElementsByClass("newsslider_link");
                    Elements imageLinks = feed.getElementsByClass("newsslider_img");
                    Log.d("Feed", "URL = " + pageUrl);
                    for (int i = 0; i < titles.size(); i++) {
                        AppContent appContent = new AppContent();
                        String title = titles.get(i).select("a").first().text();
                        String text = texts.get(i).text();
                        String date = titles.get(i).select("span").text();
                        String postLink = "http://dprof.pro" + contentLinks.get(i).select("a").attr("href");
                        String imageLink = "http://dprof.pro" + imageLinks.get(i).select("img").attr("src");
                        appContent.setTitle(title);
                        appContent.setText(text);
                        appContent.setDate(date);
                        appContent.setPostLink(postLink);
                        appContent.setImageLink(imageLink);
                        contentList.add(appContent);
                    }
                    if (page == 0) {
                        Element pageNavigator = document.getElementsByClass("modern-page-navigation").first();
                        Elements pages = pageNavigator.select("a");
                        for (int i = 0; i < pages.size(); i++) {
                            String pageUrl = "http://dprof.pro" + pages.get(i).attr("href");
                            pageSet.add(pageUrl);
                        }
                        Log.d("Feed", "pageSet = " + pageSet.size());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return contentList;
            }
        };
    }

    @Override
    public AppContent loadNewsPost(final String postLink) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(newsPostTask(postLink));
        AppContent appData = new AppContent();
        try {
            appData = (AppContent) future.get();
            executorService.shutdown();
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
                executorService.shutdownNow();
            }
        }
        return appData;
    }

    private Callable newsPostTask(final String postLink) {
        return new Callable() {
            @Override
            public Object call() throws Exception {
                AppContent appData = new AppContent();
                try {
                    Document document = Jsoup.connect(postLink).get();
                    try {
                        Element text = document.getElementsByClass("news-detail").first();
                        Element date = document.getElementsByClass("news-date-time").first();
                        appData.setText(text.html());
                        appData.setDate(date.text());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return appData;
            }
        };
    }

    @Override
    public List<AppContent> loadStaff() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(staffTask());
        List<AppContent> contentList = new ArrayList<>();
        try {
            contentList = (List<AppContent>) future.get();
            executorService.shutdown();
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
                executorService.shutdownNow();
            }
        }
        return contentList;
    }

    private Callable staffTask() {
        return new Callable() {
            @Override
            public Object call() throws Exception {
                List<AppContent> contentList = new ArrayList<>();
                Document document;
                try {
                    document = Jsoup.connect(URL_PERSONS).get();
                    if (!document.html().equals(mPreferences.getStaffDocument())) {
                        mPreferences.setStaffDocument(document.html());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    document = Jsoup.parse(mPreferences.getStaffDocument());
                }
                Element persons = document.getElementsByClass("razle_podtext").first();
                Elements images = persons.select("img");
                Elements texts = persons.select("tr").select("td").next();
                for (int i = 0; i < images.size(); i++) {
                    AppContent appData = new AppContent();
                    String imageLink = "http://dprof.pro" + images.get(i).attr("src");
                    String text = texts.get(i).html();
                    appData.setImageLink(imageLink);
                    appData.setText(text);
                    contentList.add(appData);
                }
                return contentList;
            }
        };
    }

    @Override
    public String loadAboutOrganizationText() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(aboutOrganizationTask());
        String text = Constants.MESSAGE_CONNECT_ERROR;
        try {
            text = (String) future.get();
            executorService.shutdown();
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
                executorService.shutdownNow();
            }
        }
        return text;
    }

    private Callable aboutOrganizationTask() {
        return new Callable() {
            @Override
            public Object call() throws Exception {
                Document document;
                try {
                    document = Jsoup.connect(URL_ABOUT_ORGANIZATION).get();
                    if (!document.html().equals(mPreferences.getAboutOrgDocument())) {
                        mPreferences.setAboutOrgDocument(document.html());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    document = Jsoup.parse(mPreferences.getAboutOrgDocument());
                }
                Element about = document.getElementsByClass("razle_podtext").first();
                return about.html();
            }
        };
    }
}
