package com.nydia.demo.grpc;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.nydia.demo.grpc.user.dto.AddUserRequest;
import com.nydia.demo.grpc.user.dto.SearchUserRequest;
import com.nydia.demo.grpc.user.dto.UserResponse;
import com.nydia.demo.grpc.user.service.UserGrpc;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

/**
 * grpc client调用测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrpcApplication.class)
@Slf4j
public class UserGrpcTest {
    
    @GrpcClient("inProcess")
    private UserGrpc.UserBlockingStub blockingStub;

    @GrpcClient("inProcess")
    private UserGrpc.UserStub userStub;

    @Test
    public void addTest() {

        log.info("开始testAdd");
        AddUserRequest request = AddUserRequest.newBuilder().setAddress("长沙").setAge(28).setName("啊虎1").build();
        UserResponse response;
        try {
            response = blockingStub.add(request);
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: " + e.getMessage(), e);
            throw e;
        }
        log.info("testAdd结果: " + response.getName());
    }

    @Test
    public void listTest() {

        log.info("开始listTest");
        SearchUserRequest request = SearchUserRequest.newBuilder().setName("虎").build();
        Iterator<UserResponse> response;
        try {
            response = blockingStub.list(request);
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: " + e.getMessage(), e);
            throw e;
        }

        while (response.hasNext()) {
            log.info("listTest结果: " + response.next());
        }
    }
    
    
    @Test
    public void addBatchTest() {

        log.info("开始addBatchTest");

        try {

            StreamObserver<UserResponse> responseObserver = new StreamObserver<UserResponse>() {

                @Override
                public void onNext(UserResponse value) {
                    log.info("responseObserver onNext：" + JSONObject.toJSONString(value));
                }

                @Override
                public void onError(Throwable t) {
                    log.info("responseObserver onError：" + t.getMessage(), t);
                }

                @Override
                public void onCompleted() {
                    log.info("responseObserver onCompleted：");
                }
            };

            StreamObserver<AddUserRequest> requestObserver = userStub.addBatch(responseObserver);

            // 循环插入的数据
            for (int i = 0; i < 5; i++) {
                
                log.info("===========客户端发送第{}条数据", i);
                
                AddUserRequest request =
                    AddUserRequest.newBuilder().setAddress("长沙-" + i).setAge(5 + i).setName("啊虎" + i).build();
                requestObserver.onNext(request);
                
                try {
                    Thread.sleep(1000*30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            requestObserver.onCompleted();
            
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: " + e.getMessage(), e);
            throw e;
        }
    }
}
