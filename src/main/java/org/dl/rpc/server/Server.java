package org.dl.rpc.server;

import java.io.IOException;

/**
 * @author WuJi
 * @since 2022/6/15
 **/
public interface Server {
    void stop();

    void start() throws IOException;

    void register(Class<?> serviceIntf, Class<?> Impl);

    boolean isRunning();

    int getPort();
}
