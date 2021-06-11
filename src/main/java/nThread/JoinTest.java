package nThread;

/**
 * @Auther: hqlv
 * @Date: 2021/5/29 23:55
 * @Description: join的阻塞子线程的执行
 */
public class JoinTest {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.setDaemon(true);
        t.start();
        //等待t执行完成之后在继续运行
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello......two");
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello......one");
        }
    }

}
