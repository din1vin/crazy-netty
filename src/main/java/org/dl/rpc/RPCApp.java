package org.dl.rpc;

import org.dl.rpc.client.RPCClient;
import org.dl.rpc.server.Server;
import org.dl.rpc.server.ServerCenter;
import org.dl.rpc.service.HelloService;
import org.dl.rpc.service.HelloServiceImpl;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author WuJi
 * @since 2022/6/15
 **/
public class RPCApp {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Server serviceServer = new ServerCenter(8088);
                serviceServer.register(HelloService.class, HelloServiceImpl.class);
                serviceServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        HelloService service = RPCClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
        System.out.println(service.sayHello("test"));
    }
}
