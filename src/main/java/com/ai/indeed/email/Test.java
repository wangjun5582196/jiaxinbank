package com.ai.indeed.email;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
       int a=50;

       if(a>1){
           System.out.println(1);
       }else if(a>2){
           System.out.println(2);
       }



    }

    public  static boolean getXO(String str) {
        if("".equals(str)){
            return false;
        }
        Boolean flag=false;
        Integer length=str.length();
        Set<String> strSet = new HashSet<>();
        for(int i=0;i<length;i++){
            String temp=str.substring(i,i+1);
            if(strSet.contains(temp)){
                flag=true;
                break;
            }else{
                strSet.add(temp);
            }
        }
        // 在这⾥写代码
        return flag ;

    }
}
