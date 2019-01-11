package com.tutorial.jvm;

/**
 * 栈：用于存储局部变量表、操作栈、动态链接、方法出口等信息<p>
 *
 * -Xss128k：设置每个线程的堆栈大小。
 * JDK5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。更具应用的线程所需内存大小进行调整。
 * 在相同物理内存下，减小这个值能生成更多的线程。
 * 但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右<p>
 *
 * @Described：栈层级不足探究
 * @VM args:-Xss128k
 * @author YHJ create at 2011-11-12 下午08:19:28
 */
public class StackOverFlow {
    private int i;

    public void plus() {
        i++;
        plus();
    }

    /**
     * @param args
     * @Author YHJ create at 2011-11-12 下午08:19:21
     */

    public static void main(String[] args) {
        StackOverFlow stackOverFlow = new StackOverFlow();
        try {
            stackOverFlow.plus();
        } catch (Exception e) {
            System.out.println("Exception:stack length:" + stackOverFlow.i);
            e.printStackTrace();
        } catch (Error e) {
            System.out.println("Error:stack length:" + stackOverFlow.i);
            e.printStackTrace();
        }
    }
}
