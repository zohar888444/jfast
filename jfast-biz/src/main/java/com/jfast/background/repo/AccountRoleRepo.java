package com.jfast.background.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jfast.background.domain.AccountRole;

public interface AccountRoleRepo extends JpaRepository<AccountRole, String>, JpaSpecificationExecutor<AccountRole> {

	List<AccountRole> findByAccountId(String accountId);

	List<AccountRole> findByAccountIdAndRoleDeletedFlagFalse(String accountId);

}
