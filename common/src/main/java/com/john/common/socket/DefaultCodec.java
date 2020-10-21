package com.john.common.socket;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by JohnZh on 2020/10/16
 *
 * <p></p>
 */
public class DefaultCodec implements Codec {

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public DefaultCodec(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void write(String s) throws IOException {
        outputStream = socket.getOutputStream();
        Log.d("Temp", "write: " + s + ", len: " + s.length()); // todo remove later
        writeInt(s.length());
        writeString(s);
    }

    @Override
    public String read() throws IOException {
        inputStream = socket.getInputStream();
        int length = readInt();
        Log.d("Temp", "read: " + length); // todo remove later
        byte[] bytes;
        if (length != -1 && length > 0) {
            bytes = new byte[length];
            int size = inputStream.read(bytes);
            if (size == -1 || size != length) { // error
                throw new IOException("Socket shutdown or length error");
            }
            return new String(bytes, 0, bytes.length, "UTF-8");
        } else {
            throw new IOException("Socket shutdown or length error");
        }
    }

    @Override
    public void close() {
        if (socket != null) {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                inputStream = null;
                outputStream = null;
                socket = null;
            }
        }
    }

    private int readInt() throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int length = dataInputStream.readInt();
        return length;
    }

    private void writeString(String json) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(outputStream);
        out.write(json.getBytes("UTF-8"));
        out.flush();
    }

    private void writeInt(int length) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(length);
        dataOutputStream.flush();
    }
}
