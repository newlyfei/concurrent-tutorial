package com.tutorial.arithmetic;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @ClassName: FixSizedPriorityQueue
 * @Description: 固定容量的优先队列，模拟大顶堆，用于解决求topN小的问题
 * @author zengfh
 * @date 2014年11月24日 上午10:44:48
 *
 */
public class FixSizedPriorityQueue <E extends Comparable<E>>{
    private PriorityQueue<E> queue;
    private int maxSize;// 堆的最大容量

    public FixSizedPriorityQueue(int maxSize) {
        if(maxSize <=0){
            throw new IllegalArgumentException();
        }
        this.maxSize = maxSize;
        this.queue =new PriorityQueue<E>(maxSize,new Comparator<E>() {
            // 生成最大堆使用o2-o1,生成最小堆使用o1-o2, 并修改 e.compareTo(peek) 比较规则
            public int compare(E o1, E o2) {
                return(o2.compareTo(o1));
            }
        });
    }

    public void add(E e){
        if(queue.size() < maxSize) {// 未达到最大容量，直接添加
            queue.add(e);
        }else{// 队列已满
            E peek = queue.peek();
            if(e.compareTo(peek) <0) {// 将新元素与当前堆顶元素比较，保留较小的元素
                queue.poll();
                queue.add(e);
            }
        }
    }

    /**
     * @Title: main
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param args    设定文件
     * @return void    返回类型
     * @throws
     */
    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        final FixSizedPriorityQueue<Integer> pq =new FixSizedPriorityQueue<Integer>(10);
        Random random =new Random();
        int rNum =0;
        System.out.println("100 个 0~999 之间的随机数：-----------------------------");
        for(int i =1; i <=100; i++) {
            rNum = random.nextInt(1000);
            System.out.print(rNum +", ");
            pq.add(rNum);
        }
        System.out.println();
        System.out.println("PriorityQueue 本身的遍历是无序的：------------------------");
        Iterable<Integer> iter =new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                // TODO 自动生成的方法存根
                return pq.queue.iterator();
            }

        };
        for(Integer item : iter) {
            System.out.print(item +", ");
        }
        System.out.println();
        System.out.println(pq.queue.toString());
        System.out.println("PriorityQueue 排序后的遍历：--------------------------");
        // 直接用内置的 poll() 方法，每次取队首元素（堆顶的最大值）
        while(!pq.queue.isEmpty()) {
            System.out.print(pq.queue.poll() +", ");
        }
    }

}