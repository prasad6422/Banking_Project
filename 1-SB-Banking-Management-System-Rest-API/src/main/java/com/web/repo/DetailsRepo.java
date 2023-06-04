package com.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.model.Details;
import com.web.model.Transaction;
@Repository
public interface DetailsRepo extends JpaRepository<Details, Integer> {


	@Query("SELECT d.email FROM Details d WHERE d.accountnumber = :accountnumber")
    String getEmailByAccountNumber(@Param("accountnumber") Integer accountnumber);
//	Details findByAccountnumber(Integer accountnumber);
//	 List<Details> findByStatus(String status);
//
//	Details findByAccountnumber(Integer accountnumber);

	Details findByAccountnumber(Integer accountnumber);
 
}
