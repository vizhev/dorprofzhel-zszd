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

import android.app.Activity;

import androidx.fragment.app.Fragment;
import pro.dprof.dorprofzhelzszd.di.components.MainActivityComponent;

public abstract class BaseFragment extends Fragment implements MvpView {

    protected MainActivityComponent getActivityComponent() {
        final Activity activity = requireActivity();
        if (activity instanceof BaseActivity) {
            return (MainActivityComponent) ((BaseActivity) activity).getActivityComponent();
        }
        return null;
    }

    protected void runOnUiThread(final Runnable runnable) {
        requireActivity().runOnUiThread(runnable);
    }

    //public abstract String getFragmentTag();
}
