package com.laressa.account.dto;

import com.laressa.account.domain.Account;
import com.laressa.account.domain.AccountStatus;
import com.laressa.account.domain.AccountType;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDTO(

    UUID accountId,
    String clientName,
    String document,
    BigDecimal balance,
    AccountType accountType,
    AccountStatus accountStatus
) {

    public static AccountResponseDTO fromEntity(Account account){
            return new AccountResponseDTO(
                    account.getAccountId(),
                    account.getClientName(),
                    account.getDocument(),
                    account.getBalance(),
                    account.getAccountType(),
                    account.getAccountStatus()
            );
    }
}