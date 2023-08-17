package com.jfast.background.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jfast.background.domain.Menu;

public interface MenuRepo extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {
	
	List<Menu> findByDeletedFlagFalseOrderByOrderNo();
	
	List<Menu> findByParentIdAndDeletedFlagFalse(String parentId);
	
	List<Menu> findByIdInAndDeletedFlagFalseOrderByOrderNo(List<String> ids);

}
