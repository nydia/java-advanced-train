package com.nydia.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nydia.mybatis.entity.PermMenu;
import com.nydia.mybatis.mapper.PermMenuMapper;
import com.nydia.mybatis.service.IPermMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PermMenuServiceImpl extends ServiceImpl<PermMenuMapper, PermMenu> implements IPermMenuService {

}
