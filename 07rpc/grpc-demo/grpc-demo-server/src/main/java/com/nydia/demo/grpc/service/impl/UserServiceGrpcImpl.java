package com.nydia.demo.grpc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nydia.demo.grpc.dao.entity.UserDemo;
import com.nydia.demo.grpc.dao.mapper.UserDemoMapper;
import com.nydia.demo.grpc.user.dto.AddUserRequest;
import com.nydia.demo.grpc.user.dto.SearchUserRequest;
import com.nydia.demo.grpc.user.dto.UserResponse;
import com.nydia.demo.grpc.user.service.UserGrpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * 用户管理grpc服务实现
 */
@GrpcService
@Slf4j
public class UserServiceGrpcImpl extends UserGrpc.UserImplBase {

    @Resource
    UserDemoMapper userDemoMapper;

    /**
     * 添加用户信息rpc接口
     * @param request rpc接口请求参数
     * @param responseObserver rpc流式响应
     */
    @Override
    public void add(AddUserRequest request, StreamObserver<UserResponse> responseObserver) {

        log.info("start add");

        UserDemo ud = new UserDemo();
        ud.setName(request.getName());
        ud.setAge(request.getAge());
        ud.setAddress(request.getAddress());
        userDemoMapper.insert(ud);
        
        //构造rpc响应参数
        UserResponse reply = UserResponse.newBuilder().setId(ud.getId()).setName(request.getName()).setAge(ud.getAge())
            .setAddress(ud.getAddress()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        log.info("end add");
    }
    
    /**
     * 查询用户列表
     * @param request rpc接口请求参数
     * @param responseObserver rpc流式响应，返回多个消息
     */
    @Override
    public void list(SearchUserRequest request, StreamObserver<UserResponse> responseObserver) {

        LambdaQueryWrapper<UserDemo> lq = new LambdaQueryWrapper<UserDemo>();
        if(StringUtils.hasText(request.getName())) {
            lq.eq(UserDemo::getName, request.getName());
        }
        List<UserDemo> list = userDemoMapper.selectList(lq);
        list.stream().forEach(c -> {
            UserResponse ur = UserResponse.newBuilder().setAddress(c.getAddress()).setAge(c.getAge()).setId(c.getId())
                .setName(c.getName()).build();
            responseObserver.onNext(ur);
        });
        responseObserver.onCompleted();
    }
    
    
    /**
     * 批量添加用户
     * @param responseObserver rpc流式响应，返回多个消息
     * @return
     */
    @Override
    public StreamObserver<AddUserRequest> addBatch(StreamObserver<UserResponse> responseObserver) {
        return new StreamObserver<AddUserRequest>() {
            
            List<UserDemo> userList = new ArrayList<UserDemo>();
            
            @Override
            public void onNext(AddUserRequest value) {
                UserDemo userDemoEntity = new UserDemo();
                userDemoEntity.setName(value.getName());
                userDemoEntity.setAge(value.getAge());
                userDemoEntity.setAddress(value.getAddress());
                userDemoMapper.insert(userDemoEntity);
                
                userList.add(userDemoEntity);
                
                UserResponse ur =
                    UserResponse.newBuilder().setAddress(userDemoEntity.getAddress()).setAge(userDemoEntity.getAge())
                        .setId(userDemoEntity.getId()).setName(userDemoEntity.getName()).build();
                responseObserver.onNext(ur);
            }
            
            @Override
            public void onCompleted() {
                
                responseObserver.onCompleted();
            }
            
            @Override
            public void onError(Throwable t) {
                log.info("addBatch StreamObserver onError："+t.getMessage(), t);
            }
        };
    }
    
}