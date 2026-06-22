package org.example.operations.processors;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;

public class TransferAccountProcessor implements OperationCommandProcessor {
    @Override
    public void processOperation() throws IllegalAccessException {

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
