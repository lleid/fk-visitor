package com.fk.visitor.api;

import java.util.regex.Pattern;

public class TestJava {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        System.out.println(pattern.matcher("13888").find());
        System.out.println(pattern.matcher("a").find());
        System.out.println(pattern.matcher("13118891750").find());
    }
}
