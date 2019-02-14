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

package pro.dprof.dorprofzhelzszd.ui.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.newspost.NewsPostActivity;
import pro.dprof.dorprofzhelzszd.dataclasses.News;

final class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {

    private Context mContext;
    private final List<News> mContentList = new ArrayList<>();

    void setContentList(List<News> contentList, boolean isRefresh) {
        if (isRefresh) {
            mContentList.clear();
        }
        mContentList.addAll(contentList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final News appData = mContentList.get(position);
        holder.mTvTitle.setText(appData.getTitle());
        holder.mTvText.setText(appData.getText());
        holder.mTvTDate.setText(appData.getDate());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, NewsPostActivity.class);
                intent.putExtra("PostTitle", appData.getTitle());
                intent.putExtra("PostLink", appData.getPostLink());
                intent.putExtra("ImageLink", appData.getImageLink());
                mContext.startActivity(intent);
            }
        });
        try {
            Picasso.get()
                    .load(appData.getImageLink())
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.mIvPicture);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_news_title) TextView mTvTitle;
        @BindView(R.id.tv_item_news_text) TextView mTvText;
        @BindView(R.id.tv_item_news_date) TextView mTvTDate;
        @BindView(R.id.iv_item_news_picture) ImageView mIvPicture;
        @BindView(R.id.cv_news_item) CardView mCardView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
