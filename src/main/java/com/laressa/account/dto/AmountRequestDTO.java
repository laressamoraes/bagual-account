package com.laressa.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AmountRequestDTO(

    @NotNull(message = "O valor não pode ser nulo")
    @Positive(message = "O valor não pode ser negativo")
    BigDecimal amount
) {
}