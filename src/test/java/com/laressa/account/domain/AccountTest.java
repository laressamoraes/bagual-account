package com.laressa.account.domain;

import com.laressa.account.exception.InsufficientBalanceException;
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

    @Test
    void shouldThrowExceptionWhenCreditingZeroAmount() {
        assertThatThrownBy(() -> account.credit(BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positivo");
    }

    @Test
    void shouldThrowExceptionWhenCreditingNegativeAmount() {
        assertThatThrownBy(() -> account.credit(new BigDecimal("-10.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positivo");
    }

    @Test
    void shouldThrowExceptionWhenCreditingBlockedAccount() {
        account.block();

        assertThatThrownBy(() -> account.credit(new BigDecimal("50.00")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("não está ativa");
    }

    @Test
    void shouldThrowExceptionWhenDebitExceedsBalance() {
        account.credit(new BigDecimal ("50.00"));

        assertThatThrownBy(() -> account.debit(new BigDecimal("100.00")))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessageContaining("Saldo insuficiente");
    }

    @Test
    void shouldThrowExceptionWhenDebitingZeroAmount() {
        assertThatThrownBy(() -> account.debit(BigDecimal.ZERO))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenDebitingAmountExceedsBalance() {
        assertThatThrownBy(() -> account.debit(new BigDecimal("-10.00")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenDebitingBlockedAccount() {
        account.credit(new BigDecimal ("100.00"));
        account.block();

        assertThatThrownBy(() -> account.debit(new BigDecimal("50.00")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("não está ativa");
    }

    @Test
    void shouldBlockActiveAccount() {
        account.block();

        assertThat(account.getAccountStatus()).isEqualTo(AccountStatus.BLOQUEADA);
    }

    @Test
    void shouldActivateBlockedAccount() {
        account.block();

        account.activate();

        assertThat(account.getAccountStatus()).isEqualTo(AccountStatus.ATIVA);
    }

}