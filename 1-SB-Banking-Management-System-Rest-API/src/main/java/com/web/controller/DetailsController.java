package com.web.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.model.DepositModel;
import com.web.model.Details;
import com.web.model.Transaction;
import com.web.model.WithdrawModel;
import com.web.service.DetailsService;
import com.web.service.DetailsServiceImp;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping("/transactions")
public class DetailsController {

	@SuppressWarnings("unused")
	private static final Integer Integer = null;
	@Autowired
	private DetailsService service;
	@Autowired
	DetailsServiceImp serviceimpl;
	
//	Create The New Account
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
	
//	Deposit The Amount
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
//	Withdraw The Amount
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
//	Transfer The Amount By One Account To Another Account
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
	
//	Get Account Details With Account Number
	@GetMapping("/getone/{id}")
	public Details getOneRecord(@PathVariable Integer id) {
		Details d=service.getOneDetail(id);
		return d;
	}
//	Get All Accounts
	@GetMapping("/getAll")
	public List<Details> getAll() {
		List <Details> listDetails=service.getAllDetails();
		return listDetails;
	}
	
//	Get Last One Week Transaction With Account Number
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
	
//	Get All One Month Transaction With Account Number
	@GetMapping("get1Monthtranstions/{accountnumber}")
	public List<Transaction> get1MontTranstions(@PathVariable Integer accountnumber){
		List<Transaction> listmonth = service.findTop1MonthByOrderByDateDesc(null,accountnumber);
		return listmonth;
	}
	
//	Get All Transaction Between Date With Account Number
	 @GetMapping("/search")
	    public List<Transaction> findByDateRangeByAccNumberDesc(
	    	@RequestParam("accountnumber") Integer accountnumber,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
            List<Transaction> statements = service.findByDateRangeByAccNumberDesc(accountnumber,startDate, endDate);
            return statements;
	 }
	 
//	 Check Balance With Account Number
	 @GetMapping("/checkbalance/{id}")
	 public ResponseEntity<String> getBalance(@PathVariable Integer id) {
		 String balance = service.checkBalance(id);
	        return ResponseEntity.ok(balance);
	    }
	 
	 
//Download Transaction With Account Number Between Dates In XML Format
	 @GetMapping("/download")
	  public ResponseEntity<Resource> getFile(@RequestParam("accountnumber") Integer accountnumber,
		        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
	    String filename = "transaction.xlsx";
	    InputStreamResource file = new InputStreamResource(serviceimpl.load(accountnumber,startDate, endDate));

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	  }	 
	 
	
//	 Upload Files With Account Number
	 @PostMapping("/files")
	    public ResponseEntity<Void> uploadFile(
	            @RequestParam("id") Integer id,
	            @RequestParam("file") MultipartFile file) throws IOException {
	        String contentType = file.getContentType();
	        byte[] content = file.getBytes();
	        serviceimpl.saveFile(id, contentType, content);
	        return ResponseEntity.ok().build();
	    }
	 
	 @PutMapping("/update")
		public ResponseEntity<Void> updateFile(
				@RequestParam("accountnumber") Integer accountnumber,
				@RequestParam("file") MultipartFile file) throws IOException {
			String contentType = file.getContentType();
			byte[] content = file.getBytes();
			serviceimpl.updateFile(accountnumber, contentType, content);
			return ResponseEntity.ok().build();
		}
	 
	 
	 
}




