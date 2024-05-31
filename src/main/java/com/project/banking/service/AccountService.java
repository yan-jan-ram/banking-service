package com.project.banking.service;

import java.util.List;

import com.project.banking.dto.AccountDTO;
import com.project.banking.dto.TransferAmountDTO;
import com.project.banking.exception.AccountException;

public interface AccountService {

	public AccountDTO createAccount(AccountDTO accountDto) throws AccountException;
	public AccountDTO getAccountById(Long id) throws AccountException;
	public AccountDTO depositAmmount(Long id, Double amount) throws AccountException;
	public AccountDTO withdrawAmount(Long id, Double amount) throws AccountException;
	public List<AccountDTO> getAllAccounts() throws AccountException;
	public void deleteAccount(Long id) throws AccountException;
	public void transferAmount(TransferAmountDTO transferAmountDTO) throws AccountException;
}
