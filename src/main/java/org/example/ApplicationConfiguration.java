package org.example;

import org.example.account.AccountService;
import org.example.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Scanner;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public OperationsConsoleListener operationsConsoleListener(
            UserService userService,
            AccountService accountService,
            Scanner scanner
    ) {
        return new OperationsConsoleListener(scanner, userService, accountService);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public UserService UserService(AccountService accountService) {
        return new UserService(accountService);
    }

    @Bean
    public AccountService accountService() {
        return new AccountService();
    }
}
