package com.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.model.Details;
@Repository
public interface DetailsRepo extends JpaRepository<Details, Integer> {

	Details findByAccountnumber(Integer accountnumber);

//	Details findByAccountnumber(Integer accountnumber);
//	 List<Details> findByStatus(String status);
//
//	Details findByAccountnumber(Integer accountnumber);
 
}
