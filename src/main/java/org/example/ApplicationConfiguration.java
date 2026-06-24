package org.example;

import org.example.account.AccountService;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public OperationsConsoleListener operationsConsoleListener(
            Scanner scanner,
            List<OperationCommandProcessor> commandProcessorList
    ) {
        Map<ConsoleOperationType, OperationCommandProcessor> processorMap =
                commandProcessorList
                        .stream()
                        .collect(Collectors.toMap(
                                        OperationCommandProcessor::getOperationType,
                                        processor -> processor));

        return new OperationsConsoleListener(scanner, processorMap);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public UserService userService(AccountService accountService) {
        return new UserService(accountService);
    }

    @Bean
    public AccountService accountService() {
        return new AccountService();
    }
}
