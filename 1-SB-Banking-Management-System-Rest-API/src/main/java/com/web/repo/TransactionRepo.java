package com.web.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.model.Details;
import com.web.model.Transaction;
@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
	List<Transaction> findTop7ByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber);
	List<Transaction> findTop10ByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber);
	List<Transaction> findTopAllByOrderByDateDesc(Integer accountnumber);
	List<Transaction> findTop1MonthByOrderByDateDesc(PageRequest pageRequest,Integer accountnumber);
	Details save(Details ds);
	@Query("SELECT t FROM Transaction t WHERE t.accountnumber = :accountnumber AND (t.date BETWEEN :fromDate AND :toDate)")
	List<Transaction> findByDateRangeByAccNumberDesc(@Param("accountnumber") Integer accountnumber, @Param("fromDate") LocalDateTime fromdate, @Param("toDate") LocalDateTime todate);

//	 List<Transaction> findByDateBetween(Integer accountnumber,LocalDate startDate, LocalDate endDate);
	
	}