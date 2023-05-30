package com.stars.s1bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {

    public static void main(String[] args) {
        //创建一个新的 ServerSocket，用以 监听指定端口上的连接请求
        ServerSocket serverSocket;
        PrintWriter out = null;
        try {
            serverSocket = new ServerSocket(5001);
            //对 accept () 方法的调 用将被阻塞，直到一 个连接建立
            Socket clientSocket = serverSocket.accept();
            //这些流对象都派生于该套接字的流对象
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            String requestStr, responseStr;
            while ((requestStr = in.readLine()) != null) {
                System.out.println(requestStr);
                if ("Done".equals(requestStr))
                    break;
                responseStr = "response:" + requestStr;
                //服务器的响应被,发送给了客户端
                out.println(responseStr);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null)
                out.close();
        }


    }
}
