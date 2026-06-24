package org.example;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {
    private final Scanner sc;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public OperationsConsoleListener(
            Scanner sc,
            List<OperationCommandProcessor> processorList
    ) {
        this.sc = sc;
        this.processorMap = processorList.stream()
                .collect(Collectors.toMap(
                                OperationCommandProcessor::getOperationType,
                                processor -> processor));
    }

    public void listenUpdates() {
        while(!Thread.currentThread().isInterrupted()) {
            var operationType = listenNextOperation();
            if(operationType == null) {
                return;
            }
            processNextOperation(operationType);
        }
    }

    private ConsoleOperationType listenNextOperation() {
        System.out.println("Please type next operation:");
        printAllAvailableOperations();

        while(!Thread.currentThread().isInterrupted()) {
            var nextOperation = sc.nextLine();
            try {
                return ConsoleOperationType.valueOf(nextOperation);
            }
            catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
        return null;
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
