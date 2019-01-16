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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pro.dprof.dorprofzhelzszd.App;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.di.components.ActivityComponent;
import pro.dprof.dorprofzhelzszd.di.components.DaggerActivityComponent;
import pro.dprof.dorprofzhelzszd.di.modules.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeLight);
        if (savedInstanceState == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(((App) getApplication()).getApplicationComponent())
                    .build();
            mActivityComponent.injectActivityContext(this);
        } else {
            mActivityComponent = (ActivityComponent) getLastCustomNonConfigurationInstance();
        }
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mActivityComponent;
    }
}
