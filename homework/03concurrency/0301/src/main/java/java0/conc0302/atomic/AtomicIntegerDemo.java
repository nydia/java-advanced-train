package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

/**
 * @Description TODO
 * @Date 2022/2/21 15:08
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class AtomicIntegerDemo {
    private static AtomicInteger i = new AtomicInteger();

    public static void main(String[] args) {
        i.getAndIncrement();
        System.out.println(i);

        i.getAndAdd(10);
        System.out.println(i);

        IntUnaryOperator add = (u) -> u + 10;
        i.getAndUpdate(add);
        System.out.println(i);
}
}
