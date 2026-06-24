package org.example.operations.processors;

import org.example.account.AccountService;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class WithdrawAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public WithdrawAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() throws IllegalAccessException {
        System.out.println("Enter account id:");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to withdraw:");
        int amountToWithdraw = Integer.parseInt(scanner.nextLine());
        accountService.withdrawAccount(accountId, amountToWithdraw);
        System.out.printf("Successfully withdraw amount = %s to account id = %s%n", amountToWithdraw, accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
