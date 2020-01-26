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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.domain.models.Documents;

final class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {

    private LinearLayoutManager mLayoutManager;
    private final OnItemClick mOnItemClick;
    private final List<Documents> mContentList = new ArrayList<>();
    private int mExpandedPosition = -1;
    private int mPreviousExpandedPosition = -1;

    public interface OnItemClick {

        void onClick(String assetName, String title);
    }

    DocumentsAdapter(final OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    void setContentList(final List<Documents> contentList) {
        if (mContentList.isEmpty()) {
            mContentList.addAll(contentList);
        }
    }

    void setLayoutManager(final LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_documents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final boolean isExpanded = mExpandedPosition == position;
        if (isExpanded) {
            mPreviousExpandedPosition = position;
        }
        final Documents documents = mContentList.get(position);
        holder.mLlContent.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.mTvTitle.setText(documents.getSectionTitle());
        holder.mTvTitle.setOnClickListener(v -> {
            mExpandedPosition = isExpanded ? -1 : position;
            notifyItemChanged(mPreviousExpandedPosition);
            notifyItemChanged(position);
            mLayoutManager.scrollToPositionWithOffset(position, 0);
        });
        if (holder.mLlContent.getVisibility() == View.VISIBLE) {
            holder.mLlContent.removeAllViewsInLayout();
            final List<String> buttonsText = documents.getItemTitles();
            final List<String> assetNames = documents.getAssetsNames();
            final List<String> activityTitles = documents.getActivityTitles();
            for (int i = 0; i < buttonsText.size(); i++) {
                final String buttonText = buttonsText.get(i);
                final String assetName = assetNames.get(i);
                final String title = activityTitles.get(i);
                final Button button = (Button) LayoutInflater
                        .from(holder.mLlContent.getContext())
                        .inflate(R.layout.item_documents_button, null);
                button.setText(buttonText);
                button.setOnClickListener(v -> mOnItemClick.onClick(assetName, title));
                holder.mLlContent.addView(button, i);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_documents_title)
        TextView mTvTitle;
        @BindView(R.id.ll_item_documents_list)
        LinearLayout mLlContent;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
