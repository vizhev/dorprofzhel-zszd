package pro.dprof.dorprofzhelzszd.domain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class TaskExecutor {

    public interface TaskCallback {

        void callback();
    }

    private static final ExecutorService mExecutorService = Executors.newCachedThreadPool();

    public static void submitRunnable(final Runnable runnable) {
        mExecutorService.submit(runnable);
    }

    public static void handleCallback(final TaskCallback taskCallback) {
        int retry = 0;
        do {
            try {
                taskCallback.callback();
                retry = 2;
            } catch (NullPointerException e) {
                e.printStackTrace();
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            } finally {
                retry++;
            }
        } while (retry < 2);
    }
}
