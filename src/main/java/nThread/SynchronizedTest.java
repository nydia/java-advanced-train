package nThread;

/**
 * @Auther: hqlv
 * @Date: 2021/5/30 22:18
 * @Description:
 *   为了查看Synchronized里面的monitorenter和monitorexit
 *   1. javac SynchronizedTest.java
 *   2. javap -c SynchronizedTest.class
 *   <p>
        Compiled from "SynchronizedTest.java"
        public class nThread.SynchronizedTest {
        public nThread.SynchronizedTest();
        Code:
        0: aload_0
        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
        4: return

        public void test();
        Code:
        0: ldc           #2                  // class java/lang/Object
        2: dup
        3: astore_1
        4: monitorenter
        5: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
        8: ldc           #4                  // String hello......
        10: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        13: aload_1
        14: monitorexit
        15: goto          23
        18: astore_2
        19: aload_1
        20: monitorexit
        21: aload_2
        22: athrow
        23: return
        Exception table:
        from    to  target type
        5    15    18   any
        18    21    18   any
        }
 */
public class SynchronizedTest {


    public  void test(){
        synchronized(Object.class){
            System.out.println("hello......");
        }
    }

}
