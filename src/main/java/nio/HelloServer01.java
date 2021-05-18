package nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright (C) 2021 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description: HelloServer01
 * @ClassName: HelloServer01
 * @Auther: Nydia.LHQ
 * @Date: 2021/5/12 13:27
 */
public class HelloServer01 {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(90);
            while (true) {
                Socket socket = serverSocket.accept();
                service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void service(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), Boolean.TRUE);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Conten-Type:text/html;charset-8");
            String body = "hello";
            printWriter.println("Conten-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
