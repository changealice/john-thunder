package com.nepxion.thunder.test;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ThunderServerTest {    
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath*:netty-server-context-pressure.xml");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}