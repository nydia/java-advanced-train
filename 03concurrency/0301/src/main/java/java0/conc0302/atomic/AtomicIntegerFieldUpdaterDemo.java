package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Description <p>
 * （1）字段必须是volatile类型的，在线程之间共享变量时保证立即可见
 * （2）字段的描述类型（修饰符public/protected/default/private）是与调用者与操作对象字段的关系一致。也就是说调用者能够直接操作对象字段，那么就可以反射进行原子操作。
 * （3）对于父类的字段，子类是不能直接操作的，尽管子类可以访问父类的字段。
 * （4）只能是实例变量，不能是类变量，也就是说不能加static关键字。
 * （5）只能是可修改变量，不能使final变量，因为final的语义就是不可修改。
 * （6）对于AtomicIntegerFieldUpdater和AtomicLongFieldUpdater只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。如果要修改包装类型就需要使用AtomicReferenceFieldUpdater。
 * ————————————————
 * 原文链接：https://blog.csdn.net/anLA_/article/details/78662383
 * <p/>
 * @Date 2022/2/21 15:14
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<User> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

        User user = new User(100);
        fieldUpdater.addAndGet(user, 100);
        System.out.println(user.getAge());
    }

    static class User {
        volatile int age = 100;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public User(int age) {
            this.age = age;
        }
    }


}
