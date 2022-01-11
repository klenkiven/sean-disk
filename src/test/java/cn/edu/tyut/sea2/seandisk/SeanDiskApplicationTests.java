package cn.edu.tyut.sea2.seandisk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest
class SeanDiskApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        Map<String, Object> beansOfType =
                applicationContext.getBeansOfType(Object.class);
        for (Map.Entry<String, Object> stringObjectEntry : beansOfType.entrySet()) {
            Class<?> originClass = stringObjectEntry.getValue().getClass();
            Class<?> superclass = originClass.getSuperclass();
            System.out.println(stringObjectEntry.getKey());
            System.out.println("\tOrigin Class: " + originClass);
            System.out.println("\tSuperclass: " + superclass);
            System.out.println();
        }

    }

}
