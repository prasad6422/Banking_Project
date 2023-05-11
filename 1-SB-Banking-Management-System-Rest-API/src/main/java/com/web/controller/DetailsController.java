package com.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.model.DepositModel;
import com.web.model.Details;
import com.web.model.Transaction;
import com.web.model.WithdrawModel;
import com.web.service.DetailsService;

@RestController
@CrossOrigin("*")
@RequestMapping("/transactions")
public class DetailsController {

	@SuppressWarnings("unused")
	private static final Integer Integer = null;
	@Autowired
	private DetailsService service;
	
	@PostMapping("/save")
	public String saveDets(@RequestBody Details details) {
		Details d1=service.saveDetails(details);
		String message=null;
		if(d1!=null) {
			message="New Bank Account Created";
		}
		else {
			message="Account Creation Failed";
		}
		return message;
	}
	@PostMapping("/deposit")
	public String deposit(@RequestBody DepositModel details) {
		Transaction t1=service.deposit(details);
		String message=null;
		if(t1!=null) {
			message=t1.getCredit()+"deposited in Account number:"+t1.getAccountnumber();
		}
		else {
			message="Deposit Failed";
		}
		return message;
	}
	@PostMapping("/withdraw")
	public String withdraw(@RequestBody WithdrawModel details) {
		Transaction t1=service.withdraw(details);
		String message=null;
		if(t1!=null) {
			message=t1.getDebit()+"withdrawed from Account number:"+t1.getAccountnumber();
		}
		else {
			message="Withdraw Failed Insuffient Funds";
		}
		return message;
	}
	@PostMapping("/transfer/{id}")
	public String transfer(@RequestBody Details details,@PathVariable Integer id) {
		Transaction t1=service.transfer(details,id);
		String message=null;
		if(t1!=null) {
			message=t1.getCredit()+"Transfered from Account number:"+id+"to Account number"+t1.getAccountnumber();
		}
		else {
			message="Transfer Failed Insuffient funds";
		}
		return message;
	}
	@GetMapping("/getone/{id}")
	public Details getOneRecord(@PathVariable Integer id) {
		Details d=service.getOneDetail(id);
		return d;
	}
	
	@GetMapping("/getAll")
	public List<Details> getAll() {
		List <Details> listDetails=service.getAllDetails();
		return listDetails;
	}
	
//	Get Last 5 Transaction With Account Number
	@GetMapping("/getlast7/{accountnumber}")
	public List<Transaction> getlast5(@PathVariable Integer accountnumber){
		List<Transaction> getlatest = service.findTop7ByOrderByDateDesc(null, accountnumber);
		return getlatest;
		
	}
	
//	Get Last 10 Transaction With Account Number
	@GetMapping("/getlast10/{accountnumber}")
	public List<Transaction> getlast10(@PathVariable Integer accountnumber){
		List<Transaction> getlateststatement = service.findTop10ByOrderByDateDesc(null,accountnumber);
		return getlateststatement;
		
	}
	
	
	
// Get All Transaction with AccountNumber
	@GetMapping("/getallstatement/{accountnumber}")
	public List<Transaction> getalltatement(@PathVariable Integer accountnumber){
		List<Transaction> getallstatement = service.findTop1ByOrderByDateDesc(accountnumber);
		return getallstatement;
	}
	
	@GetMapping("get1Monthtranstions/{accountnumber}")
	public List<Transaction> get1MontTranstions(@PathVariable Integer accountnumber){
		List<Transaction> listmonth = service.findTop1MonthByOrderByDateDesc(null,accountnumber);
		return listmonth;
	}
	
	
	 @GetMapping("/search")
	    public List<Transaction> findByDateRangeByAccNumberDesc(
	            @RequestParam("accountnumber") Integer accountnumber,
	            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
	            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
	        return service.findByDateRangeByAccNumberDesc(accountnumber, fromDate, toDate);
	    }
	 
	 @GetMapping("/checkbalance/{id}")
	 public ResponseEntity<String> getBalance(@PathVariable Integer id) {
		 String balance = service.checkBalance(id);
	        return ResponseEntity.ok(balance);
	    }
	
}




