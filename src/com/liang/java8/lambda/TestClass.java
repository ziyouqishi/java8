package com.liang.java8.lambda;

/**
 * Lambda 表达式的类型依赖于上下文环境，是由编译器推断出来的。
 */
public class TestClass {

    void testMethod1(TestInterface1 interface1){
        interface1.mmethod1();
    }

    void testMethod2(TestInterface2 interface2){
        interface2.method2("Hello","World");
    }

    void testMethod3(TestInterface3 interface3){
        String s=interface3.method3();
        System.out.println(s);
    }


    void testMethod4(TestInterface4 interface4){
        boolean b=interface4.method4("Hello","World");
        System.out.println(b);
    }

    /**
     * ***************************************************
     * 测试方法
     * ***************************************************
     */
    public void test(){
        /**
         * 无参无返回值
         */
        testMethod1(new TestInterface1() {
            @Override
            public void mmethod1() {
                System.out.println("匿名内部类无参无返回值");
            }
        });

        testMethod1(()->System.out.println("lambda无参无返回值"));


        /**
         * 有参无返回值
         */
        testMethod2(new TestInterface2() {
            @Override
            public void method2(String str1, String str2) {
                System.out.println("匿名内部类"+str1+str2);
            }
        });

        testMethod2((str1,str2)->{System.out.println("lambda有参无返回值"+str1+str2);});

        /**
         * 无参有返回值
         */
        testMethod3(new TestInterface3() {
            @Override
            public String method3() {
                String str1="Hello",str2="World";
                System.out.println("匿名内部类无参有返回值");
                return str1+str2;
            }
        });

        //两种写法
        testMethod3(()->  "Hello World");
        testMethod3(()->{
            String str1="Hello",str2="World";
            System.out.println("lambda无参有返回值");
            return str1+str2;

        });

        /**
         * 有参有返回值
         */
        testMethod4(new TestInterface4() {
            @Override
            public boolean method4(String str1, String str2) {
                System.out.println("匿名内部类有参有返回值");
                return str1.equals(str2);
            }
        });

        testMethod4((str1,str2)->{
            System.out.println("lambda表达式有参有返回值");
            return str1.equals(str2);

        });

        /**
         * java线程的lambda表达式写法
         */
        new Thread(()->{
            try{
                Thread.sleep(1000);
                System.out.println("java线程");
            }
            catch (Exception e){}
        }).start();

    }
}

/**
 * FunctionalInterface注解只用于只有一个抽象方法的接口，有且只有这种接口可以用于lambda表达式
 */

/**
 * 抽象方法无参无返回值
 */
@FunctionalInterface
interface TestInterface1{
    void mmethod1();
}

/**
 * 抽象方法有参无返回值
 */
@FunctionalInterface
interface TestInterface2{
    void method2(String str1,String str2);
}

/**
 * 抽象方法无参有返回值
 */
@FunctionalInterface
interface TestInterface3{
    String method3();
}


/**
 * 抽象方法有参有返回值
 */
@FunctionalInterface
interface TestInterface4{
    boolean method4(String str1,String str2);
}
