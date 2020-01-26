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

package pro.dprof.dorprofzhelzszd.ui.documentviewer;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.di.components.DocumentViewerActivityComponent;
import pro.dprof.dorprofzhelzszd.ui.base.BaseActivity;
import pro.dprof.dorprofzhelzszd.ui.dialogs.DocumentPagesDialog;
import pro.dprof.dorprofzhelzszd.utils.Constants;
import pro.dprof.dorprofzhelzszd.utils.PrintUtil;

public final class DocumentViewActivity extends BaseActivity implements DocumentViewerMvpView, DocumentPagesDialog.OnDocumentPagesDialogListener {

    @BindView(R.id.toolbar_pdf) Toolbar mToolbar;
    @BindView(R.id.pdf_document_viewer) PDFView mPdfView;
    @BindView(R.id.cv_document_viewer_html) CardView mCardView;
    @BindView(R.id.tv_document_viewer_html) TextView mTextView;

    private DocumentViewerMvpPresenter<DocumentViewerMvpView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_viewer);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPresenter = ((DocumentViewerActivityComponent) getActivityComponent()).getPresenter();
        mPresenter.onAttach(this);
        if (savedInstanceState == null) {
            final String assetName = getIntent().getStringExtra(Constants.INTENT_TAG_ASSET_NAME);
            final String activityTitle = getIntent().getStringExtra(Constants.INTENT_TAG_ACTIVITY_TITLE);
            mPresenter.onSetDocument(assetName, activityTitle);
        } else {
            mPresenter.onSetDocument(null, "Title");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final int currentPage = mPdfView.getCurrentPage();
        mPresenter.onSaveCurrentPage(currentPage);
        mPresenter.onDetach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_document_viewer, menu);
        final String documentTag = mPresenter.getDocumentTag();
        final boolean isAct = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                documentTag.equals("act.pdf");
        menu.findItem(R.id.im_document_viewer_action_print).setVisible(isAct);
        menu.findItem(R.id.im_document_viewer_action_pages).setVisible(!isAct);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.im_document_viewer_action_pages:
                new DocumentPagesDialog()
                        .setPages(mPdfView.getPageCount(), mPdfView.getCurrentPage() + 1)
                        .show(getSupportFragmentManager(), DocumentPagesDialog.TAG);
                return true;
            case R.id.im_document_viewer_action_print:
                PrintUtil.doWebViewPrint(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDocument(final String title, final String assetName, final int page) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        mCardView.setVisibility(View.GONE);
        mPdfView.fromAsset(assetName).defaultPage(page).load();
    }

    @Override
    public void onSetPage(int page) {
        mPdfView.jumpTo(page - 1, true);
    }
}
