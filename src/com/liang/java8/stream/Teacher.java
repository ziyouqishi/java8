package com.liang.java8.stream;

import java.util.List;

public class Teacher {
    private Integer age;
    private String name;
    private String sex;
    private List<Student> students;

    public Teacher(Integer age, String name,String sex) {
        this.age = age;
        this.name = name;
        this.sex=sex;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", students=" + students +
                '}';
    }
}
