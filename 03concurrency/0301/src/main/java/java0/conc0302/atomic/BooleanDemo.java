package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description TODO
 * @Date 2022/2/16 14:10
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class BooleanDemo {
    private static AtomicBoolean b = new AtomicBoolean(false);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    b.getAndSet(true);
                }
            }).start();
        }

        System.out.println(b.get());
    }
}
