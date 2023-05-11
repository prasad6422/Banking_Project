package com.web.service;

import java.time.LocalDate;
import java.util.List;

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
	public void deleteDetails(Integer id);
	public Details getOneDetail(Integer id);
	public List<Details> getAllDetails();
	
	List<Transaction> findTop10ByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber);
	List<Transaction> findTop7ByOrderByDateDesc(PageRequest pageRequest, Integer accountnumber);
	List<Transaction> findTop1ByOrderByDateDesc(Integer accountnumber);
	List<Transaction> findTop1MonthByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber);
	public String checkBalance(Integer id);
	List<Transaction> findByDateRangeByAccNumberDesc(Integer accountnumber, LocalDate fromDate, LocalDate toDate);

	
}
