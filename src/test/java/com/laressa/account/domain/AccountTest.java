package com.laressa.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Primeiro Cliente", "123.456.789-00", AccountType.CORRENTE);
    }

    @Test
    void shouldCreateActiveAccountWithZeroBalance() {
        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(account.getAccountStatus()).isEqualTo(AccountStatus.ATIVA);
    }

    @Test
    void shouldSuccessfullyCreditPositiveAmount(){
        account.credit(new BigDecimal ("100.00"));

        assertThat(account.getBalance()).isEqualByComparingTo("100.00");
    }

    @Test
    void shouldAccumulateBalanceAfterCredits(){
        account.credit(new BigDecimal ("100.00"));
        account.credit(new BigDecimal ("50.00"));

        assertThat(account.getBalance()).isEqualByComparingTo("150.00");
    }

    @Test
    void shouldSuccessfullyDebitAmountWithinAvailableBalance(){
        account.credit(new BigDecimal ("100.00"));
        account.debit(new BigDecimal ("40.00"));

        assertThat(account.getBalance()).isEqualByComparingTo("60.00");
    }

    @Test
    void shouldDebitFullBalanceAndLeaveAccountAtZero(){
        account.credit(new BigDecimal ("100.00"));
        account.debit(new BigDecimal ("100.00"));

        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

}