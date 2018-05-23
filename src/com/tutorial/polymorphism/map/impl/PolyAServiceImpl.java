package com.tutorial.polymorphism.map.impl;

import com.tutorial.polymorphism.map.PolyBean;
import com.tutorial.polymorphism.map.PolyService;

public class PolyAServiceImpl implements PolyService {
    @Override
    public PolyBean parseJson(String json) {
        //这里应该是json解析过程
        PolyBean polyBean=new PolyBean();
        polyBean.setName("PolyAServiceImpl");
        return polyBean;
    }
}
