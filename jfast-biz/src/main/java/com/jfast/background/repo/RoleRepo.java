package com.jfast.background.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jfast.background.domain.Role;

public interface RoleRepo extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
	
	List<Role> findByDeletedFlagFalse();

}
