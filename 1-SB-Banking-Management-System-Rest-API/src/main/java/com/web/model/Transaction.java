package com.web.model;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
@Entity
@NamedQuery(name="Transaction.findTop1ByOrderByDateDesc", query = "select t from Transaction t where t.accountnumber =:accountnumber order by t.date desc")
@NamedQuery(name="Transaction.findTop10ByOrderByDateDesc", query = "select t from Transaction t where t.accountnumber =:accountnumber order by t.date desc")
@NamedQuery(name="Transaction.findTop7ByOrderByDateDesc", query = "select t from Transaction t where t.accountnumber =:accountnumber order by t.date desc")
@NamedQuery(name="Transaction.findTop1MonthByOrderByDateDesc", query = "select t from Transaction t where t.accountnumber =:accountnumber order by t.date desc")
//@NamedQuery(
//	    name = "Transaction.findByDateRangeByAccNumberDesc",
//	    query = "SELECT t FROM Transaction t WHERE t.accountnumber = :accNumber AND (t.date BETWEEN :fromDate AND :toDate)"
//	)
public class Transaction {
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator ="111")
	@Column(length = 20)
	private Integer accountnumber;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator ="1")
	@Column(length = 20)
	private Integer transaction_number;
	@Column(length = 20)
	private LocalDateTime date;
	@Column(length = 20)
	private String fullname;
	@Column(length = 20)
	private Double currentbalance;
	@Column(length = 20)
	private Double debit;
	@Column(length = 20)
	private Double credit;
	@Column(length = 20)
	private String address;
	public Transaction() {}
	public Transaction(Integer accountnumber, Integer transaction_number, LocalDateTime date, String fullname,
			Double currentbalance, Double debit, Double credit, String address) {
		super();
		this.accountnumber = accountnumber;
		this.transaction_number = transaction_number;
		this.date = date;
		this.fullname = fullname;
		this.currentbalance = currentbalance;
		this.debit = debit;
		this.credit = credit;
		this.address = address;
	}
	public Integer getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(Integer accountnumber) {
		this.accountnumber = accountnumber;
	}
	public Integer getTransaction_number() {
		return transaction_number;
	}
	public void setTransaction_number(Integer transaction_number) {
		this.transaction_number = transaction_number;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Double getCurrentbalance() {
		return currentbalance;
	}
	public void setCurrentbalance(Double currentbalance) {
		this.currentbalance = currentbalance;
	}
	public Double getDebit() {
		return debit;
	}
	public void setDebit(Double debit) {
		this.debit = debit;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Transaction [accountnumber=" + accountnumber + ", transaction_number=" + transaction_number + ", date="
				+ date + ", fullname=" + fullname + ", currentbalance=" + currentbalance + ", debit=" + debit
				+ ", credit=" + credit + ", address=" + address + "]";
	}

	
	}