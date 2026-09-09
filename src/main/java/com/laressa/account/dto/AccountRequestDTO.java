package com.laressa.account.dto;

import com.laressa.account.domain.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
    @NotBlank (message = "O nome do cliente é obrigatório")
    String clientName,
    @NotBlank (message = "O documento é obrigatório")
    String document,
    @NotNull (message = "O tipo de conta é obrigatório")
    AccountType accountType
) {

}