package com.tutorial;

import java.io.IOException;
import java.lang.ref.*;
import java.util.Map;
import java.util.WeakHashMap;

public class Test {

    /**
     * 打印字符Ascii码
     */
    public void printAsciiCode(){
        try {
            int c=System.in.read();
            System.out.println("ascii value: "+c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数值比较
     */
    public void IntegerCompare(){
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;

        System.out.println(f1 == f2);
        System.out.println(f3 == f4);
    }

    /**
     * 强引用回收
     */
    public static void gcReference(){
        Object a=new java.lang.Object();
        Object b=a;

        a=null;
        System.gc();
        System.out.println(b);
    }

    /**
     * 内存不足时才进行gc空间的释放
     * 应用场景：在开发的时候软引用能够实现高速缓存
     */
    public static void softReference(){
        String name=new String("zhangsan");
        SoftReference<String> reference=new SoftReference<>(name);  //创建软引用
        name=null;
        System.gc();
        System.out.println(reference.get());
    }

    /**
     * 弱引用，出现gc立即回收
     */
    public static void weakReference(){
        String name=new String("zhangsan");
        WeakReference<String> reference=new WeakReference<>(name);  //创建弱引用
        name=null;
        System.gc();
        System.out.println(reference.get());
    }

    /**
     * 弱引用，WeakHashMap下的gc立即回收
     * 应用场景：共享数据，这些共享数据如果长时间不使用，可以将其清空。
     */
    public static void weakHashReference(){
        Map<Integer,String> item=new WeakHashMap<>();
        item.put(1,"A");
        item.put(2,"B");
        item.put(3,"C");

        Map<Integer,String> item2=new WeakHashMap<>();
        item.put(new Integer(1),new String("A"));
        item.put(new Integer(2),new String("B"));
        item.put(new Integer(3),new String("C"));
        System.gc();    //必须是引用类型，不然不能被回收
        System.out.println(String.format("%s - %s",item,item2));
    }

    /**
     * 幽灵引用，等于没有引用
     * 应用场景：用于检查对象是否还有引用存在
     */
    public static void phantomReference(){
        String name=new String("zhangsan");
        ReferenceQueue<String> queue=new ReferenceQueue<>();
        PhantomReference<String> pr=new PhantomReference<>(name,queue);
        System.out.println(pr.isEnqueued());    //有引用
        System.out.println(pr.get());   //得不到东西
        System.out.println(queue.poll());   //null

        name=null;
        System.gc();
        System.out.println(pr.isEnqueued());    //有引用
        System.out.println(pr.get());   //得不到东西
        System.out.println(queue.poll());   //
    }

    public static void main(String[] args) {
//        Test.gcReference();
//        Test.softReference();
//        Test.weakReference();
//        Test.weakHashReference();
        Test.phantomReference();
    }
}
