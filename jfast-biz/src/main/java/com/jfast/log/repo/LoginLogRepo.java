package com.jfast.log.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jfast.log.domain.LoginLog;

public interface LoginLogRepo extends JpaRepository<LoginLog, String>, JpaSpecificationExecutor<LoginLog> {
	
}
