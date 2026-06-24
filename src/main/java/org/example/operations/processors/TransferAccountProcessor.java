package org.example.operations.processors;

import org.example.account.AccountService;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;

import java.util.Scanner;

public class TransferAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    public TransferAccountProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() throws IllegalAccessException {
        System.out.println("Enter source account id:");
        int fromAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter destination account id:");
        int toAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to transfer:");
        int amountToTransfer = Integer.parseInt(scanner.nextLine());
        accountService.transfer(fromAccountId, toAccountId, amountToTransfer);
        System.out.printf("Successfully transferred %s from account id: %s to account id: %s%n", amountToTransfer, fromAccountId, toAccountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
