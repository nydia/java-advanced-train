package com.nydia.mybatis.test.io;

import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.io.VFS;
import org.junit.Test;

import java.util.Set;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/9/5 23:54
 * @Description: VFSTest
 */
public class VFSTest {

    @Test
    public void test1(){
        ResolverUtil<VFS> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(VFS.class), "org.apache.ibatis.io");
        Set<Class<? extends VFS>> classSets = resolverUtil.getClasses();
        // org.apache.ibatis.io.VFS
        // org.apache.ibatis.io.DefaultVFS
        // org.apache.ibatis.io.JBoss6VFS
        classSets.forEach(c -> System.out.println(c.getName()));
    }

}
