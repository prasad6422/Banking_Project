package com.web.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web.model.DepositModel;
import com.web.model.Details;
import com.web.model.Transaction;
import com.web.model.WithdrawModel;
import com.web.repo.DetailsRepo;
import com.web.repo.TransactionRepo;
@Service
public class DetailsServiceImp implements DetailsService {
	@Autowired
	private DetailsRepo drepo;
	
	@Autowired
	private TransactionRepo trepo;
	
	@Override
	public Details saveDetails(Details details) {
		details.setFullname(details.getFirstname()+" "+details.getLastname());
		details.setStatus("active");
		
		if(details.getBranch().equalsIgnoreCase("Hyderabad")) {
			details.setIfsccode("HDFC0001");
		}
		else if(details.getBranch().equals("Bangalore")) {
			details.setIfsccode("HDFC0002");
		}
		else{
			details.setIfsccode("HDFC0003");
		}
		Details d=drepo.save(details);
		Transaction t=new Transaction();
		t.setAccountnumber(details.getAccountnumber());
		t.setAddress(details.getAddress());
		t.setCredit(details.getCurrentbalance());
		t.setDebit(0.0);
		t.setCurrentbalance(details.getCurrentbalance());
		t.setFullname(details.getFirstname()+details.getLastname());
		LocalDate date = LocalDate.now();
		t.setDate(date);
		t.setTime(LocalTime.now());
	    trepo.save(t);
			return d;
	}

	@Override
	public Details getOneDetail(Integer id) {
		Details details=drepo.findById(id).get();
		return details;
	}
	@Override
	public Transaction deposit(DepositModel details) {
		
		Details oldDets=drepo.findById(details.getAccountnumber()).get();
		oldDets.setCurrentbalance(details.getDepositeAmount()+oldDets.getCurrentbalance());
		drepo.save(oldDets);
		Transaction t=new Transaction();
		t.setAccountnumber(details.getAccountnumber());
		t.setAddress(oldDets.getAddress());
		t.setCredit(details.getDepositeAmount());
		t.setDebit(0.0);
		t.setCurrentbalance(oldDets.getCurrentbalance());
		t.setFullname(oldDets.getFirstname()+oldDets.getLastname());

		LocalDate date = LocalDate.now();
		t.setDate(date);
		t.setTime(LocalTime.now());
	      trepo.save(t);
		return t;
	}
	@Override
	public Transaction withdraw(WithdrawModel details) {
		Details oldDets=drepo.findById(details.getAccountnumber()).get();
		if(oldDets.getCurrentbalance()>=details.getWithdrawAmount()) {
		oldDets.setCurrentbalance(oldDets.getCurrentbalance()-details.getWithdrawAmount());
		drepo.save(oldDets);
		Transaction t=new Transaction();
		t.setAccountnumber(details.getAccountnumber());
		t.setAddress(oldDets.getAddress());
		t.setDebit(details.getWithdrawAmount());
		t.setCredit(0.0);
		t.setCurrentbalance(oldDets.getCurrentbalance());
		t.setFullname(oldDets.getFirstname()+oldDets.getLastname());
		
		LocalDate date = LocalDate.now();
		t.setDate(date);
		t.setTime(LocalTime.now());
	      trepo.save(t);
	      return t;
		}
		else {
			
		return null;
		}
	}

	@Override
	public Transaction transfer(Details details, Integer id) {
		Details creditoldDets=drepo.findById(details.getAccountnumber()).get();
		Details debitoldDets=drepo.findById(id).get();
		if(debitoldDets.getCurrentbalance()>=details.getCurrentbalance()) {
		debitoldDets.setCurrentbalance(debitoldDets.getCurrentbalance()-details.getCurrentbalance());
		drepo.save(debitoldDets);
		creditoldDets.setCurrentbalance(details.getCurrentbalance()+creditoldDets.getCurrentbalance());
		drepo.save(creditoldDets);
		Transaction t=new Transaction();
		   t.setAccountnumber(id);
		   t.setAddress(debitoldDets.getAddress());
		   t.setDebit(details.getCurrentbalance());
		   t.setCredit(0.0);
		   t.setCurrentbalance(debitoldDets.getCurrentbalance());
		   t.setFullname(debitoldDets.getFirstname()+debitoldDets.getLastname());
		   LocalDate date = LocalDate.now();
			t.setDate(date);
			t.setTime(LocalTime.now());
	       trepo.save(t);
	    Transaction t1=new Transaction();
		   t1.setAccountnumber(details.getAccountnumber());
		   t1.setAddress(debitoldDets.getAddress());
		   t1.setCredit(details.getCurrentbalance());
		   t1.setDebit(0.0);
		   t1.setCurrentbalance(creditoldDets.getCurrentbalance());
		   t1.setFullname(debitoldDets.getFirstname()+debitoldDets.getLastname());
		   LocalDate date2 = LocalDate.now();
			t1.setDate(date2);
			t1.setTime(LocalTime.now());
		  
		   trepo.save(t1);
		
		return t1;
		}
		else {
			return null;
		}
		}
	@Override
	public void deleteDetails(Integer id) {
		drepo.deleteById(id);

	}


	@Override
	public List<Details> getAllDetails() {
		List<Details> list=drepo.findAll();
		return list;
	}

	@Override
	public List<Transaction> findTop10ByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber) {
		 return trepo.findTop10ByOrderByDateDesc(PageRequest.of(0, 10),accountnumber);
	}

	@Override
	public List<Transaction> findTop7ByOrderByDateDesc(PageRequest pageRequest, Integer accountnumber) {
		 return trepo.findTop7ByOrderByDateDesc(PageRequest.of(0, 7),accountnumber);
	}

	@Override
	public List<Transaction> findTop1ByOrderByDateDesc(Integer accountnumber) {
		return trepo.findTop1ByOrderByDateDesc(accountnumber);
	}

	@Override
	public List<Transaction> findTop1MonthByOrderByDateDesc(PageRequest pageRequest, Integer accountnumber) {
		
		return trepo.findTop1MonthByOrderByDateDesc(PageRequest.of(0, 30), accountnumber);
	}
	
	@Override
	@Transactional
	public List<Transaction> findByDateRangeByAccNumberDesc(Integer accountnumber, LocalDate fromdate,
			LocalDate todate) {
		return trepo.findByDateRangeByAccNumberDesc(accountnumber, fromdate, todate);
	}
	
    @Override
    public String checkBalance(Integer id) {
    Details trans = drepo.findById(id).orElse(null);
        if (trans == null) {
            return "Transaction not found for ID: " + id;
        }
    Double balance = trans.getCurrentbalance();
    return "Current balance for Account ID " + id + " is : " + balance;
    }

}
