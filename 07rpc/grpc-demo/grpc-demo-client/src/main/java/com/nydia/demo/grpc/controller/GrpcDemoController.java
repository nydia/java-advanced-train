package com.nydia.demo.grpc.controller;


import com.alibaba.fastjson.JSONObject;
import com.nydia.demo.grpc.user.dto.AddUserRequest;
import com.nydia.demo.grpc.user.dto.UserResponse;
import com.nydia.demo.grpc.user.service.UserGrpc;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GrpcDemoController {

    @GrpcClient("userDemoClient")
    private UserGrpc.UserBlockingStub blockingStub;

    @GetMapping("/helloGrpc")
    public String helloGrpc(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.info("开始helloGrpc");
        AddUserRequest request = AddUserRequest.newBuilder().setAddress("长沙").setAge(28).setName("啊虎1").build();
        UserResponse response;
        try {
            response = blockingStub.add(request);
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: " + e.getMessage(), e);
            throw e;
        }
        log.info("testAdd结果: " + response.getName());

        return JSONObject.toJSONString(response.getName());
    }
}
