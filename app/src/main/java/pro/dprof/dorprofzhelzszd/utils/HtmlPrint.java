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

    public HtmlPrint(final Context context) {
        mContext = context;
    }

    public void doWebViewPrint() {
        final WebView webView = new WebView(mContext);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {
                createWebPrintJob(view);
            }
        });
        webView.loadUrl("file:///android_asset/act_print.html");
        /*try {
            final InputStream inputStream = mContext.getAssets().open("act_print.html");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            webView.loadDataWithBaseURL(null, stringBuilder.toString(), "text/HTML", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @SuppressLint("NewApi")
    private void createWebPrintJob(final WebView webView) {
        final PrintManager printManager = (PrintManager) mContext.getSystemService(Context.PRINT_SERVICE);
        final PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        final String jobName = "Act Document";
        try {
            printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
