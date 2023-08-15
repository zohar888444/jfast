package com.jfast.background.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jfast.background.domain.BackgroundAccount;

public interface BackgroundAccountRepo
		extends JpaRepository<BackgroundAccount, String>, JpaSpecificationExecutor<BackgroundAccount> {

	BackgroundAccount findByUserNameAndDeletedFlagIsFalse(String userName);

}
