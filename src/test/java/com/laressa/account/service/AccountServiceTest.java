package com.laressa.account.service;

import com.laressa.account.domain.Account;
import com.laressa.account.domain.AccountType;
import com.laressa.account.dto.AccountRequestDTO;
import com.laressa.account.exception.AccountNotFoundException;
import com.laressa.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private AccountRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new AccountRequestDTO("Primeiro Cliente", "123-456-789.00", AccountType.CORRENTE);
        account = new Account(requestDTO.clientName(), requestDTO.document(), requestDTO.accountType());
    }

    @Test
    void shouldThrowExceptionWhenRepositoryFails() {
        when(accountRepository.save(any(Account.class))).thenThrow(new RuntimeException("Erro de conexão com o banco!"));
        assertThatThrownBy(() -> accountService.createAccount(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Erro de conexão com o Banco!");
    }

    @Test
    void shouldCreateAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.createAccount(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getClientName()).isEqualTo("Primeiro Cliente");
        assertThat(result.getDocument()).isEqualTo("123-456-789.00");
        assertThat(result.getAccountType()).isEqualTo(AccountType.CORRENTE);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldFindAccount() {
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.findById(id))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void shouldListAllAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of(account));

        List<Account> result = accountService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(account);
        verify(accountRepository).findAll();
    }
}