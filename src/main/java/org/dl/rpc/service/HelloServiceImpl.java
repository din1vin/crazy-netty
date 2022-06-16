package org.dl.rpc.service;

/**
 * @author WuJi
 * @since 2022/6/15
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello, " + name;
    }
}
