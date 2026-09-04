package com.laressa.account.dto;

import com.laressa.account.domain.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
    @NotBlank String clientName,
    @NotBlank String document,
    @NotNull AccountType accountType
) {

}