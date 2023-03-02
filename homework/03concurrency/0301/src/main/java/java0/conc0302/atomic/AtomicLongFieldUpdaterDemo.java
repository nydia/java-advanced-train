package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * @Description TODO
 * @Date 2022/2/21 16:40
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
public class AtomicLongFieldUpdaterDemo {

    public static void main(String[] args) {
        AtomicLongFieldUpdater<User> fieldUpdater = AtomicLongFieldUpdater.newUpdater(User.class, "age");

        User user = new User(100l);
        fieldUpdater.addAndGet(user, 100l);
        System.out.println(user.getAge());
    }

    static class User {
        volatile long age = 100;

        public long getAge() {
            return age;
        }

        public void setAge(long age) {
            this.age = age;
        }

        public User(long age) {
            this.age = age;
        }
    }

}
