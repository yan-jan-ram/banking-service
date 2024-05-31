package com.project.banking.dto;

public record TransferAmountDTO(
		Long fromAccountId, 
		Long toAccountId, 
		Double transferAmount) {
//Unlike a class, record doesn't need getter-setter methods
}
