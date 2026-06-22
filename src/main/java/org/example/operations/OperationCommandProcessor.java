package org.example.operations;

public interface OperationCommandProcessor {
    void processOperation() throws IllegalAccessException;
    ConsoleOperationType getOperationType();
}
