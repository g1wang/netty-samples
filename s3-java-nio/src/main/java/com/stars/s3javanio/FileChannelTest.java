package com.stars.s3javanio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile ("D:/a.txt","rw");
        FileChannel fileChannel = file.getChannel();
        // 读数据
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int bytesRead = fileChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = fileChannel.read(buf);
        }
        // 写数据
        String wData = "\n这是写入的，this is write,123456";
        ByteBuffer wbuf = ByteBuffer.allocate(1024);
        wbuf.put(wData.getBytes());
        wbuf.flip();
        while (wbuf.hasRemaining()) {
            fileChannel.write(wbuf);
        }
        wbuf.clear();
        fileChannel.close();
    }
}
