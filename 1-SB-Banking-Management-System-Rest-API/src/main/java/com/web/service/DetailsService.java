package com.web.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import com.web.model.DepositModel;
import com.web.model.Details;
import com.web.model.Transaction;
import com.web.model.WithdrawModel;
public interface DetailsService {
	public Details saveDetails(Details dets);
	public Transaction deposit(DepositModel dets);
	public Transaction withdraw(WithdrawModel dets);
	public Transaction transfer(Details dets,Integer id);
	public Optional<Details> getOneDetail(Integer id);   
	public List<Details> getAllDetails();
	public String deleteByAccountNumber(Integer id);
	
	
	
//	public List<Transaction> getAllTransaction(Integer id);
	
	List<Transaction> findTop10ByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber);
	List<Transaction> findTop7ByOrderByDateDesc(PageRequest pageRequest, Integer accountnumber);
	List<Transaction> findTopAllByOrderByDateDesc(Integer accountnumber);  
	List<Transaction> findTop1MonthByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber);
	public String checkBalance(Integer id);
	List<Transaction> findByDateRangeByAccNumberDesc(Integer accountnumber, LocalDate fromDate, LocalDate toDate);
	void saveFile(Integer id, String contentType, byte[] content);
//	ByteArrayInputStream load(Integer accountnumber, LocalDate fromdate, LocalDate todate);

	
	Optional<Transaction> get1Transaction(Integer accountnumber);  
	public String getEmailByAccountNumber(Integer accountnumber); 
	List<Transaction> getLatestMonthTransactions(Integer accountnumber, LocalDate startDate, LocalDate endDate); 
	String getDetailsView();
	
}
