package org.example;


import org.example.account.Account;
import org.example.account.AccountService;
import org.example.user.User;
import org.example.user.UserService;
import java.util.List;
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
            var operationType = listenNextOperation();
            try {
                processNextOperation(operationType);
            }
            catch (Exception e) {
                System.out.printf("Error executing command %s, error = %s %n", operationType, e.getMessage());
            }
        }
    }

    private String listenNextOperation() {
        return sc.nextLine();
    }

    private void processNextOperation(String operation) throws IllegalAccessException {
        switch (operation) {
            case "USER_CREATE" -> {

                System.out.print("Enter login for you user: ");
                String login = sc.nextLine();
                User user = userService.createUser(login);
                System.out.println("User created " + user.toString());

            }
            case "SHOW_ALL_USERS" -> {

                List<User> users = userService.getAllUsers();
                System.out.println("List of all users: ");
                users.forEach(System.out::println);

            }
            case "ACCOUNT_CREATE" -> {

                System.out.print("Enter the user id for which to create an account: ");
                int userId = Integer.parseInt(sc.nextLine());
                User user = userService.findUserById(userId).orElseThrow(() -> new IllegalArgumentException(
                        "No such user with id: %s".formatted(userId)));
                Account account = accountService.createAccount(user);
                user.getAccountList().add(account);

                System.out.printf("New account created with id: %s for user: %s%n", account.getId(), user.getLogin());

            }
            default -> System.out.println("LOL");
        }
    }
}
