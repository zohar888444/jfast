package com.jfast.background.param;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.jfast.background.domain.Role;
import com.jfast.common.utils.IdUtils;

import lombok.Data;

@Data
public class RoleParam {

	private String id;

	@NotBlank
	private String name;

	public Role convertToPo() {
		Role po = new Role();
		BeanUtils.copyProperties(this, po);
		po.setId(IdUtils.getId());
		po.setCreateTime(new Date());
		po.setDeletedFlag(false);
		return po;
	}

}
