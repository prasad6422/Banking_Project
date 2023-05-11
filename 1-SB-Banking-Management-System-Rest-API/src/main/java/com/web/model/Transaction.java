package com.web.model;
import java.time.LocalDate;
import java.time.LocalTime;

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
//	@Temporal(TemporalType.DATE)
	private LocalDate date;
	@Column(length = 20)
	private LocalTime time;
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

	public Transaction(Integer accountnumber, LocalDate date, LocalTime time,String fullname, Double currentbalance, Double debit,
			Double credit, String address) {
		super();
		this.accountnumber = accountnumber;
		this.date = date;
		this.time = time;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
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
		return "Transaction [accountnumber=" + accountnumber + ", date=" + date + ", time" + time + ", fullname=" + fullname
				+ ", currentbalance=" + currentbalance + ", debit=" + debit + ", credit=" + credit + ", address="
				+ "address ]";
	}
}