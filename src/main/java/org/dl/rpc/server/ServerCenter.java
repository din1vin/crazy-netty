package org.dl.rpc.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WuJi
 * @since 2022/6/15
 **/
@SuppressWarnings({"unchecked", "unused", "InfiniteLoopStatement"})
public class ServerCenter implements Server {
    private static final ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Map<String, Class<?>> serverRegistry = new HashMap<>();
    private boolean isRunning = false;
    private final int port;

    public ServerCenter(int port) {
        this.port = port;
    }

    @Override
    public void stop() {
        executor.shutdown();
        isRunning = false;
    }

    @Override
    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        isRunning = true;
        System.out.println("server started");
        try {
            while (true) {
                executor.execute(new ServerTask(server.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }

    @Override
    public void register(Class<?> serviceIntf, Class<?> impl) {
        serverRegistry.put(serviceIntf.getName(), impl);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPort() {
        return port;
    }

    private static class ServerTask implements Runnable {
        final Socket client;

        public ServerTask(Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class<?> serviceClass = serverRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassCastException(serviceName + "not found");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);
                // 3.将执行结果反序列化，通过socket发送给客户端
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tryClose(output, input, client);
            }
        }

        private void tryClose(Closeable... c) {
            for (Closeable closeable : c) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }
            }

        }
    }

}
