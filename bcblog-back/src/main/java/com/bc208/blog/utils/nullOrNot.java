package com.bc208.blog.utils;

public class nullOrNot {
    public static void istrue(Boolean flag,String msg){
        if(flag){
         throw new ParamsException(msg);
        }
    }
}
