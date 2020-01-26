package pro.dprof.dorprofzhelzszd.utils;

import android.content.Context;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

public abstract class PrintUtil {

    public static void doWebViewPrint(final Context context) {
        final WebView webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(final WebView view, final String url) {
                final PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
                final PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
                final String jobName = "Act Document";
                try {
                    printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
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
}
