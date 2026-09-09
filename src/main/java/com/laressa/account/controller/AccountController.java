package com.laressa.account.controller;

import com.laressa.account.domain.Account;
import com.laressa.account.dto.AccountRequestDTO;
import com.laressa.account.dto.AccountResponseDTO;
import com.laressa.account.dto.AmountRequestDTO;
import com.laressa.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO accountRequest) {
        Account account = accountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponseDTO.fromEntity(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable UUID id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok(AccountResponseDTO.fromEntity(account));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> findAll() {
        List<AccountResponseDTO> accounts = accountService.findAll().stream().map(AccountResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(accounts);
    }

    @PatchMapping("/{id}/debit")
    public ResponseEntity<AccountResponseDTO> debit(@PathVariable UUID id, @Valid @RequestBody AmountRequestDTO amountRequest) {
        Account account = accountService.debit(id, amountRequest.amount());
        return  ResponseEntity.ok(AccountResponseDTO.fromEntity(account));
    }

    @PatchMapping("/{id}/credit")
    public ResponseEntity<AccountResponseDTO> credit(@PathVariable UUID id, @Valid @RequestBody AmountRequestDTO amountRequest) {
        Account account = accountService.credit(id, amountRequest.amount());
        return ResponseEntity.ok(AccountResponseDTO.fromEntity(account));
    }
}