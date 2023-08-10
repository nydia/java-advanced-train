package com.nydia.mybatis.test;

import java.io.*;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/8/10 22:58
 * @Description: SerializableTest
 */
public class SerializableTest implements Serializable {

    private final static long serialVersionUID = 1L;

    private String firstName = null;
    private String lastName = null;
    private Integer age = null;
    // unserializable field
    private transient School school= null;

    public static final SerializableTest instance= new SerializableTest();

    public SerializableTest () { }

    public SerializableTest (String fname, String lname, Integer age, School school) {
        this.firstName = fname;
        this.lastName = lname;
        this.age = age;
        this.school = school;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        //invoke default serialization method
        out.defaultWriteObject();

        if(school == null)
            school = new School();
        out.writeObject(school.sName);
        out.writeObject(school.sId);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //invoke default serialization method
        in.defaultReadObject();

        String name = (String) in.readObject();
        String id = (String) in.readObject();
        school = new School(name, id);
    }

    private Object readResolve(){
        return instance;
    }

    private Object writeReplace ( ) {
        //return new SerializationProxy4Student(this); // this：Student instance
        return null;
    }

    public class School{

        public String sName = null;
        public String sId = null;

        public School(){
            this.sName = "";
            this.sId = "";
        }
        public School(String name, String id){
            this.sName = name;
            this.sId = id;
        }
    }


}
