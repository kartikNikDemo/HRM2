package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {
	
	 @Query("SELECT a FROM Leave a where a.user.id=:userId")
	  List<Leave> findAllLeave(@Param("userId")int userId);

}
