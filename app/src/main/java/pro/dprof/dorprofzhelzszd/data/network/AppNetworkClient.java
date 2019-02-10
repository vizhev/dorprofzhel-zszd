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

import pro.dprof.dorprofzhelzszd.data.prefs.PreferencesHelper;
import pro.dprof.dorprofzhelzszd.utils.AppContent;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class AppNetworkClient implements NetworkClient {

    private static final String URL_NEWS = "http://dprof.pro/news";
    private static final String URL_ABOUT_ORGANIZATION = "http://dprof.pro/razdel/ob-organizatsii/";
    private static final String URL_PERSONS = "http://dprof.pro/razdel/apparat-dorprofzhela/";
    private static final Set<String> pageSet = new LinkedHashSet<>();
    private static int page = 0;

    private PreferencesHelper mPreferences;

    public AppNetworkClient(PreferencesHelper preferencesHelper) {
        if (pageSet.isEmpty()) {
            pageSet.add(URL_NEWS);
        }
        mPreferences = preferencesHelper;
    }

    @Override
    public List<AppContent> loadNewsFeed(boolean isRefresh) {
        final List<AppContent> contentList = new ArrayList<>();
        if (isRefresh) {
            page = 0;
        }
        if (page < pageSet.size()) {
            final String pageUrl = (String) pageSet.toArray()[page];
            Log.d("Feed", "Start load feed task. Page = " + page + " pageSet = " + pageSet.size());
            try {
                final Document document = Jsoup.connect(pageUrl).get();
                final Element feed = document.getElementsByClass("newsslider3").first();
                final Elements titles = feed.getElementsByClass("newsslider_title");
                final Elements texts = feed.getElementsByClass("newsslider_anons");
                final Elements contentLinks = feed.getElementsByClass("newsslider_link");
                final Elements imageLinks = feed.getElementsByClass("newsslider_img");
                Log.d("Feed", "URL = " + pageUrl);
                for (int i = 0; i < titles.size(); i++) {
                    final AppContent appContent = new AppContent();
                    final String title = titles.get(i).select("a").first().text();
                    final String text = texts.get(i).text();
                    final String date = titles.get(i).select("span").text();
                    final String postLink = "http://dprof.pro" + contentLinks.get(i).select("a").attr("href");
                    final String imageLink = "http://dprof.pro" + imageLinks.get(i).select("img").attr("src");
                    appContent.setTitle(title);
                    appContent.setText(text);
                    appContent.setDate(date);
                    appContent.setPostLink(postLink);
                    appContent.setImageLink(imageLink);
                    contentList.add(appContent);
                }
                if (page == 0) {
                    final Element pageNavigator = document.getElementsByClass("modern-page-navigation").first();
                    final Elements pages = pageNavigator.select("a");
                    for (int i = 0; i < pages.size(); i++) {
                        String nextPage = "http://dprof.pro" + pages.get(i).attr("href");
                        pageSet.add(nextPage);
                    }
                    Log.d("Feed", "pageSet = " + pageSet.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            page++;
        }
        return contentList;
    }

    @Override
    public AppContent loadNewsPost(final String postLink) {
        final AppContent appData = new AppContent();
        try {
            final Document document = Jsoup.connect(postLink).get();
            try {
                final Element text = document.getElementsByClass("news-detail").first();
                final Element date = document.getElementsByClass("news-date-time").first();
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

    @Override
    public List<AppContent> loadStaff() {
        final List<AppContent> contentList = new ArrayList<>();
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
        final Element persons = document.getElementsByClass("razle_podtext").first();
        final Elements images = persons.select("img");
        final Elements texts = persons.select("tr").select("td").next();
        for (int i = 0; i < images.size(); i++) {
            final AppContent appData = new AppContent();
            final String imageLink = "http://dprof.pro" + images.get(i).attr("src");
            final String text = texts.get(i).html();
            appData.setImageLink(imageLink);
            appData.setText(text);
            contentList.add(appData);
        }
        return contentList;
    }

    @Override
    public String loadAboutOrganizationText() {
        String text = Constants.MESSAGE_CONNECT_ERROR;
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
        try {
            Element about = document.getElementsByClass("razle_podtext").first();
            text = about.html();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return text;
    }
}
