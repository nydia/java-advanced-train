package java0.conc0302.atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * @Description TODO
 * @Date 2022/2/21 15:54
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class AtomicLongArrayDemo {
    private static AtomicLongArray longArray = new AtomicLongArray(new long[10]);

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.clear();
            map.put(i, i);

            new Thread(() -> {
                Map.Entry<Integer, Integer> entry = map.entrySet().iterator().next();
                longArray.getAndAdd(entry.getKey(), entry.getValue());
            }).start();
        }

        for (int i = 0; i < longArray.length(); i++) {
            System.out.println("longArray" + i + " = " + longArray.get(i));
        }
    }
}
