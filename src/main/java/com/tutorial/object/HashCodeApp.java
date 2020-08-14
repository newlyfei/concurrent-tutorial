package com.tutorial.object;

import java.util.HashMap;
import java.util.Objects;

public class HashCodeApp {
    private String name;

    public HashCodeApp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("equals");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashCodeApp that = (HashCodeApp) o;
        return Objects.equals(name, that.name);
    }

//    @Override
//    public int hashCode() {
//        System.out.println("hashCode");
//        return Objects.hash(name);
//    }

    @Override
    public String toString() {
        System.out.println("toString");
        return "HashCodeApp{" +
                "name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        HashCodeApp hashCodeApp=new HashCodeApp("Jack");
        System.out.println(hashCodeApp.hashCode());


        HashMap<HashCodeApp, Integer> hashMap = new HashMap<HashCodeApp, Integer>();
        hashMap.put(hashCodeApp, 1);

        /**
         * 存在hashCode时为1，否则为null；原因就在于重写equals方法的同时忘记重写hashCode方法
         * 虽然通过重写equals方法使得逻辑上姓名和年龄相同的两个对象被判定为相等的对象（跟String类类似），
         * 但是要知道默认情况下，hashCode方法是将对象的存储地址进行映射。
         * 那么上述代码的输出结果为“null”就不足为奇了。原因很简单，p1指向的对象是两个对象，它们的存储地址肯定不同
         */
        System.out.println(hashMap.get(new HashCodeApp("Jack")));

    }
}
