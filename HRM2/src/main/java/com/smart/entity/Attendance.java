package com.smart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int attendenceId;
	private String emplyoeeName;
	private String date;
	private String signTime;
	private String logOutTime;
	private String place;
	private String emplyoeeId;
	
	
	@ManyToOne
	@JsonIgnore
	private User user;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "attendance")
	private List<Attendance>  attendance=new ArrayList<>();
	
	public Attendance(int attendenceId, String emplyoeeName, String date, String signTime, String logOutTime,
			String place, String emplyoeeId, User user, List<Attendance> attendance) {
		super();
		this.attendenceId = attendenceId;
		this.emplyoeeName = emplyoeeName;
		this.date = date;
		this.signTime = signTime;
		this.logOutTime = logOutTime;
		this.place = place;
		this.emplyoeeId = emplyoeeId;
		this.user = user;
		this.attendance = attendance;
	}
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getAttendenceId() {
		return attendenceId;
	}
	public void setAttendenceId(int attendenceId) {
		this.attendenceId = attendenceId;
	}
	public String getEmplyoeeName() {
		return emplyoeeName;
	}
	public void setEmplyoeeName(String emplyoeeName) {
		this.emplyoeeName = emplyoeeName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getLogOutTime() {
		return logOutTime;
	}
	public void setLogOutTime(String logOutTime) {
		this.logOutTime = logOutTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getEmplyoeeId() {
		return emplyoeeId;
	}
	public void setEmplyoeeId(String emplyoeeId) {
		this.emplyoeeId = emplyoeeId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Attendance> getAttendance() {
		return attendance;
	}
	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}
	

	
	}
	


	
	


