package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * @Description TODO
 * @Date 2022/2/21 15:38
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class AtomicLongDemo {

    static AtomicLong l = new AtomicLong();

    public static void main(String[] args) {
        l.getAndIncrement();
        System.out.println("2 -- " + l);

        LongUnaryOperator opr = (u) -> u + 20;
        l.getAndUpdate(opr);
        System.out.println("3 -- " + l);

        LongBinaryOperator bopr = (a,b) -> a + b;
        l.getAndAccumulate(10, bopr);
        System.out.println("1 -- " + l);

        l.addAndGet(20);
        System.out.println("4 -- " + l);
    }
}
