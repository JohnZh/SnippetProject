package com.john.common.socket;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by JohnZh on 2020/10/14
 *
 * <p></p>
 */
public class SocketConnector implements Connector {
    public static final int CON_MAX_RETRY = 2;

    class WriteTask implements Runnable {

        @Override
        public void run() {
            while (!socket.isConnected()) {
                try {
                    socket.connect(new InetSocketAddress(host, port));
                    connecting = false;
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    if (socket.isConnected()) break;
                    if (connectRetry == CON_MAX_RETRY) {
                        onConnectError();
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    onHostOrPortError();
                    return;
                }
                connectRetry++;
            }

            readTask = new ReadTask();
            readThread = new Thread(readTask);
            readThread.start();

            while (true) {
                try {
                    String s = queue.take();
                    codec.write(s);
                } catch (IOException e) {
                    e.printStackTrace();
                    onIOError(true, e);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if (quit) {
                        Log.d("Temp", "Write thread quit"); // todo remove later
                        break;
                    }
                }
            }
        }
    }

    private void onIOError(boolean write, IOException e) {
        Log.d("Temp", "onIOError: write thread ? " + write); // todo remove later
        // TODO: 2020/10/15 shutdown and reconnect
        Log.e("Temp", "onIOError: " + e.getMessage()); // todo remove later
    }

    private void onConnectError() {
        Log.d("Temp", "onConnectError: "); // todo remove later
    }

    private void onHostOrPortError() {
        Log.d("Temp", "onHostOrPortError: "); // todo remove later
    }

    class ReadTask implements Runnable {
        @Override
        public void run() {
            Log.d("Temp", "Client read task run"); // todo remove later
            while (true) {
                try {
                    Log.d("Temp", "client read wait"); // todo remove later
                    String content = codec.read(); // will block
                    if (!TextUtils.isEmpty(content)) {
                        poster.obtainMessage(0, content).sendToTarget();
                        Log.d("Temp", "client read: " + content); // todo remove later
                    } else {
                        Log.d("Temp", "client read empty "); // todo remove later
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onIOError(false, e);
                    if (quit) {
                        Log.d("Temp", "Read thread quit"); // todo remove later
                        break;
                    }
                }
            }
        }
    }

    private Poster poster;
    private Codec codec;
    private Socket socket;
    private int connectRetry;
    private Thread writeThread;
    private Thread readThread;
    private WriteTask writeTask;
    private ReadTask readTask;
    private BlockingQueue<String> queue;
    private Callback callback;
    private String host;
    private int port;

    private volatile boolean quit;
    private volatile boolean connecting;

    public SocketConnector() {
        queue = new LinkedBlockingQueue<>();
        host = "localhost";
        port = 4444;
        poster = new Poster(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (callback != null) {
                    String obj = (String) msg.obj;
                    callback.onCallback(obj);
                }
                return true;
            }
        });
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    @Override
    public synchronized void connect() {
        if (!connecting) {
            connecting = true;
            connectRetry = 0;
            socket = new Socket();
            codec = new DefaultCodec(socket);
            writeTask = new WriteTask();
            writeThread = new Thread(writeTask);
            writeThread.start();
        }
    }

    @Override
    public void shutdown() {
        queue.clear();
        quit = true;
        writeThread.interrupt();
        codec.close();
        socket = null;
    }

    @Override
    public synchronized boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    @Override
    public void send(String s) {
        queue.offer(s);
        if (!isConnected()) {
            connect();
        }
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
