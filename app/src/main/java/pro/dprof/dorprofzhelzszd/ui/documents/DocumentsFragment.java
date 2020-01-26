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

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;
import pro.dprof.dorprofzhelzszd.ui.documentviewer.DocumentViewActivity;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class DocumentsFragment extends BaseFragment implements DocumentsMvpView {

    public final static String TAG = "DocumentsFragment";

    @BindView(R.id.pb_documents) ProgressBar mProgressBar;
    @BindView(R.id.rv_documents) RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private DocumentsMvpPresenter<DocumentsMvpView> mPresenter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_documents, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = getActivityComponent().getDocumentsPresenter();
        mPresenter.onAttach(this);
        mPresenter.onSetAdapter();
        if (mPresenter.isNeedLoadingContent()) {
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mPresenter.onSetContent();
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
        mPresenter = null;
    }

    @Override
    public void setAdapter(final DocumentsAdapter documentsAdapter) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(documentsAdapter);
        ((DocumentsAdapter) Objects.requireNonNull(mRecyclerView.getAdapter())).setLayoutManager(layoutManager);
    }

    @Override
    public void showContent() {
        runOnUiThread(() -> {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showDocument(final String assetName, final String title) {
        final Intent intent = new Intent(requireContext(), DocumentViewActivity.class);
        intent.putExtra(Constants.INTENT_TAG_ASSET_NAME, assetName);
        intent.putExtra(Constants.INTENT_TAG_ACTIVITY_TITLE, title);
        startActivity(intent);
    }
}
