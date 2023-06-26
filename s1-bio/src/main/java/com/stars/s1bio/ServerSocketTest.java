package com.stars.s1bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {

    public static void main(String[] args) throws IOException {
        //创建一个新的 ServerSocket，用以 监听指定端口上的连接请求
        final ServerSocket serverSocket = new ServerSocket(5001);
        ;

        try {
            for (; ; ) {

                //对 accept () 方法的调 用将被阻塞，直到一 个连接建立
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter out = null;
                        try {
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
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (out != null)
                                out.close();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
