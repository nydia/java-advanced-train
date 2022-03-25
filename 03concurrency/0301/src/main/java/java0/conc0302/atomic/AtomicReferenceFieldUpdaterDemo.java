package java0.conc0302.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterDemo {

    //此类接收三个参数：
    //1、字段所在的类。
    //2、字段的类型。
    //3、更新字段的内容。
    private static AtomicReferenceFieldUpdater fieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");
    private static AtomicReferenceFieldUpdater fieldUpdater2 = AtomicReferenceFieldUpdater.newUpdater(User.class, Integer.class, "age");

    public static void main(String[] args) {
        User user = new User();
        fieldUpdater.compareAndSet(user, "name1", "name2");
        System.out.println(user.getName());

        fieldUpdater2.compareAndSet(user, 100, 200);
        System.out.println(user.getAge());
    }

    static class User {
        volatile Integer age = 100;
        volatile String name = "name1";

        public User() {
            this.age = age;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}