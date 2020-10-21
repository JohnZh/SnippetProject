package com.john.common.socket;

import java.io.IOException;

/**
 * Created by JohnZh on 2020/10/13
 *
 * <p></p>
 */
public interface Codec {

    void write(String s) throws IOException;

    String read() throws IOException;

    void close();
}
