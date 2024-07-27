package com.example.springbootdemo.configure;

import com.example.springbootdemo.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyDeferredImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        LogUtil.info(log, "DeferredImportSelector # selectImports");
        return new String[0];
    }

    //重写 getImportGroup 方法，上面的 selectImports 就不会执行
    @Override
    public Class<? extends Group> getImportGroup() {
        LogUtil.info(log, "DeferredImportSelector # getImportGroup");
        return MyDeferredImportSelectorGroup.class;
    }

    public static class MyDeferredImportSelectorGroup implements Group{
        private final List<Entry> imports = new ArrayList<>();
        @Override
        public void process(AnnotationMetadata metadata, DeferredImportSelector selector) {
            LogUtil.info(log, "DeferredImportSelector # Group # process");
        }

        @Override
        public Iterable<Entry> selectImports() {
            LogUtil.info(log, "DeferredImportSelector # Group # selectImports");
            return imports;
        }
    }


}