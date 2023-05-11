package com.web.model;
import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
@Entity
public class Details {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator ="111")
	@Column(length = 20)
	private Integer accountnumber;
	@Column(length = 20)
	private String firstname;
	@Column(length = 20)
	private String lastname;
	@Column(length = 20)
	private String fullname;
	@Column(length = 30)
	private String email;
	@Column(length = 20)
	private String password;
	@Column(length = 20)
	private String accounttype;
	@Column(length = 20)
	private String address;
	@Column(length = 20)
	private String gender;
	@Column(length = 20)
	private String branch;
	@Column(length = 20)
	private String ifsccode;
	@Column(length = 20)
	private Long mobilenumber;
	@Column(length = 40)
	@Lob
	private Blob photo;
	@Column(length = 60)
	private String adharcard;
	@Column(length = 60)
	private Double currentbalance;
	@Column(length = 20)
	private String status;
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "transaction_id",referencedColumnName = "accountnumber")

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Transaction transaction;

	public Details() {}

	public Integer getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(Integer accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public Long getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(Long mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}

	public String getAdharcard() {
		return adharcard;
	}

	public void setAdharcard(String adharcard) {
		this.adharcard = adharcard;
	}

	public Double getCurrentbalance() {
		return currentbalance;
	}

	public void setCurrentbalance(Double currentbalance) {
		this.currentbalance = currentbalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public String toString() {
		return "Details [accountnumber=" + accountnumber + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", fullname=" + fullname + ", email=" + email + ", password=" + password + ", accounttype="
				+ accounttype + ", address=" + address + ", gender=" + gender + ", branch=" + branch + ", ifsccode="
				+ ifsccode + ", mobilenumber=" + mobilenumber + ", photo=" + photo + ", adharcard=" + adharcard
				+ ", currentbalance=" + currentbalance + ", status=" + status + ", transaction=" + transaction + "]";
	}
	
}