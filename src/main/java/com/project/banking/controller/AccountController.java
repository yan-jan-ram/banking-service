package com.project.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.banking.dto.AccountDTO;
import com.project.banking.dto.TransactionDTO;
import com.project.banking.dto.TransferAmountDTO;
import com.project.banking.exception.AccountException;
import com.project.banking.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/accounts")
@Validated
public class AccountController {

	@Autowired
	Environment environment;
	
	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	//http://localhost:8081/api/accounts/create
	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO accountDTO) throws AccountException {
		AccountDTO accountDto = accountService.createAccount(accountDTO);
		return new ResponseEntity<>(accountDto, HttpStatus.CREATED);
	}
	
	//http://localhost:8081/api/accounts/get/
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<AccountDTO> getAccountByid(@PathVariable Long id) throws AccountException {
		AccountDTO accountDto = accountService.getAccountById(id);
		return new ResponseEntity<>(accountDto, HttpStatus.OK);
	}
	
	//http://localhost:8081/api/accounts/deposit/
	@PutMapping(value = "/deposit/{id}")
	public ResponseEntity<AccountDTO> depositAmount(@PathVariable Long id, @RequestBody @Valid Map<String, Double> request) throws AccountException {
		Double amount = request.get("amount");
		AccountDTO accountDto = accountService.depositAmmount(id, amount);
		return new ResponseEntity<>(accountDto, HttpStatus.OK);
	}
	
	//http://localhost:8081/api/accounts/withdraw/
	@PutMapping(value = "/withdraw/{id}")
	public ResponseEntity<AccountDTO> withdrawAmount(@PathVariable Long id, @RequestBody @Valid Map<String, Double> request) throws AccountException {
		Double amount = request.get("amount");
		AccountDTO accountDto = accountService.withdrawAmount(id, amount);
		return new ResponseEntity<>(accountDto, HttpStatus.OK);
	}
	
	//http://localhost:8081/api/accounts/getAll
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<AccountDTO>> getAllAccounts() throws AccountException {
		List<AccountDTO> dtos = accountService.getAllAccounts();
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	//http://localhost:8081/api/accounts/delete/
	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws AccountException {
		accountService.deleteAccount(id);
		String message = environment.getProperty("API.DELETE_SUCCESS");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	//http://localhost:8081/api/accounts/transfer
	@PostMapping(value = "/transfer")
	public ResponseEntity<String> transferAmount(@RequestBody @Valid TransferAmountDTO transferAmountDTO) throws AccountException {
		accountService.transferAmount(transferAmountDTO);
		String message = environment.getProperty("API.TRANSFER_SUCCESS");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	//http://localhost:8081/api/accounts/transactions/
	@GetMapping(value = "/transactions/{id}")
	public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@PathVariable(value = "id") Long accountId) throws AccountException {
		List<TransactionDTO> transactionList = accountService.getTransactionHistory(accountId);
		return new ResponseEntity<>(transactionList, HttpStatus.OK);
	}
	
	//http://localhost:8081/api/accounts/bulk
	@PostMapping(value = "/bulk", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<AccountDTO>> createAccounts(@RequestBody @Valid List<AccountDTO> accountDTOs) throws AccountException {
	    List<AccountDTO> result = accountService.createAccounts(accountDTOs);
	    return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

}
