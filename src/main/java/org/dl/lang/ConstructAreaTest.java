package org.dl.lang;

/**
 * @author WuJi
 * @since 2022/6/14
 **/
public class ConstructAreaTest {
    private int i;
    private String s;
    public ConstructAreaTest() {
        System.out.println("No args Construct");
    }
    public ConstructAreaTest(int i) {
        this.i = i;
        System.out.println("i construct");
    }

    {
        System.out.println("common construct");
        System.out.println("--------");
    }
    public static void main(String[] args) {
        ConstructAreaTest test1 = new ConstructAreaTest();
        ConstructAreaTest test2 = new ConstructAreaTest(1);
    }
}
