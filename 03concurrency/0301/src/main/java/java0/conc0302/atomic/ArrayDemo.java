package java0.conc0302.atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @Description TODO
 * @Date 2022/2/16 14:17
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class ArrayDemo {

    private static AtomicIntegerArray integerArray = new AtomicIntegerArray(new int[10]);
    private static AtomicLongArray longArray = new AtomicLongArray(new long[10]);
    private static AtomicReferenceArray<String> referenceArray  = new AtomicReferenceArray(new String[10]);

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.clear();
            map.put(i, i);

            new Thread(() -> {
                Map.Entry<Integer, Integer> entry = map.entrySet().iterator().next();
                integerArray.getAndAdd(entry.getKey(), entry.getValue());
                longArray.getAndAdd(entry.getKey(), entry.getValue());
                referenceArray.getAndUpdate(entry.getKey(), )

            }).start();
        }

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < integerArray.length(); i++) {
            System.out.println("integerArray" + i + " = " + integerArray.get(i) + ",  longArray" + i + " = " + longArray.get(i));
        }

    }

}
