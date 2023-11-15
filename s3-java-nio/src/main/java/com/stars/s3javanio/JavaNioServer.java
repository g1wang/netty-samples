package com.stars.s3javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class JavaNioServer {
    public void start(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocket.bind(address);
        Selector selector = Selector.open();
        //将serverSocket注册到Selector以接收连接
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        for (; ; ) {

            try {
                //等待需要处理的新事件；阻塞 将一直持续到下一个传入事件
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                        SocketChannel client = ssChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                        System.out.println("Accepted connection from " + client);
                    }
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        System.out.println("SC 2:" + socketChannel);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        if (socketChannel.read(byteBuffer) != -1) {
                            String content = new String(byteBuffer.array()).trim();
                            System.out.println("收到的客户端数据：" + content);
                        }
                        byteBuffer.clear();
                        //回复数据
                        String reply = "thanks";
                        socketChannel.write(ByteBuffer.wrap(reply.getBytes()));
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        client.close();
                    }

                } catch (IOException e) {
                    System.out.println();
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ex) {
                    }
                }
                iterator.remove();
            }
        }
    }
}
