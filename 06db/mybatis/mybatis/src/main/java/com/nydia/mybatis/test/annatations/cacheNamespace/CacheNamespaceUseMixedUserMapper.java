package com.nydia.mybatis.test.annatations.cacheNamespace;

import com.nydia.mybatis.entity.User;
import org.apache.ibatis.annotations.CacheNamespaceRef;


@CacheNamespaceRef(value = CacheNamespaceUseAnnatationUserMapper.class)
public interface CacheNamespaceUseMixedUserMapper {

    User selectById(Integer id);


}
