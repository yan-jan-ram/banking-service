package com.project.banking.dto;

import com.project.banking.entity.AccountEntity;

import lombok.Data;

@Data
public class AccountDTO {

	private Long id;
	private String holderName;
	private Double balance;
	
	public static AccountEntity prepareAccountEntity(AccountDTO accountDto) {
		AccountEntity accountEntity = new AccountEntity();
		
		accountEntity.setId(accountDto.getId());
		accountEntity.setHolderName(accountDto.getHolderName());
		accountEntity.setBalance(accountDto.getBalance());
		
		return accountEntity;
	}
	
	public static AccountDTO prepareAccountDTO(AccountEntity accountEntity) {
		AccountDTO accountDto = new AccountDTO();
		
		accountDto.setId(accountEntity.getId());
		accountDto.setHolderName(accountEntity.getHolderName());
		accountDto.setBalance(accountEntity.getBalance());
		
		return accountDto;
	}
}
