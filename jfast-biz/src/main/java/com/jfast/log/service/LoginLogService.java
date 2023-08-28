package com.jfast.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.jfast.log.domain.LoginLog;
import com.jfast.log.repo.LoginLogRepo;

@Validated
@Service
public class LoginLogService {

	@Autowired
	private LoginLogRepo loginLogRepo;

	@Transactional
	public void recordLoginLog(LoginLog loginLog) {
		loginLogRepo.save(loginLog);
	}

}
