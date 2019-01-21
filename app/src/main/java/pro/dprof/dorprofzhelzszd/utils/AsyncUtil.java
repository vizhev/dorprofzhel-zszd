package pro.dprof.dorprofzhelzszd.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class AsyncUtil {

    private static final ExecutorService mExecutorService = Executors.newCachedThreadPool(
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    thread.setDaemon(true);
                    return thread;
                }
            }
    );

    public static void submitRunnable(Runnable runnable) {
        mExecutorService.submit(runnable);
    }
}
