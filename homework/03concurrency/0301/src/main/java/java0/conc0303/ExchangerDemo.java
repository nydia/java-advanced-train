package java0.conc0303;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: hqlv
 * @Date: 2022/3/25 16:18
 * @Description: <p>Exchanger 是 JDK 1.5 开始提供的一个用于两个工作线程之间交换数据的封装工具类，简单说就是一个线程在完成一定的事务后想与另一个线程交换数据，则第一个先拿出数据的线程会一直等待第二个线程，直到第二个线程拿着数据到来时才能彼此交换对应数据。
 */
public class ExchangerDemo {

    //静态内部类
    static class Producer implements Runnable {
        private Exchanger<Integer> exchanger = new Exchanger();
        private Integer data;

        public Producer(Integer data, Exchanger exchanger) {
            this.exchanger = exchanger;
            this.data = data;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Producer交换前数据：" + data);
                    data = exchanger.exchange(data);
                    System.out.println("Producer交换后数据：" + data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //非静态内部类
    class Consumer implements Runnable {
        private Exchanger<Integer> exchanger = new Exchanger();
        private Integer data;

        public Consumer(Integer data, Exchanger exchanger) {
            this.exchanger = exchanger;
            this.data = data;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Consumer交换前数据：" + data);
                    data = exchanger.exchange(data);
                    System.out.println("Consumer交换后数据：" + data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(new Producer(1, exchanger)).start();
        new Thread(new ExchangerDemo().new Consumer(2, exchanger)).start();
        TimeUnit.SECONDS.sleep(7);
        System.exit(-1);
    }

}
