package com.tutorial.polymorphism.map;

import com.tutorial.polymorphism.map.PolyTool;

public class PolyMain {

    public PolyMain(){
        PolyTool.init();
    }

    public PolyBean resolver(String json,String service){
        PolyService polyService=PolyTool.getService(service);
        return polyService.parseJson(json);

    }

    public static void main(String[] args) {
        PolyMain polyMain=new PolyMain();

        PolyBean polyBean=polyMain.resolver("{}","polyAService");
        System.out.println(String.format("name=%s",polyBean.getName()));

        polyBean=polyMain.resolver("{}","polyBService");
        System.out.println(String.format("name=%s",polyBean.getName()));

    }
}
