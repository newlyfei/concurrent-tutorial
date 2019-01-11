package com.tutorial.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:PermSize=64M JVM初始分配的非堆内存
 * -XX:MaxPermSize=128M JVM最大允许分配的非堆内存，按需分配
 *
 * @Description ：常量池内存溢出探究
 * @VM args : -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class ConstantOutOfMemory {
      public static void main(String[] args) throws Exception {
        try {
            List<String> strings = new ArrayList<String>();
            int i = 0;
            while(true){
                strings.add(String.valueOf(i++).intern());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
