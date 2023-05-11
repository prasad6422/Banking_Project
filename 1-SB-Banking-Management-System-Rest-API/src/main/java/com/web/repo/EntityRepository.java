package com.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.model.FileEntity;

public interface EntityRepository extends JpaRepository<FileEntity, Long> {
	@Query("update FileEntity f set f.contentType = :contentType, f.content = :content , f.accountnumber = :accountnumber where f.id = :id")
	@Modifying
	void updateFile(Integer accountnumber, String contentType, byte[] content);
//	List<FileEntity> findByAccountnumber(Integer accountnumber);
//	Optional<FileEntity> findById(Long id);
}
