package org.dl.rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author WuJi
 * @since 2022/6/15
 **/
@SuppressWarnings({"unchecked", "unused"})
public class RPCClient<T> {
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress address) {
        // 1.将本地的接口调用转换成JDK的动态代理，在动态代理中实现接口的远程调用
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface},
                (Object proxy, Method method, Object[] args) -> {
                    Socket socket = null;
                    ObjectOutputStream output = null;
                    ObjectInputStream input = null;
                    try {
                        // 2.创建Socket客户端，根据指定地址连接远程服务提供者
                        socket = new Socket();
                        socket.connect(address);
                        // 3.将远程服务调用所需的接口类、方法名、参数列表等编码后发送给服务提供者
                        output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeUTF(serviceInterface.getName());
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(args);
                        // 4.同步阻塞等待服务器返回应答，获取应答后返回
                        input = new ObjectInputStream(socket.getInputStream());
                        return input.readObject();
                    } finally {
                        if (socket != null) socket.close();
                        if (output != null) output.close();
                        if (input != null) input.close();
                    }
                });
    }
}
