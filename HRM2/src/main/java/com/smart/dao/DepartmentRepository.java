package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.Contact;
import com.smart.entity.User;

public interface DepartmentRepository extends JpaRepository<Contact, Integer> {

	 //It deparment emplyoee list
	@Query("SELECT c FROM Contact c WHERE department='IT' AND c.user = :user")
	public List<Contact> getListOFItEmplyoee( @Param("user") User user);
	
	@Query("SELECT c FROM Contact c WHERE department='Sales' AND c.user = :user")
	public List<Contact> getListOFSalesEmplyoee( @Param("user") User user);
	
	@Query("SELECT c FROM Contact c WHERE department='Marketing' AND c.user = :user")
	public List<Contact> getListOFMarketingEmplyoee( @Param("user") User user);
	
	@Query("SELECT c FROM Contact c WHERE department='Production' AND c.user = :user")
	public List<Contact> getListOFProductionEmplyoee( @Param("user") User user);
	
	@Query("SELECT c FROM Contact c WHERE department='Purchase' AND c.user = :user")
	public List<Contact> getListOFPurchaseEmplyoee( @Param("user") User user);
	
	
}
