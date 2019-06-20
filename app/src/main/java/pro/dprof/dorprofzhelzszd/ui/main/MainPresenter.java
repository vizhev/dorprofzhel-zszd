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

import androidx.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;
import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;

public final class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    static final int ADD_FRAGMENT = 1;
    static final int REPLACE_FRAGMENT = 2;
    static final int REMOVE_FRAGMENT = 0;

    private String mCurrentFragmentTag;
    private final Map<String, BaseFragment> mBackStackMap = new LinkedHashMap<>();

    @Override
    public void onStartFragmentTransaction(@NonNull BaseFragment fragment, String fragmentTag, int action) {
        mCurrentFragmentTag = fragmentTag;
        if (mBackStackMap.containsKey(fragmentTag)) {
            fragment = mBackStackMap.get(fragmentTag);
            mBackStackMap.remove(fragmentTag);
        }
        if (fragment != null) {
            mBackStackMap.put(fragmentTag, fragment);
            getMvpView().startFragmentTransaction(fragment, action);
            getMvpView().selectDrawerItemAndSetTitle(fragmentTag);
        }
    }

    @Override
    public void onRemoveFragment(String fragmentTag) {
        mBackStackMap.remove(fragmentTag);
        final int index = mBackStackMap.size() - 1;
        mCurrentFragmentTag = (String) Objects.requireNonNull(mBackStackMap.keySet().toArray())[index];
        final BaseFragment fragment = mBackStackMap.get(mCurrentFragmentTag);
        if (fragment != null) {
            getMvpView().startFragmentTransaction(fragment, REMOVE_FRAGMENT);
            getMvpView().selectDrawerItemAndSetTitle(mCurrentFragmentTag);
        }
    }

    @Override
    public String getCurrentFragmentTag() {
        return mCurrentFragmentTag;
    }

    @Override
    public int getBackStackCount() {
        return mBackStackMap.size();
    }
}
