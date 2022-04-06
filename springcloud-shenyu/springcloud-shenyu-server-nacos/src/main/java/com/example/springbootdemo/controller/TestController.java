/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.springbootdemo.controller;

import com.example.springbootdemo.dto.UserDTO;
import org.apache.shenyu.client.springcloud.annotation.ShenyuSpringCloudClient;
import org.springframework.web.bind.annotation.*;

/**
 * TestController.
 */
@RestController
@RequestMapping("/test")
@ShenyuSpringCloudClient(path = "/test/**")
public class TestController {


    /**
     * Post string.
     *
     * @param userDTO the user dto
     * @return the string
     */
    @PostMapping("/save")
    public UserDTO post(@RequestBody final UserDTO userDTO) {
        userDTO.setUserName("hello world spring cloud save user");
        return userDTO;
    }

    /**
     * Find by user id user dto.
     *
     * @param userId the user id
     * @return the user dto
     */
    @GetMapping("/findByUserId")
    public UserDTO findByUserId(@RequestParam("userId") final String userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName("hello world spring cloud findBy user");
        return userDTO;
    }
}
