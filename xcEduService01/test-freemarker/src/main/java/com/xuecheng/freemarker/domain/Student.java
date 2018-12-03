package com.xuecheng.freemarker.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class Student {

    private String name;//姓名     
    private int age;//年龄     
    private Date birthday;//生日     
    private Float money;//钱包     
    /*private List<Student> friends;//朋友列表     
    private Student bestFriend;//好的朋友*/

    public Student() {
    }

    public Student(String name, int age, Date birthday, Float money /*,List<Student> friends, Student bestFriend*/) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.money = money;
        /*this.friends = friends;
        this.bestFriend = bestFriend;*/
    }
}
