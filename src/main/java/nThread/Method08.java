package nThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description:
 */
public class Method08 {
    final static ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        executorService.submit(new MyThread());
        System.out.println("hello......two");
        executorService.shutdownNow();
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello......one");
        }
    }

}
