package com.hongri.recyclerview.java8;

/**
 * Create by zhongyao on 2021/10/14
 * Description:Java8新特性系列(interface):
 * 1、@FunctionalInterface注解 --- 函数式接口：
 * 函数式接口其实本质上还是一个接口，但是它是一种特殊的接口：
 * SAM类型的接口（Single Abstract Method）。定义了这种类型的接口，使得以其为参数的方法，可以在调用时，使用一个Lambda表达式作为参数。
 *
 * 注意点：
 * (1)、唯一的抽象方法，有且仅有一个。
 * (2)、可用于lamba类型的使用方式。
 * (3)、不能被覆盖之后，再声明为抽象方法，则不算抽象方法。
 * (4)、加上标注,则会触发JavaCompiler的检查。对于符合函数接口的接口，加不加都无关紧要，但是加上则会提供一层编译检查的保障。
 *      如果不符合，则会报错。
 */
@FunctionalInterface
public interface IMyListener {

    String study(String msg);

//    void learn();
}
