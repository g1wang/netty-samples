package com.stars.s3javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class JavaNioClient {

    public void start(String host, Integer port, String data) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
            while (!socketChannel.finishConnect()) {
            }
//            while ((socketChannel.validOps()&SelectionKey.OP_WRITE)==SelectionKey.OP_WRITE){
//
//            }
            ByteBuffer buf = ByteBuffer.wrap(data.getBytes());
            socketChannel.write(buf);
            buf.clear();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            while (socketChannel.read(buffer) != 0) {
                buffer.flip();
                while (buffer.hasRemaining()){
                    content.append((char) buffer.get());
                }
                System.out.println(content);
            }
            System.out.println("结束");
            socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        JavaNioClient javaNioClient = new JavaNioClient();
        javaNioClient.start("127.0.0.1", 5001, "send data 111");
    }
}
