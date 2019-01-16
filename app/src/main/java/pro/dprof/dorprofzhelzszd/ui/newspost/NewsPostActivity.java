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

package pro.dprof.dorprofzhelzszd.ui.newspost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseActivity;
import pro.dprof.dorprofzhelzszd.utils.AppContent;

public final class NewsPostActivity extends BaseActivity implements NewsPostMvpView {

    @BindView(R.id.cv_news_post) CardView cardView;
    @BindView(R.id.tv_news_post_title) TextView tvPostTitle;
    @BindView(R.id.tv_news_post_text) TextView tvPostText;
    @BindView(R.id.tb_news_post) Toolbar toolbar;
    @BindView(R.id.iv_news_post_picture) ImageView ivPicture;
    @BindView(R.id.pb_news_post) ProgressBar progressBar;

    private NewsPostMvpPresenter<NewsPostMvpView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_post);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        System.out.println(savedInstanceState == null);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            final String postTitle = intent.getStringExtra("PostTitle");
            final String postLink = intent.getStringExtra("PostLink");
            final String imageLink = intent.getStringExtra("ImageLink");
            mPresenter = getActivityComponent().getNewsPostPresenter();
            mPresenter.onAttach(this);
            mPresenter.onSetPostContent(postTitle, postLink, imageLink);
        } else {
            mPresenter = (NewsPostMvpPresenter<NewsPostMvpView>) getLastCustomNonConfigurationInstance();
            mPresenter.onAttach(this);
            mPresenter.onSetPostContent("Title", "postLink", "imageLink");
        }
        progressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setPostContent(final AppContent postContent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String postDate = postContent.getDate();
                    String postTitle = postContent.getTitle();
                    String postTextHtml = postContent.getText();
                    String imageLink = postContent.getImageLink();
                    tvPostTitle.setText(postTitle);
                    tvPostText.setText(Html.fromHtml(postTextHtml));
                    tvPostText.setMovementMethod(LinkMovementMethod.getInstance());
                    try {
                        Picasso.get()
                                .load(imageLink)
                                .resize(420, 290)
                                .into(ivPicture);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setTitle(postDate);
                    }
                    progressBar.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
