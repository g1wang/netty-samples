package com.stars.s3javanio;

import java.io.IOException;

public class JavaNioServerTest {

    public static void main(String[] args) throws IOException {
        JavaNioServer javaNioServer = new JavaNioServer();
        javaNioServer.server(5001);

    }
}
