package com.test;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    InputStream inputStream;
    ServerSocket serverSocket;
    HandleThread handleThread;

    Server() {
        try {
            /**
             * 创建一个本地套接字，循环接收客户端发送的数据并输出
             */
            serverSocket = new ServerSocket(8007);
            Socket socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            handleThread = new HandleThread(inputStream);
            handleThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class HandleThread extends Thread {
        InputStream in;

        public HandleThread(InputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            try {
                int len = 0;
                byte[] buff = new byte[255];
                while (!Thread.interrupted()) {
                    len = in.read(buff);
                    if (len != -1) {
                        for (int i = 0; i < len; i++) {
                            Log.d("hjw_test", "" + buff[i]);
                        }
                    } else {
                        Log.d("hjw_test", "the end of stream");
                        break;
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("hjw_test", "IOException e = " + e);
            }
            Log.d("hjw_test", "HandleThread stop");
        }

        public void close() throws IOException {
            Log.d("hjw_test", "in.close");
            in.close();
        }
    }

    public void close() throws IOException {
        handleThread.interrupt();
        inputStream.close();
        handleThread.close();
        serverSocket.close();
        Log.d("hjw_test", "server close");
    }

}