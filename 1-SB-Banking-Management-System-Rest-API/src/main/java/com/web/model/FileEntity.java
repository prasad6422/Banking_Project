package com.web.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
//@NamedQuery(name="FileEntity.findByAccountNumber", query = "select f from FileEntity f where f.accountnumber =:accountnumber")
//public class FileEntity implements Serializable {
public class FileEntity {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 20)
	private Integer accountnumber;
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY,generator ="1")
//	private Long id;
	private String contentType;

	@Lob
	private byte[] content;

	public FileEntity() {
		super();
	}

	
//	public Long getId() {
//		return id;
//	}
//
//
//	public void setId(Long id) {
//		this.id = id;
//	}


	public Integer getAccountnumber() {
		return accountnumber;
	}


	public void setAccountnumber(Integer accountnumber) {
		this.accountnumber = accountnumber;
	}


	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "FileEntity [accountnumber=" + accountnumber + ", contentType=" + contentType
				+ ", content=" + Arrays.toString(content) + "]";
	}

	
	
}
