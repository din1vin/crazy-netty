package org.dl.nio.buffer;

import java.nio.IntBuffer;

/**
 * @author WuJi
 * @since 2022/6/13
 **/
public class BufferTest {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(20);
        System.out.println("capacity: " + intBuffer.capacity());
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        System.out.println("capacity: " + intBuffer.capacity());
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        intBuffer.flip();
        System.out.println("capacity: " + intBuffer.capacity());
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        intBuffer.flip();
        System.out.println("capacity: " + intBuffer.capacity());
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
    }
}
