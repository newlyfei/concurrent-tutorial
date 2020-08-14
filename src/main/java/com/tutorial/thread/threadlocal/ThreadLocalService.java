package com.tutorial.thread.threadlocal;

/**
 * ThreadLocal从本质上讲，是提供了一个“线程级”的变量作用域，
 * 它是一种线程封闭（每个线程独享变量）技术，
 * 更直白点讲，ThreadLocal可以理解为将对象的作用范围限制在一个线程上下文中，使得变量的作用域为“线程级”。
 */
public class ThreadLocalService {
    public static void writeValue(){
        System.out.println(ThreadLocalApp.getNum());
    }
}
