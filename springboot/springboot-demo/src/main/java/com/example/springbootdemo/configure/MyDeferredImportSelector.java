package com.example.springbootdemo.configure;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

public class MyDeferredImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        System.out.println("selectImports方法执行了---->");
        return new String[0];
    }

    //重写 getImportGroup 方法，上面的 selectImports 就不会执行
    @Override
    public Class<? extends Group> getImportGroup() {
        System.out.println("getImportGroup");
        return MyDeferredImportSelectorGroup.class;
    }

    public static class MyDeferredImportSelectorGroup implements Group{
        private final List<Entry> imports = new ArrayList<>();
        @Override
        public void process(AnnotationMetadata metadata, DeferredImportSelector selector) {
            System.out.println("MyDeferredImportSelectorGroup.Group");
        }

        @Override
        public Iterable<Entry> selectImports() {
            System.out.println("Group中的：selectImports方法");
            return imports;
        }
    }


}