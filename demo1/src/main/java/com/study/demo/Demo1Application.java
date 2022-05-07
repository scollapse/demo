package com.study.demo;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(Demo1Application.class, args);
        /*
            ConfigurableApplicationContext 是SpringApplication run方法返回的对象

            此接口结合了所有ApplicationContext需要实现的接口，因此大多数的ApplicationContext都要实现此接口

            它在ApplicationContext的基础上增加了一系列配置应用上下文的功能

            配置应用上下文和控制应用上下文生命周期的方法在此接口中被封装起来

            以免客户端程序直接使用
         */

        System.out.println(context);

        // 获取单例
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String,Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        map.entrySet().stream().filter(e -> e.getKey().startsWith("component")).forEach(e -> {
            System.out.println(e.getKey() + "=" + e.getValue());
        });

        //ApplicationContext
        //1.国际化  (此处不行  TestMessageSource 可以实现 )
//        System.out.println(context.getMessage("hi", null, Locale.CHINA));
//        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
//        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        //
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        //事件发布器

        context.getBean(Component1.class).register();
    }

}
