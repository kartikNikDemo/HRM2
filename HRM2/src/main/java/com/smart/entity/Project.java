package com.smart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int projectId;
	private String projectTitle;
	private String startDate;
	private String endDate;
	private String deliveryDate;
	private String totalDuration;
	private String NumberOfEmplyoee;
	private String Status;
	private String Description;
	public Project(int projectId, String projectTitle, String startDate, String endDate, String deliveryDate,
			String totalDuration, String numberOfEmplyoee, String status, String description) {
		super();
		this.projectId = projectId;
		this.projectTitle = projectTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.deliveryDate = deliveryDate;
		this.totalDuration = totalDuration;
		NumberOfEmplyoee = numberOfEmplyoee;
		Status = status;
		Description = description;
	}
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}
	public String getNumberOfEmplyoee() {
		return NumberOfEmplyoee;
	}
	public void setNumberOfEmplyoee(String numberOfEmplyoee) {
		NumberOfEmplyoee = numberOfEmplyoee;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	

}
