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

package pro.dprof.dorprofzhelzszd.ui.aboutorg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseFragment;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class AboutOrgFragment extends BaseFragment implements AboutOrgMvpView {

    public final static String TAG = "AboutOrgFragment";

    @BindView(R.id.tv_about_org) TextView tvAbout;
    @BindView(R.id.cv_about_org) CardView cardView;

    private AboutOrgMvpPresenter<AboutOrgMvpView> mPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_about_org, container, false);
        ButterKnife.bind(this, view);
        mPresenter = getActivityComponent().getAboutOrgPresenter();
        mPresenter.onAttach(this);
        mPresenter.onLoadAboutText();
        cardView.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void setAboutText(final String text) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (text.equals(Constants.MESSAGE_CONNECT_ERROR)) {
                        Toast.makeText(getActivity(), Constants.MESSAGE_CONNECT_ERROR, Toast.LENGTH_SHORT).show();
                    } else {
                        tvAbout.setText(Html.fromHtml(text));
                        tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
                        cardView.setVisibility(View.VISIBLE);
                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
