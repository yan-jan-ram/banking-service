package com.project.banking.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.banking.dto.AccountDTO;
import com.project.banking.dto.TransferAmountDTO;
import com.project.banking.entity.AccountEntity;
import com.project.banking.exception.AccountException;
import com.project.banking.exception.InsufficientBalanceException;
import com.project.banking.exception.InvalidAmountTransferException;
import com.project.banking.repository.AccountRepository;
import com.project.banking.service.AccountService;

import jakarta.transaction.Transactional;

@Service(value = "accountService")
@Transactional
public class AccountServiceImpl implements AccountService{

	private final AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDto) throws AccountException{
		// TODO Auto-generated method stub
		AccountEntity accountEntity = AccountDTO.prepareAccountEntity(accountDto);
		AccountEntity savedEntity = accountRepository.save(accountEntity);
		
		return AccountDTO.prepareAccountDTO(savedEntity);
	}

	@Override
	public AccountDTO getAccountById(Long id) throws AccountException{
		// TODO Auto-generated method stub
		AccountEntity accountEntity = accountRepository
				.findById(id)
				.orElseThrow(() -> new AccountException("service.ACCOUNT_NOT_FOUND"));
		
		return AccountDTO.prepareAccountDTO(accountEntity);
	}

	@Override
	public AccountDTO depositAmmount(Long id, Double amount) throws AccountException {
		// TODO Auto-generated method stub
		AccountEntity accountEntity = accountRepository
				.findById(id)
				.orElseThrow(() -> new AccountException("service.ACCOUNT_NOT_FOUND"));
		Double availableBalance = accountEntity.getBalance() + amount;
		accountEntity.setBalance(availableBalance);
		AccountEntity savedEntity = accountRepository.save(accountEntity);
		return AccountDTO.prepareAccountDTO(savedEntity);
	}

	@Override
	public AccountDTO withdrawAmount(Long id, Double amount) throws AccountException {
		// TODO Auto-generated method stub
		AccountEntity accountEntity = accountRepository
				.findById(id)
				.orElseThrow(() -> new AccountException("service.ACCOUNT_NOT_FOUND"));
		if (accountEntity.getBalance() < amount) {
			throw new InsufficientBalanceException("service.NOT_ENOUGH_BALANCE");
		}
		Double availableBalance = accountEntity.getBalance() - amount;
		accountEntity.setBalance(availableBalance);
		AccountEntity savedEntity = accountRepository.save(accountEntity);
		return AccountDTO.prepareAccountDTO(savedEntity);
	}

	@Override
	public List<AccountDTO> getAllAccounts() throws AccountException {
		// TODO Auto-generated method stub
		List<AccountEntity> accounts = accountRepository.findAll();
		if (accounts.isEmpty()) {
			throw new AccountException("service.NO_ACCOUNTS_FOUND");
		}
		List<AccountDTO> dtoList = accounts
				.stream()
				.map((account) -> AccountDTO.prepareAccountDTO(account))
				.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public void deleteAccount(Long id) throws AccountException {
		// TODO Auto-generated method stub
		accountRepository
				.findById(id)
				.orElseThrow(() -> new AccountException("service.ACCOUNT_NOT_FOUND"));
		accountRepository.deleteById(id);
	}

	@Override
	public void transferAmount(TransferAmountDTO transferAmountDTO) throws AccountException {
		// TODO Auto-generated method stub
		AccountEntity fromAccount = accountRepository
				.findById(transferAmountDTO.fromAccountId())
				.orElseThrow(() -> new AccountException("service.ACCOUNT_NOT_FOUND"));
		AccountEntity toAccount = accountRepository
				.findById(transferAmountDTO.toAccountId())
				.orElseThrow(() -> new AccountException("service.ACCOUNT_NOT_FOUND"));
		
		if (transferAmountDTO.fromAccountId().equals(transferAmountDTO.toAccountId())) {
			throw new InvalidAmountTransferException("service.SAME_ACCOUNT");
		}
		if (fromAccount.getBalance() < transferAmountDTO.transferAmount()) {
			throw new InsufficientBalanceException("service.NOT_ENOUGH_BALANCE");
		}
		
		Double debit = fromAccount.getBalance() - transferAmountDTO.transferAmount();
		fromAccount.setBalance(debit);
		Double credit = toAccount.getBalance() + transferAmountDTO.transferAmount();
		toAccount.setBalance(credit);
		
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
	}

}
