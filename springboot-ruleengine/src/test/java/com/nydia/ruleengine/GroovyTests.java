package com.nydia.ruleengine;

import com.nydia.ruleengine.groovy.service.GroovyClassLoaderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class GroovyTests {

    @Autowired
    private GroovyClassLoaderService groovyService;

    @Test
    void contextLoads() {
    }

    @Test
    public void groovy() {
        groovyService.run();
    }

}
