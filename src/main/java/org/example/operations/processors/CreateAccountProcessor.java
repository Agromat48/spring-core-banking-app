package org.example.operations.processors;

import org.example.account.Account;
import org.example.account.AccountService;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.user.User;
import org.example.user.UserService;

import java.util.Scanner;

public class CreateAccountProcessor implements OperationCommandProcessor {
    private final Scanner scanner;
    private final AccountService accountService;
    private final UserService userService;

    public CreateAccountProcessor(Scanner sc, AccountService accountService, UserService userService) {
        this.scanner = sc;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void processOperation() {
        System.out.print("Enter the user id for which to create an account: ");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userService.findUserById(userId).orElseThrow(() -> new IllegalArgumentException(
                "No such user with id: %s".formatted(userId)));
        Account account = accountService.createAccount(user);
        user.getAccountList().add(account);

        System.out.printf("New account created with id: %s for user: %s%n", account.getId(), user.getLogin());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
