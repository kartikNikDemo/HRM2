package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.Attendance;
import com.smart.entity.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Integer> {
	@Query("SELECT a FROM Payroll a where a.user.id=:userId")
	  List<Payroll> findAllPayrollList(@Param("userId")int userId);
}
