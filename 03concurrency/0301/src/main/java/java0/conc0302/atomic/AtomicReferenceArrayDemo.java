package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicReferenceArrayDemo{

    private static AtomicReferenceArray<String> referenceArray = new AtomicReferenceArray(new String[10]);

    public static void main(String[] args) {
        referenceArray.getAndSet(0,"1");
        System.out.println(referenceArray.get(0));
    }

}