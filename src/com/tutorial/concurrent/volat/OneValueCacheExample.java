package com.tutorial.concurrent.volat;

import java.math.BigInteger;
import java.util.Arrays;

public class OneValueCacheExample {
    private volatile OneValueCache cache=new OneValueCache(null,null);

    class OneValueCache{
        private final BigInteger lastNumber;
        private final BigInteger[] lastFactors;

        public OneValueCache(BigInteger i, BigInteger[] factors){
            lastNumber=i;
            lastFactors= Arrays.copyOf(factors,factors.length);
        }

        public BigInteger[] getFactors(BigInteger i){
            if(lastNumber==null || !lastNumber.equals(i)){
                return null;
            }else{
                return Arrays.copyOf(lastFactors,lastFactors.length);
            }
        }
    }

    public void run(){
        BigInteger i=new BigInteger("1");
        BigInteger[] factors=cache.getFactors(i);
        if(factors==null){
            factors=factor(i);
            cache=new OneValueCache(i,factors);
        }
    }

    private BigInteger[] factor(BigInteger i) {
        BigInteger[] val=new BigInteger[5];
        val[0]=BigInteger.valueOf(12312);
        val[1]=BigInteger.valueOf(22312);
        val[2]=BigInteger.valueOf(32312);
        val[3]=BigInteger.valueOf(42312);
        val[4]=BigInteger.valueOf(52312);
        return val;
    }


    public static void main(String[] args) {
        int t=5;
        while(t>0){
            t--;
            new Thread(){
                public void run(){
                    OneValueCacheExample oneValueCacheExample=new OneValueCacheExample();
                    oneValueCacheExample.run();
                }
            }.start();
        }
    }
}
