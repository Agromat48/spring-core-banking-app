package org.example.operations.processors;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.user.User;
import org.example.user.UserService;

import java.util.Scanner;

public class CreateUserProcessor implements OperationCommandProcessor {
    private final Scanner sc;
    private final UserService userService;

    public CreateUserProcessor(Scanner sc, UserService userService) {
        this.sc = sc;
        this.userService = userService;
    }

    @Override
    public void processOperation() throws IllegalAccessException {
        System.out.print("Enter login for you user: ");
        String login = sc.nextLine();
        User user = userService.createUser(login);
        System.out.println("User created " + user.toString());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
