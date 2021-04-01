package com.jcom.cms;

import java.util.*;
import java.util.stream.Stream;

public class TestCl {
    List<String> list = new ArrayList<>();
    Map<String,String> m=new HashMap<>();
    LinkedHashMap<Integer, Integer> mint=new LinkedHashMap<>();
    Collection<String> collection = new ArrayList<>();
    List<Integer> intd = new ArrayList<>();

    public void method1(){
        setArr();

        m.forEach((k,v)->{System.out.println("k is:"+k);});
       mint.entrySet().stream().filter(entry -> entry.getValue() == Collections.max(mint.values()));

        //Integer dd1 = mint.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue()&& entry1.getValue()>2 && entry2.getValue()>2 ? 1 : -1).get().getKey();
        //System.out.println(dd1);

     for(Map.Entry<Integer, Integer> entry: mint.entrySet()){

       }

       /*
      for(Map.Entry<String,String> entry : m.entrySet()){
          System.out.println(entry.getKey()+entry.getValue());
      }

      m.forEach((k,v)->{System.out.println(k);});
        list.forEach(val->{System.out.println(val);});

      for(int i=0;i<list.size();i++){

      }
      String[] data = {"a","b","c","d"};

      for(int i = 0; i<data.length;i++){

      }

        */

    }

   public void setArr(){
       list.add("1");
       list.add("2");
       list.add("3");
       list.add("4");

       m.put("a","1");
       m.put("b","2");
       m.put("c","3");
       m.put("d","4");

       collection.add("eee");
       collection.add("ee2");
       collection.add("ee3");

       intd.add(1);
       intd.add(3);
       intd.add(4);

       mint.put(4,1);
       mint.put(2,2);
       mint.put(5,2);
       mint.put(3,1);

    }

}
