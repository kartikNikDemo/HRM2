package com.smart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int payrollId;
	private String emplyoeeName;
	private int emplyoeeId;
	private String month; 
	private double salary;
    private double deduction;
    private String payDate;
    private String PaidDate;
    private String Status;
    
    @ManyToOne
	@JsonIgnore
	private User user;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "payroll")
	private List<Payroll>  payroll=new ArrayList<>();
	public Payroll(int payrollId, String emplyoeeName, int emplyoeeId, String month, double salary, double deduction,
			String payDate, String paidDate, String status, User user, List<Payroll> payroll) {
		super();
		this.payrollId = payrollId;
		this.emplyoeeName = emplyoeeName;
		this.emplyoeeId = emplyoeeId;
		this.month = month;
		this.salary = salary;
		this.deduction = deduction;
		this.payDate = payDate;
		PaidDate = paidDate;
		Status = status;
		this.user = user;
		this.payroll = payroll;
	}
	public Payroll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPayrollId() {
		return payrollId;
	}
	public void setPayrollId(int payrollId) {
		this.payrollId = payrollId;
	}
	public String getEmplyoeeName() {
		return emplyoeeName;
	}
	public void setEmplyoeeName(String emplyoeeName) {
		this.emplyoeeName = emplyoeeName;
	}
	public int getEmplyoeeId() {
		return emplyoeeId;
	}
	public void setEmplyoeeId(int emplyoeeId) {
		this.emplyoeeId = emplyoeeId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getDeduction() {
		return deduction;
	}
	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPaidDate() {
		return PaidDate;
	}
	public void setPaidDate(String paidDate) {
		PaidDate = paidDate;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Payroll> getPayroll() {
		return payroll;
	}
	public void setPayroll(List<Payroll> payroll) {
		this.payroll = payroll;
	}
	
	 
	
    
	 
}
