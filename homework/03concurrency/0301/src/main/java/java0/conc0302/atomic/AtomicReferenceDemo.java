package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description TODO
 * @Date 2022/2/23 16:44
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class AtomicReferenceDemo {

    private static AtomicReference<String> atomicReference = new AtomicReference("tom");

    public static void main(String[] args) {
        atomicReference.compareAndSet("tom", "jorry");
        System.out.println(atomicReference.get());
    }

}
