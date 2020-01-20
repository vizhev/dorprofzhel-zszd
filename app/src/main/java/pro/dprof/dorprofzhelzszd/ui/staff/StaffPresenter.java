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

import java.util.List;

import pro.dprof.dorprofzhelzszd.domain.TaskExecutor;
import pro.dprof.dorprofzhelzszd.domain.models.Staff;
import pro.dprof.dorprofzhelzszd.ui.base.BasePresenter;

public final class StaffPresenter<V extends StaffMvpView> extends BasePresenter<V>
        implements StaffMvpPresenter<V> {

    private final StaffAdapter mAdapter = new StaffAdapter();

    @Override
    public void onSetAdapter() {
        getMvpView().setAdapter(mAdapter);
    }

    @Override
    public void onSetContent() {
        TaskExecutor.submitRunnable(() -> {
            final List<Staff> contentList = getRepository().getStaffList();
            synchronized (mAdapter) {
                mAdapter.setContentList(contentList);
            }
            TaskExecutor.handleCallback(() -> getMvpView().showContent());
        });
    }
}
