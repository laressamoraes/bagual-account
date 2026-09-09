package com.laressa.account.service;

import com.laressa.account.domain.Account;

import com.laressa.account.dto.AccountRequestDTO;
import com.laressa.account.exception.AccountNotFoundException;
import com.laressa.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(AccountRequestDTO request) {
        Account account = new Account(
                request.clientName(),
                request.document(),
                request.accountType()
        );
        return accountRepository.save(account);
    }

    public Account findById (UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Conta não encontrada " + accountId));
    }

    public List<Account> findAll()  {
        return accountRepository.findAll();
    }

    public Account debit (UUID accountId, BigDecimal amount) {
        Account  account = findById(accountId);
        account.debit(amount);
        return accountRepository.save(account);
    }

    public Account credit (UUID accountId, BigDecimal amount) {
        Account  account = findById(accountId);
        account.credit(amount);
        return accountRepository.save(account);
    }
}