package com.james.mall.bo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mall
 * @description：
 * @create: 2020-07-10 11:04
 * @author: James
 * @version: 1.0
 */
public class TestStream {
    public static void main(String[] args) {

//        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
//// 获取对应的平方数
//        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
//        System.out.println(squaresList);
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        System.out.println(strings);
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        filtered.forEach(System.out::println);
    }
}
