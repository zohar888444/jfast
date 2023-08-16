package com.jfast.background.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jfast.background.domain.BackgroundAccount;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

@Data
public class SuperAdminVO {

	private String id;

	private String userName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date latelyLoginTime;

	public static List<SuperAdminVO> convertFor(List<BackgroundAccount> pos) {
		if (CollectionUtil.isEmpty(pos)) {
			return new ArrayList<>();
		}
		List<SuperAdminVO> vos = new ArrayList<>();
		for (BackgroundAccount po : pos) {
			vos.add(convertFor(po));
		}
		return vos;
	}

	public static SuperAdminVO convertFor(BackgroundAccount po) {
		if (po == null) {
			return null;
		}
		SuperAdminVO vo = new SuperAdminVO();
		BeanUtils.copyProperties(po, vo);
		return vo;
	}

}
