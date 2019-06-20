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

package pro.dprof.dorprofzhelzszd.ui.base;

import pro.dprof.dorprofzhelzszd.domain.Repository;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;
    private volatile Repository mRepository;

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    protected V getMvpView() {
        return mMvpView;
    }

    // Must be set before use getRepository(), for example in ActivityModule, in di.
    public void setRepository(Repository dataProvider) {
        mRepository = dataProvider;
    }

    protected Repository getRepository() {
        return mRepository;
    }
}
