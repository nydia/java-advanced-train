package com.nydia.mybatisplus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Types;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

//快速生成代码

@SpringBootTest
public class GeneratorTest {

    @Test
    public void gen() {
        FastAutoGenerator.create("jdbc:h2:mem:test2;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "root", "test")
                .globalConfig(builder -> {
                    builder.author("nydia") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir("C://temp//code//")
                            .enableSpringdoc()
                            .commentDate("yyyy-MM-dd")
                    //.enableKotlin()
                    ; // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.nydia") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .xml("mapper.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C://temp//code//")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的表名
                            .addTablePrefix("t_", "p_")// 设置过滤表前缀
                            .entityBuilder()
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .enableTableFieldAnnotation()
                            .enableChainModel()
                            .enableLombok()
                            .idType(IdType.ASSIGN_ID)
                            .enableChainModel()
                            .logicDeleteColumnName("del_f")
                    ;
                    builder.controllerBuilder().enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateConfig(new Consumer<TemplateConfig.Builder>() {
                    @Override
                    public void accept(TemplateConfig.Builder builder) {
                        builder.controller("/templates/controller.java");
                    }
                })
                .execute();
    }
}