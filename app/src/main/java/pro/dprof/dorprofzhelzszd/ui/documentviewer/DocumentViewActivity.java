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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.dprof.dorprofzhelzszd.R;
import pro.dprof.dorprofzhelzszd.ui.base.BaseActivity;
import pro.dprof.dorprofzhelzszd.ui.dialogs.DocumentPagesDialog;
import pro.dprof.dorprofzhelzszd.utils.Constants;

public final class DocumentViewActivity extends BaseActivity implements DocumentViewerMvpView, DocumentPagesDialog.OnDocumentPagesDialogListener {

    @BindView(R.id.toolbar_pdf) Toolbar toolbar;
    @BindView(R.id.pdf_document_viewer) PDFView pdfView;
    @BindView(R.id.cv_document_viewer_html) CardView cardView;
    @BindView(R.id.tv_document_viewer) TextView textView;

    private DocumentViewerMvpPresenter<DocumentViewerMvpView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_viewer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPresenter = getActivityComponent().getPdfViewerPresenter();
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
        final int currentPage = pdfView.getCurrentPage();
        mPresenter.onSaveCurrentPage(currentPage);
        mPresenter.onDetach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // removed because this document is not for public use
       /* boolean isNewApi = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String documentTag = mPresenter.getDocumentTag();
        if (documentTag.equals("Act") && isNewApi) {
            getMenuInflater().inflate(R.menu.menu_pdf_action, menu);
            return true;
        }*/
       getMenuInflater().inflate(R.menu.menu_document_viewer, menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_document_viewer_action_pages:
                new DocumentPagesDialog()
                        .setPages(pdfView.getPageCount(), pdfView.getCurrentPage() + 1)
                        .show(getSupportFragmentManager(), DocumentPagesDialog.TAG);
                return true;
            /*case R.id.item_pdf_action_print:
                //this document is not for public use
                new HtmlPrint(this).doWebViewPrint();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDocument(String title, String assetName, int page) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        cardView.setVisibility(View.GONE);
        pdfView.fromAsset(assetName).defaultPage(page).load();
    }

    @Override
    public void onSetPage(int page) {
        pdfView.jumpTo(page - 1, true);
    }
}
