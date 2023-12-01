package com.smart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "leave_Details")
public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int leaveId;
	private String EmplyoeeName;
	private String EmplyoeeId;
	private String StartDate;
	private String EndDate;
	private String  LeaveType;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Leave> getLeave() {
		return leave;
	}
	public void setLeave(List<Leave> leave) {
		this.leave = leave;
	}


	@ManyToOne
	@JsonIgnore
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "leave")
	private List<Leave>  leave=new ArrayList<>(); 
	
       
	public Leave(int leaveId, String emplyoeeName, String emplyoeeId, String startDate, String endDate,
			String leaveType, String description, User user, List<Leave> leave) {
		super();
		this.leaveId = leaveId;
		EmplyoeeName = emplyoeeName;
		EmplyoeeId = emplyoeeId;
		StartDate = startDate;
		EndDate = endDate;
		LeaveType = leaveType;
		this.description = description;
		this.user = user;
		this.leave = leave;
	}
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}
	public String getEmplyoeeName() {
		return EmplyoeeName;
	}
	public void setEmplyoeeName(String emplyoeeName) {
		EmplyoeeName = emplyoeeName;
	}
	public String getEmplyoeeId() {
		return EmplyoeeId;
	}
	public void setEmplyoeeId(String emplyoeeId) {
		EmplyoeeId = emplyoeeId;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	public String getLeaveType() {
		return LeaveType;
	}
	public void setLeaveType(String leaveType) {
		LeaveType = leaveType;
	}
	
	
	

}
