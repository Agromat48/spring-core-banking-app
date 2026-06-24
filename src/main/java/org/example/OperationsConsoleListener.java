package org.example;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import java.util.Map;
import java.util.Scanner;

public class OperationsConsoleListener {
    private final Scanner sc;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    public OperationsConsoleListener(
            Scanner sc,
            Map<ConsoleOperationType, OperationCommandProcessor> processorMap
    ) {
        this.sc = sc;
        this.processorMap = processorMap;
    }

    public void listenUpdates() {
        while(true) {
            var operationType = listenNextOperation();
            processNextOperation(operationType);
        }
    }

    private ConsoleOperationType listenNextOperation() {
        System.out.println("Please type next operation:");
        printAllAvailableOperations();

        while(true) {
            var nextOperation = sc.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperation);
            }
            catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
    }

    private void printAllAvailableOperations() {
        processorMap.keySet().stream()
                .sorted(Enum::compareTo)
                .forEach(System.out::println);
    }

    private void processNextOperation(ConsoleOperationType operation) {
        try {
            var processor = processorMap.get(operation);
            processor.processOperation();
        }
        catch (Exception e) {
            System.out.printf("Error executing command %s, error = %s %n", operation, e.getMessage());
        }
    }

    public void start() {
        System.out.println("Console listener started");
    }

    public void end() {
        System.out.println("Console listener end listen");
    }
}
