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

package pro.dprof.dorprofzhelzszd.ui.aboutapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseActivity;

public final class AboutAppActivity extends BaseActivity {

    @BindView(R.id.lv_about_app) ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
        final View view = getLayoutInflater().inflate(
                R.layout.element_header_about_app, mListView, false
        );
        final ImageView ivLogo = view.findViewById(R.id.iv_about_app_logo);
        final TextView tvMessage = view.findViewById(R.id.tv_about_app_message);
        Picasso.get()
                .load(R.drawable.ic_logo)
                .resize(500, 500)
                .into(ivLogo);
        tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
        mListView.addHeaderView(view, "Header", false);
        mListView.setHeaderDividersEnabled(true);
        mListView.setAdapter(createAdapter());
        mListView.setOnItemClickListener(createItemListener());
    }

    private SimpleAdapter createAdapter() {
        final List<Map<String, String>> dataList = new ArrayList<>();

        final Map<String, String> author = new HashMap<>(2);
        author.put("First Line", getResources().getString(R.string.about_app_author));
        author.put("Second Line", getResources().getString(R.string.about_app_author_name) +
                " | " + getResources().getString(R.string.about_app_author_email)
        );

        final Map<String, String> license = new HashMap<>(2);
        license.put("First Line", getResources().getString(R.string.about_app_license));
        license.put("Second Line", getResources().getString(R.string.about_app_license_name));

        final Map<String, String> source = new HashMap<>(2);
        source.put("First Line", getResources().getString(R.string.about_app_source));
        source.put("Second Line", getResources().getString(R.string.about_app_source_name));

        dataList.add(author);
        dataList.add(license);
        dataList.add(source);
        return new SimpleAdapter(
                this,
                dataList,
                android.R.layout.simple_list_item_2,
                new String[]{"First Line", "Second Line"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
    }

    private AdapterView.OnItemClickListener createItemListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent();
                switch (position) {
                    case 1:
                        intent.setAction(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:" + getResources().getString(R.string.about_app_author_email)));
                        break;
                    case 2:
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(getResources().getString(R.string.about_app_license_link)));
                        break;
                    case 3:
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(getResources().getString(R.string.about_app_source_link)));
                        break;
                }
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
