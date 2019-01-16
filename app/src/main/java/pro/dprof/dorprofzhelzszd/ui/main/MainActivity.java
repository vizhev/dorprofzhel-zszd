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

package pro.dprof.dorprofzhelzszd.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.aboutapp.AboutAppActivity;
import pro.dprof.dorprofzhelzszd.ui.aboutorg.AboutOrgFragment;
import pro.dprof.dorprofzhelzszd.ui.base.BaseActivity;
import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;
import pro.dprof.dorprofzhelzszd.ui.documents.DocumentsFragment;
import pro.dprof.dorprofzhelzszd.ui.newsfeed.NewsFeedFragment;
import pro.dprof.dorprofzhelzszd.ui.note.NoteFragment;
import pro.dprof.dorprofzhelzszd.ui.staff.StaffFragment;

public final class MainActivity extends BaseActivity implements MainMvpView {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view) NavigationView mNavigationView;

    private MainMvpPresenter<MainMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mNavigationView.setNavigationItemSelectedListener(createNavigationListener());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.setDrawerSlideAnimationEnabled(false);
        toggle.syncState();
        if (savedInstanceState == null) {
            BaseFragment newsFragment = new NewsFeedFragment();
            mPresenter = getActivityComponent().getMainPresenter();
            mPresenter.onAttach(this);
            mPresenter.onStartFragmentTransaction(newsFragment, NewsFeedFragment.TAG, MainPresenter.ADD_FRAGMENT);
        } else {
            mPresenter = (MainMvpPresenter<MainMvpView>) getLastCustomNonConfigurationInstance();
            mPresenter.onAttach(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentFragmentTag = mPresenter.getCurrentFragmentTag();
        selectDrawerItemAndSetTitle(currentFragmentTag);
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
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }
        if (mPresenter.getBackStackCount() > 1) {
            String tag = getSupportFragmentManager().getFragments().get(0).getTag();
            mPresenter.onRemoveFragment(tag);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void startFragmentTransaction(@NonNull BaseFragment fragment,
                                         @NonNull String fragmentTag,
                                         @NonNull String action) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (action) {
            case MainPresenter.ADD_FRAGMENT:
                transaction.add(R.id.fl_main, fragment, fragmentTag);
                break;
            case MainPresenter.REPLACE_FRAGMENT:
                String resumedFragmentTag = getSupportFragmentManager().getFragments().get(0).getTag();
                boolean isFragmentResumed = resumedFragmentTag != null && resumedFragmentTag.equals(fragmentTag);
                if (isFragmentResumed) {
                    return;
                }
                transaction.replace(R.id.fl_main, fragment, fragmentTag);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            case MainPresenter.REMOVE_FRAGMENT:
                transaction.replace(R.id.fl_main, fragment, fragmentTag);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                break;
        }
        transaction.commit();
    }

    @Override
    public void selectDrawerItemAndSetTitle(String tag) {
        switch (tag) {
            case NewsFeedFragment.TAG:
                mToolbar.setTitle(getResources().getString(R.string.drawer_news));
                mNavigationView.setCheckedItem(R.id.item_drawer_news);
                break;
            case DocumentsFragment.TAG:
                mToolbar.setTitle(getResources().getString(R.string.drawer_documentation));
                mNavigationView.setCheckedItem(R.id.item_drawer_documentation);
                break;
            case NoteFragment.TAG:
                mToolbar.setTitle(getResources().getString(R.string.drawer_note));
                mNavigationView.setCheckedItem(R.id.item_drawer_note);
                break;
            case StaffFragment.TAG:
                mToolbar.setTitle(getResources().getString(R.string.drawer_staff));
                mNavigationView.setCheckedItem(R.id.item_drawer_staff);
                break;
            case AboutOrgFragment.TAG:
                mToolbar.setTitle(getResources().getString(R.string.drawer_about_org));
                mNavigationView.setCheckedItem(R.id.item_drawer_about_org);
                break;
        }
    }

    private NavigationView.OnNavigationItemSelectedListener createNavigationListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(Gravity.START);
                switch (item.getItemId()) {
                    case R.id.item_drawer_news:
                        BaseFragment oopsFragment = new NewsFeedFragment();
                        mPresenter.onStartFragmentTransaction(oopsFragment, NewsFeedFragment.TAG, MainPresenter.REPLACE_FRAGMENT);
                        return true;
                    case R.id.item_drawer_documentation:
                        BaseFragment documentsFragment = new DocumentsFragment();
                        mPresenter.onStartFragmentTransaction(documentsFragment, DocumentsFragment.TAG, MainPresenter.REPLACE_FRAGMENT);
                        return true;
                    case R.id.item_drawer_note:
                        BaseFragment noteFragment = new NoteFragment();
                        mPresenter.onStartFragmentTransaction(noteFragment, NoteFragment.TAG, MainPresenter.REPLACE_FRAGMENT);
                        return true;
                    case R.id.item_drawer_staff:
                        BaseFragment personsFragment = new StaffFragment();
                        mPresenter.onStartFragmentTransaction(personsFragment, StaffFragment.TAG, MainPresenter.REPLACE_FRAGMENT);
                        return true;
                    case R.id.item_drawer_about_org:
                        BaseFragment aboutOrgFragment = new AboutOrgFragment();
                        mPresenter.onStartFragmentTransaction(aboutOrgFragment, AboutOrgFragment.TAG, MainPresenter.REPLACE_FRAGMENT);
                        return true;
                    case R.id.item_drawer_about_app:
                        Intent intent = new Intent(MainActivity.this, AboutAppActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        };
    }
}
