package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example");

        OperationsConsoleListener temp = context.getBean(OperationsConsoleListener.class);
        temp.listenUpdates();
    }
}
