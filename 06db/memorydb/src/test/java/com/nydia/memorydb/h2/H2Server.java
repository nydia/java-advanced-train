package com.nydia.memorydb.h2;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/7/30 21:22
 * @Description: H2Server
 */
@Slf4j
public class H2Server {

    public static void start() {
        try {
            // 启动TCP服务器
            Server.createTcpServer("-tcp", "-tcpAllowOthers",
                    "-tcpPort", H2Properties.TCP_PORT, "-ifNotExists",
                    //"-baseDir", H2Properties.BASE_DIR,
                    "-pg").start();
        } catch (Exception e) {
            log.error("启动H2 Server 失败", e);
            throw new RuntimeException("启动H2 Server 失败");
        }

    }

    public static void main(String[] args) {
        start();
    }

}
