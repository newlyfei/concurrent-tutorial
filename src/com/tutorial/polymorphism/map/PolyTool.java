package com.tutorial.polymorphism.map;

import com.tutorial.polymorphism.map.impl.PolyAServiceImpl;
import com.tutorial.polymorphism.map.impl.PolyBServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class PolyTool {
    private static final Map<String,PolyService> poly=new HashMap<String,PolyService>();


    public static void init(){
        poly.put("polyAService",new PolyAServiceImpl());
        poly.put("polyBService",new PolyBServiceImpl());
    }

    public static PolyService getService(String serviceName){
        return poly.get(serviceName);
    }
}
