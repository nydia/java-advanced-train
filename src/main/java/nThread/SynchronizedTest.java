package nThread;

/**
 * @Auther: hqlv
 * @Date: 2021/5/30 22:18
 * @Description:
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        test();
    }

    public synchronized static void test(){
        System.out.println("hello......");
    }

}
