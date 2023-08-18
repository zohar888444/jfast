package com.jfast.background.vo;

import org.springframework.beans.BeanUtils;

import com.jfast.background.domain.BackgroundAccount;

import lombok.Data;

@Data
public class AccountAuthInfoVO {

	private String id;

	private String userName;

	private String loginPwd;

	private String state;
	
	private Boolean superAdminFlag;

	public static AccountAuthInfoVO convertFor(BackgroundAccount po) {
		if (po == null) {
			return null;
		}
		AccountAuthInfoVO vo = new AccountAuthInfoVO();
		BeanUtils.copyProperties(po, vo);
		return vo;
	}

}
