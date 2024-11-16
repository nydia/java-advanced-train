package com.nydia.mybatis.controller;

import com.nydia.mybatis.entity.PermMenu;
import com.nydia.mybatis.service.IPermMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private IPermMenuService menuService;


    @RequestMapping(value = "/insert")
    public String insert() {
        List<PermMenu> list = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            PermMenu menu = new PermMenu();
            menu.setBelongAdmId("101");
            menu.setState(false);
            menu.setCategory("USER");
            menu.setRelatedPageFlag(false);
            menu.setDownAdmFlag(false);
            menu.setMenuCodeSegment("MNUZ" + i);
            menu.setMenuCode("MNUZ" + i);
            menu.setMenuName("MNUZ");
            menu.setMenuRoute("PUBLIC");
            menu.setPageCode("");
            menu.setParentId("1828744363711545345");
            menu.setTreeCode("100000000000");
            menu.setCreateBy("10000");
            list.add(menu);
        }
        menuService.saveBatch(list);
        return "success";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        menuService.removeById(id);
        return "success";
    }

}
