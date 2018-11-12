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

package pro.dprof.dorprofzhelzszd.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.document_viewer.DocumentViewActivity;
import pro.dprof.dorprofzhelzszd.utils.AppData;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {

    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private static List<AppData> mContentList;
    private int mExpandedPosition = -1;
    private int mPreviousExpandedPosition = -1;

    public DocumentsAdapter(List<AppData> contentList) {
        mContentList = contentList;
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_documents, parent, false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final boolean isExpanded = mExpandedPosition == position;
        if (isExpanded) {
            mPreviousExpandedPosition = position;
        }
        final AppData appData = mContentList.get(position);
        holder.llContent.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.tvTitle.setText(appData.getTitle());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(mPreviousExpandedPosition);
                notifyItemChanged(position);
                mLayoutManager.scrollToPositionWithOffset(position, 0);
            }
        });
        if (holder.llContent.getVisibility() == View.VISIBLE) {
            holder.llContent.removeAllViewsInLayout();
            List<String> buttonsText = appData.getItemsTitles();
            List<String> assetNames = appData.getAssetsNames();
            List<String> activityTitles = appData.getActivitysTitles();
            for (int i = 0; i < buttonsText.size(); i++) {
                final String buttonText = buttonsText.get(i);
                final String assetName = assetNames.get(i);
                final String activityTitle = activityTitles.get(i);
                final Button button = (Button) LayoutInflater.from(mContext)
                        .inflate(R.layout.item_documents_button, null);
                button.setText(buttonText);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DocumentViewActivity.class);
                        intent.putExtra(Constants.INTENT_TAG_ASSET_NAME, assetName);
                        intent.putExtra(Constants.INTENT_TAG_ACTIVITY_TITLE, activityTitle);
                        mContext.startActivity(intent);
                    }
                });
                holder.llContent.addView(button, i);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mContentList != null) {
            return mContentList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_documents_title)
        TextView tvTitle;
        @BindView(R.id.ll_item_documents_main)
        LinearLayout llMain;
        @BindView(R.id.ll_item_documents_list)
        LinearLayout llContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
