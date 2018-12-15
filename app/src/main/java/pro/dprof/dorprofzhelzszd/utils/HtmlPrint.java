package pro.dprof.dorprofzhelzszd.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public final class HtmlPrint {

    private Context mContext;
    private WebView mWebView;

    public HtmlPrint(Context context) {
        mContext = context;
    }

    public void doWebViewPrint() {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(mContext);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                mWebView = null;
            }
        });

        webView.loadUrl("file:///android_asset/act_print.html");
        //webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    @SuppressLint("NewApi")
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager)mContext.getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = "Act Document";
        try {
            printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
