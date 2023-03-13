package com.example.demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Date 2023/3/10 9:32
 * @Created by <a href="mailto:nydia_lhq@hotmail.com">lvhuaqiang</a>
 */
@Service
public class DemoService {

    @Autowired
    private UserRepository userRepository;

    public void add(){
        UserEntity userEntity = userRepository.save(new UserEntity(1L,"nihao",20));
        System.out.println(userEntity);
    }

    public void get(){
        UserEntity userEntity = userRepository.getById(1L);
        System.out.println(userEntity.getName());
    }


}
