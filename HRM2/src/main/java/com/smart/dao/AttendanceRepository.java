package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	//this is use to get the list of contact only
	 @Query("SELECT a FROM Attendance a where a.user.id=:userId")
	  List<Attendance> findAllAttendance(@Param("userId")int userId);
}
