package org.example;


import org.example.account.AccountService;
import org.example.user.UserService;

import java.util.Scanner;

public class OperationsConsoleListener {
    private final Scanner sc;
    private final UserService userService;
    private final AccountService accountService;

    public OperationsConsoleListener(Scanner sc,
                                     UserService userService,
                                     AccountService accountService) {
        this.sc = sc;
        this.userService = userService;
        this.accountService = accountService;
    }

    public void listenUpdates() {
        while(true) {
            String nextOperation = sc.nextLine();

            if(nextOperation.equals("ACCOUNT_CREATE")) {
                System.out.println("account created");
            }
            else if(nextOperation.equals("USER_CREATE")) {
                System.out.println("user created");

            }
            else {
                System.out.println("LOL");
            }
        }
    }
}
