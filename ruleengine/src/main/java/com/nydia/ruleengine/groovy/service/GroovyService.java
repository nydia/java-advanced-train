package com.nydia.ruleengine.groovy.service;

import com.nydia.ruleengine.groovy.EngineGroovyModuleRule;
import com.nydia.ruleengine.groovy.entity.Context;
import groovy.lang.GroovyClassLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/4/2 00:21
 * @Description: GroovyService
 */
@Service
public class GroovyService {

    public void run() {
        try {


            ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap(128);
            final String path = "classpath*:*.groovy_template";
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Arrays.stream(resolver.getResources(path))
                    .parallel()
                    .forEach(resource -> {
                        try {
                            String fileName = resource.getFilename();
                            InputStream input = resource.getInputStream();
                            InputStreamReader reader = new InputStreamReader(input);
                            BufferedReader br = new BufferedReader(reader);
                            StringBuilder template = new StringBuilder();
                            for (String line; (line = br.readLine()) != null; ) {
                                template.append(line).append("\n");
                            }
                            concurrentHashMap.put(fileName, template.toString());
                        } catch (Exception e) {
                            //log.error("resolve file failed", e);
                        }
                    });
            String scriptBuilder = concurrentHashMap.get("ScriptTemplate.groovy_template");
            String scriptClassName = "testGroovy";
            //这一部分String的获取逻辑进行可配置化
            String StrategyLogicUnit = "if(context.amount>=20000){\n" +
                    "            context.nextScenario='A'\n" +
                    "            return true\n" +
                    "        }\n" +
                    "        ";
            String fullScript = String.format(scriptBuilder, scriptClassName, StrategyLogicUnit);


            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class<EngineGroovyModuleRule> aClass = classLoader.parseClass(fullScript);
            Context context = new Context();
            context.setAmount(30000);
            try {
                EngineGroovyModuleRule engineGroovyModuleRule = aClass.newInstance();
                System.out.println("Groovy Script returns:{} " + engineGroovyModuleRule.run(context));
                System.out.println("Next Scenario is {}" + context.getNextScenario());
            } catch (Exception e) {
                System.out.println("error...");
            }
        } catch (Exception e) {

        }
    }

}
