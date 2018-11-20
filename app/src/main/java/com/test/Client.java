package com.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public Client() {

    }

    public synchronized void send() throws UnknownHostException, IOException, InterruptedException {
        Socket socket = new Socket("localhost", 8007);
        OutputStream out = socket.getOutputStream();
        out.write("hello".getBytes());
        wait();
        // 这边不能关闭流和socket
        // out.close();
        // socket.close();
    }
}
