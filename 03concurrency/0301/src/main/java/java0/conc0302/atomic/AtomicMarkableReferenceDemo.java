package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @Description TODO
 * @Date 2022/2/21 16:57
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class AtomicMarkableReferenceDemo {

    private static AtomicMarkableReference<String> markableReference = new AtomicMarkableReference("tom", false);

    public static void main(String[] args) {

        boolean oldMarked = markableReference.isMarked();
        String oldReference = markableReference.getReference();

        System.out.println("初始化之后的标记：" + oldMarked);
        System.out.println("初始化之后的值：" + oldReference);

        String newReference = "jerry";

        boolean b = markableReference.compareAndSet(oldReference, newReference, true, false);
        if (!b) {
            System.out.println("Mark不一致，无法修改Reference的值");
        }
        b = markableReference.compareAndSet(oldReference, newReference, false, true);
        if (b) {
            System.out.println("Mark一致，修改reference的值为jerry");
        }
        System.out.println("修改成功之后的Mark：" + markableReference.isMarked());
        System.out.println("修改成功之后的值：" + markableReference.getReference());
    }

}
