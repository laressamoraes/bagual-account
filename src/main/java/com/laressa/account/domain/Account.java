package com.laressa.account.domain;

import com.laressa.account.exception.InsufficientBalanceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;

    @NotBlank
    @Column(nullable = false)
    private String clientName;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus;

    @Version
    private Long version;

    public Account() {
    }

    public Account(String clientName, String document, AccountType accountType) {
        this.clientName = clientName;
        this.document = document;
        this.balance = BigDecimal.ZERO;
        this.accountType = accountType;
        this.accountStatus = AccountStatus.ATIVA;
    }

    public void debit(BigDecimal amount) {
        validatePositiveAmount(amount);
        validateActiveAccount();

        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente para débito de " + amount);
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        validatePositiveAmount(amount);
        validateActiveAccount();

        this.balance = this.balance.add(amount);
    }

    public void block() {
        this.accountStatus = AccountStatus.BLOQUEADA;
    }

    public void activate() {
        this.accountStatus = AccountStatus.ATIVA;
    }

    private void validatePositiveAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser positivo!");
        }
    }

    private void validateActiveAccount() {
        if (this.accountStatus != AccountStatus.ATIVA) {
            throw new IllegalStateException("Conta não está ativa para movimentação!");
        }
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getVersion() {
        return version;
    }
}