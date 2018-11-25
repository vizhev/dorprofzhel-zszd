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

import java.util.LinkedHashMap;
import java.util.Map;

import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;
import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    public static final String ADD_FRAGMENT = "add_fragment";
    public static final String REPLACE_FRAGMENT = "replace_fragment";
    public static final String REMOVE_FRAGMENT = "remove_fragment";

    private String mCurrentFragmentTag;
    private Map<String, BaseFragment> mBackStackMap;

    public MainPresenter() {
        mBackStackMap = new LinkedHashMap<>();
    }

    @Override
    public void onStartFragmentTransaction(BaseFragment fragment, String fragmentTag, String action) {
        if (mBackStackMap.containsKey(fragmentTag)) {
            fragment = mBackStackMap.get(fragmentTag);
            mBackStackMap.remove(fragmentTag);
        }
        mBackStackMap.put(fragmentTag, fragment);
        mCurrentFragmentTag = fragmentTag;
        getMvpView().startFragmentTransaction(fragment, fragmentTag, action);
        getMvpView().selectDrawerItemAndSetTitle(fragmentTag);
    }

    @Override
    public void onRemoveFragment(String fragmentTag) {
        mBackStackMap.remove(fragmentTag);
        Object[] keyArray = mBackStackMap.keySet().toArray();
        int index = keyArray.length - 1;
        String tag = (String) keyArray[index];
        BaseFragment fragment = mBackStackMap.get(tag);
        getMvpView().startFragmentTransaction(fragment, tag, REMOVE_FRAGMENT);
        getMvpView().selectDrawerItemAndSetTitle(tag);
        mCurrentFragmentTag = tag;
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
