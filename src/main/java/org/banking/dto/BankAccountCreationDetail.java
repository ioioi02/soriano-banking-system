package org.banking.dto;

import java.math.BigDecimal;

public record BankAccountCreationDetail (String accountHolderName, BigDecimal initialDeposit, String createdByEmployee) {
}