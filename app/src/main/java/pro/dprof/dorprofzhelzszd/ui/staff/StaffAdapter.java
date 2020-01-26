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

package pro.dprof.dorprofzhelzszd.ui.staff;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
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
import pro.dprof.dorprofzhelzszd.domain.models.Staff;

final class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private final List<Staff> mContentList = new ArrayList<>();

    void setContentList(final List<Staff> contentList) {
        if (mContentList.isEmpty()) {
            mContentList.addAll(contentList);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_staff, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Staff staff = mContentList.get(position);
        holder.mTvPersonsText.setText(Html.fromHtml(staff.getInfo()));
        try {
            Picasso.get()
                    .load(staff.getImageLink())
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .resize(170, 190)
                    .into(holder.mIvPersonsPicture);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item_persons) ImageView mIvPersonsPicture;
        @BindView(R.id.tv_item_persons) TextView mTvPersonsText;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
