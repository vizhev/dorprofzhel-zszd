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

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;

public final class NewsFeedFragment extends BaseFragment implements NewsFeedMvpView {

    public final static String TAG = "NewsFeedFragment";

    @BindView(R.id.rv_news) RecyclerView mRecyclerView;
    @BindView(R.id.sr_news_feed) SwipeRefreshLayout mSwipeRefreshLayout;

    private Unbinder mUnbinder;
    private NewsFeedMvpPresenter<NewsFeedMvpView> mPresenter;

    private boolean isNeedLoadContent = false;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = getActivityComponent().getNewsFeedPresenter();
        mPresenter.onAttach(this);
        mPresenter.onSetAdapter();
        if (Objects.requireNonNull(mRecyclerView.getAdapter()).getItemCount() == 0) {
            mSwipeRefreshLayout.setRefreshing(true);
            mPresenter.onSetContent(true);
        }
        mSwipeRefreshLayout.setOnRefreshListener(createRefreshListener());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
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
    public void setAdapter(NewsFeedAdapter adapter) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final RecyclerView.OnScrollListener scrollListener = createScrollListener(layoutManager);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void stopRefreshing() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                try {
                    Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (0 == mRecyclerView.getAdapter().getItemCount()) {
                        Toast.makeText(getActivity(), R.string.connect_error_message, Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private RecyclerView.OnScrollListener createScrollListener(final LinearLayoutManager linearLayoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("ScrollListener", "Last visible position: " + linearLayoutManager.findLastVisibleItemPosition());
                Log.d("ScrollListener", "Item count: " + linearLayoutManager.getItemCount());

                if (dy > 0) {
                    boolean isLastPost =
                            linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.getItemCount() - 3;
                    boolean isPreLastPost =
                            linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.getItemCount() - 4;
                    if (isPreLastPost) {
                        isNeedLoadContent = true;
                    }
                    if (isNeedLoadContent && isLastPost) {
                        isNeedLoadContent = false;
                        mSwipeRefreshLayout.setRefreshing(true);
                        mPresenter.onSetContent(false);
                    }
                }
            }
        };
    }

    private SwipeRefreshLayout.OnRefreshListener createRefreshListener() {
        return () -> mPresenter.onSetContent(true);
    }
}
