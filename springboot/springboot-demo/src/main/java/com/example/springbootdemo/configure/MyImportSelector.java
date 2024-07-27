package com.example.springbootdemo.configure;

import com.example.springbootdemo.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        LogUtil.info(log, "ImportSelector # selectImports");
        return new String[0];
    }

}