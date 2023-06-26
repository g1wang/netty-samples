package com.stars.s1bio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClientTest {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            Socket socket = null;
            PrintWriter out = null;
            BufferedReader in = null;
            InetAddress inet = null;
            try {
                inet = InetAddress.getByName("127.0.0.1");
                socket = new Socket(inet, 5001);
                //输出流 os对象初始化
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                out.println("who am I ?");
                out.println("say hi "+i);
                out.println("Done");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null)
                    System.out.println(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (out != null)
                    out.close();

            }
        }



    }
}
