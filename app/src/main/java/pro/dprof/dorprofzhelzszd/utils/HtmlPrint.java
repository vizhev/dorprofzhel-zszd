package pro.dprof.dorprofzhelzszd.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public final class HtmlPrint {

    private final Context mContext;

    public HtmlPrint(Context context) {
        mContext = context;
    }

    public void doWebViewPrint() {
        final WebView webView = new WebView(mContext);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
            }
        });
        webView.loadUrl("file:///android_asset/act_print.html");
        //webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
    }

    @SuppressLint("NewApi")
    private void createWebPrintJob(WebView webView) {
        final PrintManager printManager = (PrintManager)mContext.getSystemService(Context.PRINT_SERVICE);
        final PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        final String jobName = "Act Document";
        try {
            printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
