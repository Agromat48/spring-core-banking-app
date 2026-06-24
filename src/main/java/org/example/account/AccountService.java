package org.example.account;

import org.example.user.User;

import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountService {
    private final Map<Integer, Account> accountMap;
    private int idCounter;
    private final int defaultAccountAmount;
    private final double transferCommission;

    public AccountService(int defaultAccountAmount, double transferCommission) {
        this.defaultAccountAmount = defaultAccountAmount;
        this.transferCommission = transferCommission;
        this.accountMap = new HashMap<>();
        this.idCounter = 0;
    }

    public Account createAccount(User user) {
        ++idCounter;

        Account newAccount = new Account(idCounter, user.getId(), defaultAccountAmount);
        accountMap.put(newAccount.getId(), newAccount);

        return newAccount;
    }

    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public List<Account> getAllUserAccounts(int userId) {
        return accountMap.values()
                .stream()
                .filter(account -> account.getUserId() == userId)
                .toList();
    }

    public void depositAccount(int accountId, int moneyToDeposit) throws IllegalAccessException {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id = %s"
                        .formatted(accountId)));

        if(moneyToDeposit <= 0) {
            throw new IllegalAccessException("Cannot to deposit not positive amount: amount = %s"
                    .formatted(moneyToDeposit));
        }

        account.setMoneyAmount(account.getMoneyAmount() + moneyToDeposit);
    }

    public void withdrawAccount(int accountId, int moneyToWithdraw) throws IllegalAccessException {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id = %s"
                        .formatted(accountId)));

        if(account.getMoneyAmount() < moneyToWithdraw || moneyToWithdraw <= 0) {
            throw new IllegalAccessException("Cannot to withdraw amount: amount = %s"
                    .formatted(moneyToWithdraw));
        }

        account.setMoneyAmount(account.getMoneyAmount() - moneyToWithdraw);
    }

    public Account closeAccount(int accountId) throws IllegalAccessException {
        Account accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id = %s"
                        .formatted(accountId)));

        List<Account> accountList = getAllUserAccounts(accountToRemove.getUserId());
        if(accountList.size() == 1) {
            throw  new IllegalAccessException("Cannot close the only one account");
        }

        Account accountToDeposit =  accountList.stream()
                .filter(it -> it.getId() != accountId)
                .findFirst()
                .orElseThrow();

        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());

        accountMap.remove(accountId);
        return accountToRemove;
    }

    public void transfer(int fromAccountId, int toAccountId, int amountToTransfer) throws IllegalAccessException {
        Account accountFrom = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id = %s"
                        .formatted(fromAccountId)));

        Account  accountTo = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id = %s"
                        .formatted(toAccountId)));

        if(accountFrom.getMoneyAmount() < amountToTransfer || amountToTransfer <= 0) {
            throw new IllegalAccessException("Cannot to transfer amount: amount = %s"
                    .formatted(amountToTransfer));
        }

        int totalAmountToDeposit = accountTo.getUserId() != accountFrom.getUserId()
                ? (int) (amountToTransfer - amountToTransfer * transferCommission)
                : amountToTransfer;

        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amountToTransfer);
        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + totalAmountToDeposit);
    }
}
